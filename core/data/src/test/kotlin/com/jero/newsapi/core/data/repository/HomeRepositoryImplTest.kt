package com.jero.newsapi.core.data.repository

import app.cash.turbine.test
import com.example.data.repository.home.HomeRepositoryImpl
import com.example.network.service.NewClient
import com.example.network.service.NewService
import com.example.test.MainCoroutinesRule
import com.jero.newsapi.core.data.mother.ApiNewMother
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.retrofit.responseOf
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.Response

class HomeRepositoryImplTest {

    private lateinit var repository: HomeRepositoryImpl
    private lateinit var client: NewClient
    private val service: NewService = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        client = NewClient(service)
        repository = HomeRepositoryImpl(client)
    }

    @Test
    fun `GIVEN a news list THEN emit that list of news`() = runTest {
        val mockData = ApiNewMother.buildApiNewResponse()

        whenever(service.fetchNewList()).thenReturn(
            ApiResponse.responseOf {
                Response.success(mockData)
            }
        )

        repository.fetchNewsList().test {
            val newsList = awaitItem()

            assertEquals(mockData.articles.size, newsList.size)

            val firstApiNew = mockData.articles[0]
            val firstNew = newsList[0]
            assertEquals(firstApiNew.title, firstNew.title)
            assertEquals(firstApiNew.description, firstNew.description)
            assertEquals(firstApiNew.articleUrl, firstNew.articleUrl)
            assertEquals(firstApiNew.author, firstNew.author)

            awaitComplete()
        }

        verify(service, atLeastOnce()).fetchNewList()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun `GIVEN an empty news list THEN the count of news is 0`() = runTest {
        val emptyMockData = ApiNewMother.buildApiNewResponse(articles = emptyList())

        whenever(service.fetchNewList()).thenReturn(
            ApiResponse.responseOf { Response.success(emptyMockData) }
        )

        repository.fetchNewsList().test {
            val newsList = awaitItem()
            assertEquals(0, newsList.size)
            awaitComplete()
        }

        verify(service, atLeastOnce()).fetchNewList()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun `GIVEN an API error THEN emit an error`() = runTest {
        whenever(service.fetchNewList()).thenReturn(
            ApiResponse.responseOf { Response.error(500, "Internal Server Error".toResponseBody()) }
        )

        repository.fetchNewsList().test {
            awaitComplete()
        }

        verify(service, atLeastOnce()).fetchNewList()
        verifyNoMoreInteractions(service)
    }
}
