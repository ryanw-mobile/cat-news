/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_list")
data class NewsListEntity(
    @PrimaryKey
    @ColumnInfo(name = "list_id")
    val listId: Int,
    val title: String,
)
