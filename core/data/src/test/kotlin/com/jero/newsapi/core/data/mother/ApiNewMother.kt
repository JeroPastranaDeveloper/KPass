package com.jero.newsapi.core.data.mother

import com.example.network.model.ApiByLine
import com.example.network.model.ApiDocs
import com.example.network.model.ApiMedia
import com.example.network.model.ApiMediaMetadata
import com.example.network.model.ApiNew
import com.example.network.model.ApiNewDetail
import com.example.network.model.ApiNewDetailResponse
import com.example.network.model.ApiNewsResponse
import com.example.network.model.ApiPerson

object ApiNewMother {
    fun buildApiNewResponse(
        status: String = "OK",
        totalResults: Int = 10,
        articles: List<ApiNew> = buildApiNewList()
    ): ApiNewsResponse =
        ApiNewsResponse(
            status = status,
            totalResults = totalResults,
            articles = articles
        )

    private fun buildApiNewList(): List<ApiNew> = listOf(
        ApiNew(
            title = "title 1",
            description = "description 1",
            articleUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1KCVIvx-J4z9RQZ7hxt7HOOoEW95J8NOEw&s",
            author = "author 1",
            media = buildApiMediaList()
        ),
        ApiNew(
            title = "title 2",
            description = "description 2",
            articleUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1KCVIvx-J4z9RQZ7hxt7HOOoEW95J8NOEw&s",
            author = "author 2",
            media = buildApiMediaList()
        )
    )

    private fun buildApiMediaList(): List<ApiMedia> = listOf(
        ApiMedia(
            type = "image",
            subtype = "thumbnail",
            mediaMetadata = listOf(
                ApiMediaMetadata(
                    url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1KCVIvx-J4z9RQZ7hxt7HOOoEW95J8NOEw&s",
                    format = "Standard Thumbnail",
                    height = 150,
                    width = 150
                ),
                ApiMediaMetadata(
                    url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1KCVIvx-J4z9RQZ7hxt7HOOoEW95J8NOEw&s",
                    format = "Large Thumbnail",
                    height = 300,
                    width = 300
                )
            )
        )
    )

    fun buildApiNewDetailResponse(): ApiNewDetailResponse = ApiNewDetailResponse(
        response = ApiDocs(
            docs = listOf(
                ApiNewDetail(
                    snippet = "snippet",
                    printPage = "A1",
                    sectionName = "section name",
                    abstract = "abstract",
                    source = "source",
                    uri = "uri",
                    newsDesk = "news desk",
                    pubDate = "2025-01-06",
                    wordCount = 1200,
                    leadParagraph = "lead paragraph",
                    typeOfMaterial = "type of material",
                    webUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRI1KCVIvx-J4z9RQZ7hxt7HOOoEW95J8NOEw&s",
                    printSection = "print section",
                    id = "12345",
                    subsectionName = "subsection name",
                    byline = ApiByLine(
                        original = "original",
                        person = listOf(
                            ApiPerson(
                                firstName = "first name",
                                middleName = null,
                                lastName = "last name",
                                role = "role"
                            )
                        )
                    ),
                    documentType = "document type"
                )
            )
        )
    )
}
