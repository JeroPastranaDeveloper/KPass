package com.jero.newsapi.core.data.viewmodel

import com.example.domain.repository.home.HomeRepository
import com.example.domain.repository.roomdatabase.GetNewsRepository
import com.example.home.HomeViewContract
import com.example.home.HomeViewModel
import com.example.home.toNew
import com.example.model.ALL_NEWS
import com.example.model.FAVORITE_NEWS
import com.example.test.MainCoroutinesRule
import com.jero.newsapi.core.data.mother.NewMother
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var newsRepository: HomeRepository
    private lateinit var getNewsRepository: GetNewsRepository

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        newsRepository = mock()
        getNewsRepository = mock()
        homeViewModel = HomeViewModel(newsRepository, getNewsRepository)
    }

    @Test
    fun `WHEN getAllNews is called THEN updates state with the fetched news`() = runTest {
        val news = NewMother.buildNewList()

        newsRepository.stub {
            onBlocking { fetchNewsList() }.thenReturn(flowOf(news))
        }

        homeViewModel.sendIntent(HomeViewContract.UiIntent.ChangeScreen(ALL_NEWS))

        val state = homeViewModel.state.first()

        assertFalse(state.isLoading)
        assertEquals(news, state.news)
        assertEquals(ALL_NEWS, state.screen)
    }

    @Test
    fun `GIVEN a ChangeScreen intent with FAVORITE_NEWS WHEN getFavoriteNews is called THEN updates state with favorite news`() =
        runTest {
            val favoriteNews = NewMother.buildNewDetailList()

            getNewsRepository.stub {
                onBlocking { getNews() }.thenReturn(favoriteNews)
            }

            homeViewModel.sendIntent(HomeViewContract.UiIntent.ChangeScreen(FAVORITE_NEWS))

            val favoriteNewsMapped = favoriteNews.map {
                it.toNew()
            }

            val state = homeViewModel.state.first()

            assertFalse(state.isLoading)
            assertEquals(favoriteNewsMapped, state.news)
            assertEquals(FAVORITE_NEWS, state.screen)
        }
}
