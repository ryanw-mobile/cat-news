/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uk.ryanwong.catnews.app.database.LocalDatabaseImpl
import uk.ryanwong.catnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.catnews.storydetail.data.local.entity.StoryEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
internal class StoryDaoTest {
    private lateinit var localDatabase: LocalDatabaseImpl
    private lateinit var storyDao: StoryDao

    /**
     * Test plan: basic CRUD usages with some special cases
     */
    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        localDatabase = Room.inMemoryDatabaseBuilder(context, LocalDatabaseImpl::class.java)
            .build()

        storyDao = localDatabase.storyDao()
    }

    @After
    fun cleanUp() {
        localDatabase.close()
    }

    /**
     * Story
     */
    @Test
    fun emptyTable_InsertStory_ReturnStory() = runTest {
        val storyId = 1
        val story = StoryEntity(
            storyId = storyId,
            headline = "some-headline",
            heroImageUrl = "https://some.hero.image/url",
            heroImageAccessibilityText = "some-accessibility-text",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
        )
        storyDao.getStory(storyId = storyId) shouldBe null

        storyDao.insertStory(story = story)

        val returnedStory = storyDao.getStory(storyId = storyId)
        returnedStory shouldBe story
    }

    @Test
    fun existingStory_UpdateStory_ReturnUpdatedStory() = runTest {
        val storyId = 1
        val existingStory = StoryEntity(
            storyId = storyId,
            headline = "some-headline",
            heroImageUrl = "https://some.hero.image/url",
            heroImageAccessibilityText = "some-accessibility-text",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
        )
        val newStory = StoryEntity(
            storyId = storyId,
            headline = "some-new-headline",
            heroImageUrl = "https://some.hero.image/new-url",
            heroImageAccessibilityText = "some-new-accessibility-text",
            creationDate = "2022-5-21T00:00:01Z",
            modifiedDate = "2022-5-21T00:00:01Z",
        )
        storyDao.insertStory(story = existingStory)

        storyDao.insertStory(story = newStory)

        val returnedStory = storyDao.getStory(storyId = storyId)
        returnedStory shouldBe newStory
    }

    @Test
    fun existingStory_DeleteStory_ReturnNullTitle() = runTest {
        val storyId = 1
        val story = StoryEntity(
            storyId = storyId,
            headline = "some-headline",
            heroImageUrl = "https://some.hero.image/url",
            heroImageAccessibilityText = "some-accessibility-text",
            creationDate = "2022-5-21T00:00:00Z",
            modifiedDate = "2022-5-21T00:00:00Z",
        )
        storyDao.insertStory(story = story)

        storyDao.deleteStory(storyId = storyId)

        val returnedStory = storyDao.getStory(storyId = storyId)
        returnedStory shouldBe null
    }

    /**
     * Content
     */
    @Test
    fun emptyTable_InsertEmptyContentsList_ReturnEmptyList() = runTest {
        val storyId = 1
        val contents = emptyList<ContentEntity>()
        storyDao.getContents(storyId = storyId) shouldBe emptyList()

        storyDao.insertContents(contents = contents)

        val returnedContents = storyDao.getContents(storyId = storyId)
        returnedContents shouldBe emptyList()
    }

    @Test
    fun emptyTable_InsertOneContentEntity_ReturnOneContentEntity() = runTest {
        val storyId = 1
        val contents = listOf(
            ContentEntity(
                sequenceId = 1,
                storyId = storyId,
                type = "some-type",
                url = "https://some.url/",
                accessibilityText = "some-accessibility-text",
                text = "some-text",
            ),
        )
        storyDao.getContents(storyId = storyId) shouldBe emptyList()

        storyDao.insertContents(contents = contents)

        val returnedContents = storyDao.getContents(storyId = storyId)
        returnedContents shouldBe contents
    }

    @Test
    fun emptyTable_InsertMultipleContentEntities_ReturnMultipleContentEntities() = runTest {
        val storyId = 1
        val contents = listOf(
            ContentEntity(
                sequenceId = 1,
                storyId = storyId,
                type = "some-type-1",
                url = "https://some.url/1",
                accessibilityText = "some-accessibility-text-1",
                text = "some-text-1",
            ),
            ContentEntity(
                sequenceId = 2,
                storyId = storyId,
                type = "some-type-2",
                url = "https://some.url/2",
                accessibilityText = "some-accessibility-text-2",
                text = "some-text-2",
            ),
            ContentEntity(
                sequenceId = 3,
                storyId = storyId,
                type = "some-type-3",
                url = "https://some.url/3",
                accessibilityText = "some-accessibility-text-3",
                text = "some-text-3",
            ),
        )
        storyDao.getContents(storyId = storyId) shouldBe emptyList()

        storyDao.insertContents(contents = contents)

        val returnedContents = storyDao.getContents(storyId = storyId)
        returnedContents shouldContainExactlyInAnyOrder contents
    }

    @Test
    fun existingContents_ReplaceContentEntity_ReturnUpdatedContents() = runTest {
        val storyId = 1
        val contentEntityOne = ContentEntity(
            sequenceId = 1,
            storyId = storyId,
            type = "some-type-1",
            url = "https://some.url/1",
            accessibilityText = "some-accessibility-text-1",
            text = "some-text-1",
        )
        val contentEntityTwo = ContentEntity(
            sequenceId = 2,
            storyId = storyId,
            type = "some-type-2",
            url = "https://some.url/2",
            accessibilityText = "some-accessibility-text-2",
            text = "some-text-2",
        )
        val contentEntityOneModified = ContentEntity(
            sequenceId = 1,
            storyId = storyId,
            type = "some-type-3",
            url = "https://some.url/3",
            accessibilityText = "some-accessibility-text-3",
            text = "some-text-3",
        )
        val contents = listOf(contentEntityOne, contentEntityTwo)
        storyDao.insertContents(contents = contents)

        storyDao.insertContents(contents = listOf(contentEntityOneModified))

        val returnedContents = storyDao.getContents(storyId = storyId)
        returnedContents shouldContainExactlyInAnyOrder listOf(
            contentEntityOneModified,
            contentEntityTwo,
        )
    }

    @Test
    fun existingContents_DeleteByStoryId_ReturnEmptyContents() = runTest {
        val storyId = 1
        val contentEntityOne = ContentEntity(
            sequenceId = 1,
            storyId = storyId,
            type = "some-type-1",
            url = "https://some.url/1",
            accessibilityText = "some-accessibility-text-1",
            text = "some-text-1",
        )
        val contentEntityTwo = ContentEntity(
            sequenceId = 2,
            storyId = storyId,
            type = "some-type-2",
            url = "https://some.url/2",
            accessibilityText = "some-accessibility-text-2",
            text = "some-text-2",
        )
        val contents = listOf(contentEntityOne, contentEntityTwo)
        storyDao.insertContents(contents = contents)

        storyDao.deleteContents(storyId = storyId)

        val returnedContents = storyDao.getContents(storyId = storyId)
        returnedContents shouldBe emptyList()
    }
}
