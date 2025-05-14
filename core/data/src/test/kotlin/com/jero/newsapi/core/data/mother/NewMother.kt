package com.jero.newsapi.core.data.mother

import com.example.model.New
import com.example.model.NewDetail
import com.example.utils.emptyString

object NewMother {
    fun buildNewList(new: New = buildNew()): List<New> = listOf(new)

    fun buildNew(
        title: String = "Title 1",
        description: String = "Description 1",
        articleUrl: String = "url1",
        author: String = "Author 1",
        imageUrl: String = "image url"
    ): New = New(
        title = title,
        description = description,
        articleUrl = articleUrl,
        author = author,
        imageUrl = imageUrl
    )

    fun buildNewDetailList(new: NewDetail = buildNewDetail()): List<NewDetail> = listOf(new)

    fun buildNewDetail(
        title: String = emptyString(),
        snippet: String = emptyString(),
        printPage: String = emptyString(),
        sectionName: String = emptyString(),
        abstract: String = emptyString(),
        imageUrl: String = emptyString(),
        source: String = emptyString(),
        uri: String = emptyString(),
        newsDesk: String = emptyString(),
        pubDate: String = emptyString(),
        wordCount: Int = 0,
        leadParagraph: String = emptyString(),
        typeOfMaterial: String = emptyString(),
        webUrl: String = emptyString(),
        printSection: String = emptyString(),
        id: String = emptyString(),
        subsectionName: String = emptyString(),
        author: String = emptyString(),
        documentType: String = emptyString(),
        isFavorite: Boolean = false,
    ): NewDetail = NewDetail(
        title,
        snippet,
        printPage,
        sectionName,
        abstract,
        imageUrl,
        source,
        uri,
        newsDesk,
        pubDate,
        wordCount,
        leadParagraph,
        typeOfMaterial,
        webUrl,
        printSection,
        id,
        subsectionName,
        author,
        documentType,
        isFavorite
    )
}