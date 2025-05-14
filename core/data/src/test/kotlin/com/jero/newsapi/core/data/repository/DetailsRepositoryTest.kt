package com.jero.newsapi.core.data.repository

import app.cash.turbine.test
import com.example.data.repository.details.DetailsRepositoryImpl
import com.example.database.NewDao
import com.example.network.model.mappers.toNewDetail
import com.example.network.service.NewClient
import com.example.network.service.NewService
import com.example.test.MainCoroutinesRule
import com.jero.newsapi.core.data.mother.NewDaoMother
import com.jero.newsapi.core.data.mother.ApiNewMother
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.responseOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.Response

class DetailsRepositoryTest {

    private lateinit var repository: DetailsRepositoryImpl
    private lateinit var client: NewClient
    private val service: NewService = mock()
    private val newDao: NewDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        client = NewClient(service)
        repository = DetailsRepositoryImpl(client, newDao)
    }

    @Test
    fun `GIVEN a not favorite new THEN that new is not favorite`() = runTest {
        val mockUrl = "https://example.com/article"
        val mockImageUrl = "https://example.com/image.jpg"
        val mockApiNewDetail = ApiNewMother.buildApiNewDetailResponse()
        val expectedNewDetail = mockApiNewDetail.response?.docs?.firstOrNull()?.toNewDetail(mockImageUrl)

        val fq = "web_url:(\"$mockUrl\")"
        doReturn(ApiResponse.responseOf { Response.success(mockApiNewDetail) }).whenever(service).fetchNewDetail(fq)

        whenever(newDao.getNewByUrl(mockUrl)).thenReturn(null)

        repository.fetchNewDetail(mockUrl, mockImageUrl).test {
            val result = awaitItem()
            assertEquals(expectedNewDetail, result)
            assertEquals(false, result.isFavorite)
            awaitComplete()
        }

        verify(service).fetchNewDetail(fq)
        verify(newDao).getNewByUrl(mockUrl)
        verifyNoMoreInteractions(service, newDao)
    }

    @Test
    fun `GIVEN a favorite new THEN that new is favorite`() = runTest {
        val mockUrl = "https://example.com/article"
        val mockImageUrl = "https://example.com/image.jpg"
        val mockApiNewDetail = ApiNewMother.buildApiNewDetailResponse()
        val expectedNewDetail = mockApiNewDetail.response?.docs?.firstOrNull()?.toNewDetail(mockImageUrl)?.copy(isFavorite = true)

        val fq = "web_url:(\"$mockUrl\")"
        doReturn(ApiResponse.responseOf { Response.success(mockApiNewDetail) }).whenever(service).fetchNewDetail(fq)

        whenever(newDao.getNewByUrl(mockUrl)).thenReturn(NewDaoMother.buildNewEntity(
            title = expectedNewDetail?.title.orEmpty(),
            description = expectedNewDetail?.abstract.orEmpty(),
            imageUrl = mockImageUrl,
            author = expectedNewDetail?.author.orEmpty(),
            articleUrl = mockUrl
        ))

        repository.fetchNewDetail(mockUrl, mockImageUrl).test {
            val result = awaitItem()
            assertEquals(expectedNewDetail, result)
            assertEquals(true, result.isFavorite)
            awaitComplete()
        }

        verify(service).fetchNewDetail(fq)
        verify(newDao).getNewByUrl(mockUrl)
        verifyNoMoreInteractions(service, newDao)
    }
}
