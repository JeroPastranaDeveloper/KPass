package com.jero.data.file_permissions_manager

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.jero.core.model.Account
import com.jero.domain.file_manager.SecureFileManager
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

class SecureFileManagerImpl : SecureFileManager {

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA256"
        private const val ITERATION_COUNT = 65536
        private const val KEY_LENGTH = 256
        private const val SALT_LENGTH = 16
        private const val IV_LENGTH = 12
    }

    override fun encryptAndSave(context: Context, uri: Uri, password: String, accounts: List<Account>) {
        val salt = ByteArray(SALT_LENGTH).apply { SecureRandom().nextBytes(this) }
        val iv = ByteArray(IV_LENGTH).apply { SecureRandom().nextBytes(this) }
        val key = deriveKey(password, salt)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))

        val json = Gson().toJson(accounts)
        val encryptedData = cipher.doFinal(json.toByteArray(UTF_8))

        context.contentResolver.openOutputStream(uri)?.use { output ->
            output.write(salt)
            output.write(iv)
            output.write(encryptedData)
        }
    }

    override fun decryptAndRead(context: Context, uri: Uri, password: String): List<Account>? {
        val allBytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: return null
        if (allBytes.size <= SALT_LENGTH + IV_LENGTH) return null

        val salt = allBytes.sliceArray(0 until SALT_LENGTH)
        val iv = allBytes.sliceArray(SALT_LENGTH until (SALT_LENGTH + IV_LENGTH))
        val encryptedData = allBytes.sliceArray(SALT_LENGTH + IV_LENGTH until allBytes.size)

        val key = deriveKey(password, salt)

        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
            val decryptedBytes = cipher.doFinal(encryptedData)
            val json = decryptedBytes.toString(UTF_8)
            Gson().fromJson(json, Array<Account>::class.java).toList()
        } catch (e: Exception) {
            null
        }
    }

    private fun deriveKey(password: String, salt: ByteArray): SecretKey {
        val factory = SecretKeyFactory.getInstance(KEY_DERIVATION_ALGORITHM)
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH)
        val tmp = factory.generateSecret(spec)
        return SecretKeySpec(tmp.encoded, "AES")
    }
}
