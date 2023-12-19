/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ryanwong.catnews.newslist.data.local.entity.NewsItemEntity
import uk.ryanwong.catnews.newslist.data.local.entity.NewsListEntity

@Dao
interface NewsListDao {
    @Query("SELECT title FROM news_list WHERE list_id = :listId LIMIT 1")
    suspend fun getNewsListTitle(listId: Int): String?

    @Query("SELECT * FROM news_items WHERE list_id = :listId ORDER BY modified_date DESC")
    suspend fun getNewsList(listId: Int): List<NewsItemEntity>

    @Query("SELECT * FROM news_items WHERE list_id = :listId AND news_id = :newsId LIMIT 1")
    suspend fun getNewsItem(listId: Int, newsId: Int): NewsItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsItems(newsItems: List<NewsItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsListTitle(newsListEntity: NewsListEntity)

    @Query("DELETE FROM news_list WHERE list_id = :listId ")
    suspend fun deleteListTitle(listId: Int)

    @Query("DELETE FROM news_items WHERE list_id = :listId ")
    suspend fun deleteNewsItems(listId: Int)
}
