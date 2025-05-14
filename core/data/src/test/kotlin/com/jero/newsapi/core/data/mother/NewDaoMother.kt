package com.jero.newsapi.core.data.mother

import com.example.database.entity.NewEntity

object NewDaoMother {
    fun buildNewEntity(
        title: String = "title",
        description: String = "description",
        imageUrl: String = "imageUrl",
        author: String = "author",
        articleUrl: String = "articleUrl"
    ): NewEntity = NewEntity(
        title, description, imageUrl, author, articleUrl
    )
}