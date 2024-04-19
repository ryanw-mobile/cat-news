/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import uk.ryanwong.catnews.app.database.LocalDatabaseImpl
import uk.ryanwong.catnews.newslist.data.local.entity.NewsItemEntity
import uk.ryanwong.catnews.newslist.data.local.entity.NewsListEntity

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
internal class NewsListDaoTest {
    private lateinit var localDatabase: LocalDatabaseImpl
    private lateinit var newsListDao: NewsListDao

    /**
     * Test plan: basic CRUD usages with some special cases
     */

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabaseImpl::class.java)
            .build()

        newsListDao = localDatabase.newsListDao()
    }

    @After
    fun cleanUp() {
        localDatabase.close()
    }

    /***
     * List Title
     */
    @Test
    fun emptyTable_InsertListTitle_ReturnListTitle() = runTest {
        val listId = 1
        val listTitle = "some-list-title"
        newsListDao.getNewsListTitle(listId = listId) shouldBe null

        newsListDao.insertNewsListTitle(NewsListEntity(listId = listId, title = listTitle))

        val returnedTitle = newsListDao.getNewsListTitle(listId = listId)
        returnedTitle shouldBe listTitle
    }

    @Test
    fun existingListTitle_UpdateListTitle_ReturnUpdatedListTitle() = runTest {
        val listId = 1
        val existingListTitle = "some-list-title"
        val newListTitle = "some-new-list-title"
        newsListDao.insertNewsListTitle(NewsListEntity(listId = listId, title = existingListTitle))

        newsListDao.insertNewsListTitle(NewsListEntity(listId = listId, title = newListTitle))

        val returnedTitle = newsListDao.getNewsListTitle(listId = listId)
        returnedTitle shouldBe newListTitle
    }

    @Test
    fun existingListTitle_DeleteListTitle_ReturnNullTitle() = runTest {
        val listId = 1
        val existingListTitle = "some-list-title"
        newsListDao.insertNewsListTitle(NewsListEntity(listId = listId, title = existingListTitle))

        newsListDao.deleteListTitle(listId = listId)

        val returnedTitle = newsListDao.getNewsListTitle(listId = listId)
        returnedTitle shouldBe null
    }

    /***
     * News List (in the form of List<NewsItemEntity>)
     */
    @Test
    fun emptyTable_InsertEmptyNewsList_ReturnEmptyList() = runTest {
        val listId = 1
        val newsItems = emptyList<NewsItemEntity>()
        newsListDao.getNewsList(listId = listId) shouldBe emptyList()

        newsListDao.insertNewsItems(newsItems = newsItems)

        val returnedNewsList = newsListDao.getNewsList(listId = listId)
        returnedNewsList shouldBe emptyList()
    }

    @Test
    fun emptyTable_InsertOneNewsItem_ReturnOneNewsItem() = runTest {
        val listId = 1
        val newsItems = listOf(
            NewsItemEntity(
                listId = listId,
                newsId = 1,
                type = "some-type",
                headline = "some-headline",
                creationDate = "2022-5-21T00:00:00Z",
                modifiedDate = "2022-5-21T00:00:00Z",
                advertUrl = "https://some.url/",
                weblinkUrl = "https://some.weblink.url/",
                teaserText = "some-teaser-text",
                teaserImageHref = "https://some.teaser.image/href",
                teaserImageTemplated = true,
                teaserImageType = "some-teaser-image-type",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
            ),
        )
        newsListDao.getNewsList(listId = listId) shouldBe emptyList()

        newsListDao.insertNewsItems(newsItems = newsItems)

        val returnedNewsList = newsListDao.getNewsList(listId = listId)
        returnedNewsList shouldBe newsItems
    }

    @Test
    fun emptyTable_InsertMultipleNewsItems_ReturnMultipleNewsItems() = runTest {
        val listId = 1
        val newsItems = listOf(
            NewsItemEntity(
                listId = listId,
                newsId = 1,
                type = "some-type",
                headline = "some-headline",
                creationDate = "2022-5-21T00:00:00Z",
                modifiedDate = "2022-5-21T00:00:00Z",
                advertUrl = "https://some.url/",
                weblinkUrl = "https://some.weblink.url/",
                teaserText = "some-teaser-text",
                teaserImageHref = "https://some.teaser.image/href",
                teaserImageTemplated = true,
                teaserImageType = "some-teaser-image-type",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
            ),
            NewsItemEntity(
                listId = listId,
                newsId = 2,
                type = "some-type-2",
                headline = "some-headline-2",
                creationDate = "2022-5-21T00:00:01Z",
                modifiedDate = "2022-5-21T00:00:01Z",
                advertUrl = "https://some.url/2",
                weblinkUrl = "https://some.weblink.url/2",
                teaserText = "some-teaser-text-2",
                teaserImageHref = "https://some.teaser.image/href/2",
                teaserImageTemplated = true,
                teaserImageType = "some-teaser-image-type-2",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text-2",
            ),
            NewsItemEntity(
                listId = listId,
                newsId = 3,
                type = "some-type-3",
                headline = "some-headline-3",
                creationDate = "2022-5-21T00:00:02Z",
                modifiedDate = "2022-5-21T00:00:02Z",
                advertUrl = "https://some.url/3",
                weblinkUrl = "https://some.weblink.url/3",
                teaserText = "some-teaser-text-3",
                teaserImageHref = "https://some.teaser.image/href/3",
                teaserImageTemplated = true,
                teaserImageType = "some-teaser-image-type-3",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text-3",
            ),
        )
        newsListDao.getNewsList(listId = listId) shouldBe emptyList()

        newsListDao.insertNewsItems(newsItems = newsItems)

        val returnedNewsList = newsListDao.getNewsList(listId = listId)
        returnedNewsList shouldContainExactlyInAnyOrder newsItems
    }

    @Test
    fun existingNewsList_ReplaceNewsItem_ReturnUpdatedNewsList() = runTest {
        val listId = 1
        val newsItemEntityOne = NewsItemEntity(
            listId = listId,
            newsId = 1,
            type = "some-type",
            headline = "some-headline",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text",
            teaserImageHref = "https://some.teaser.image/href",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
        )
        val newsItemEntityTwo = NewsItemEntity(
            listId = listId,
            newsId = 2,
            type = "some-type-2",
            headline = "some-headline-2",
            creationDate = "2022-5-21T00:00:01Z",
            modifiedDate = "2022-5-21T00:00:01Z",
            advertUrl = "https://some.url/2",
            weblinkUrl = "https://some.weblink.url/2",
            teaserText = "some-teaser-text-2",
            teaserImageHref = "https://some.teaser.image/href/2",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type-2",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text-2",
        )
        val newsItemEntityOneModified = NewsItemEntity(
            listId = listId,
            newsId = 1,
            type = "some-type-3",
            headline = "some-headline-3",
            creationDate = "2022-5-21T00:00:02Z",
            modifiedDate = "2022-5-21T00:00:02Z",
            advertUrl = "https://some.url/3",
            weblinkUrl = "https://some.weblink.url/3",
            teaserText = "some-teaser-text-3",
            teaserImageHref = "https://some.teaser.image/href/3",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type-3",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text-3",
        )
        val newsItems = listOf(newsItemEntityOne, newsItemEntityTwo)
        newsListDao.insertNewsItems(newsItems = newsItems)

        newsListDao.insertNewsItems(newsItems = listOf(newsItemEntityOneModified))

        val returnedNewsList = newsListDao.getNewsList(listId = listId)
        returnedNewsList shouldContainExactlyInAnyOrder listOf(
            newsItemEntityOneModified,
            newsItemEntityTwo,
        )
    }

    @Test
    fun existingNewsList_DeleteNewsList_ReturnEmptyList() = runTest {
        val listId = 1
        val newsItemEntityOne = NewsItemEntity(
            listId = listId,
            newsId = 1,
            type = "some-type",
            headline = "some-headline",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text",
            teaserImageHref = "https://some.teaser.image/href",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
        )
        val newsItemEntityTwo = NewsItemEntity(
            listId = listId,
            newsId = 2,
            type = "some-type-2",
            headline = "some-headline-2",
            creationDate = "2022-5-21T00:00:01Z",
            modifiedDate = "2022-5-21T00:00:01Z",
            advertUrl = "https://some.url/2",
            weblinkUrl = "https://some.weblink.url/2",
            teaserText = "some-teaser-text-2",
            teaserImageHref = "https://some.teaser.image/href/2",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type-2",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text-2",
        )
        val newsItems = listOf(newsItemEntityOne, newsItemEntityTwo)
        newsListDao.insertNewsItems(newsItems = newsItems)

        newsListDao.deleteNewsItems(listId = listId)

        val returnedNewsList = newsListDao.getNewsList(listId = listId)
        returnedNewsList shouldBe emptyList()
    }

    /***
     * News Item (in the form of NewsItemEntity?)
     */
    @Test
    fun existingTable_GetOneNewsItem_ReturnOneNewsItem() = runTest {
        val listId = 1
        val originalNewsItemEntity = NewsItemEntity(
            listId = listId,
            newsId = 1,
            type = "some-type",
            headline = "some-headline",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text",
            teaserImageHref = "https://some.teaser.image/href",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
        )
        val newsItems = listOf(originalNewsItemEntity)
        newsListDao.insertNewsItems(newsItems = newsItems)

        val newsItemEntity = newsListDao.getNewsItem(listId = listId, newsId = 1)

        newsItemEntity shouldBe originalNewsItemEntity
    }

    @Test
    fun existingTable_GetNonExistentItem_ReturnNull() = runTest {
        val listId = 1
        val originalNewsItemEntity = NewsItemEntity(
            listId = listId,
            newsId = 1,
            type = "some-type",
            headline = "some-headline",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text",
            teaserImageHref = "https://some.teaser.image/href",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
        )
        val newsItems = listOf(originalNewsItemEntity)
        newsListDao.insertNewsItems(newsItems = newsItems)

        val newsItemEntity = newsListDao.getNewsItem(listId = listId, newsId = 2)

        newsItemEntity shouldBe null
    }
}
