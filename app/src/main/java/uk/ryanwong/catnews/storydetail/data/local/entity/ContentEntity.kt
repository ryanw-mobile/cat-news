/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.ryanwong.catnews.storydetail.data.remote.model.ContentDto

@Entity(tableName = "contents")
data class ContentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sequence_id")
    val sequenceId: Int = 0,
    @ColumnInfo(name = "story_id")
    val storyId: Int,
    val type: String?,
    val url: String?,
    @ColumnInfo(name = "accessibility_text")
    val accessibilityText: String?,
    val text: String?,
) {
    companion object {
        fun fromDto(storyId: Int, contentDtoList: List<ContentDto>?): List<ContentEntity> {
            return contentDtoList?.map { content ->
                with(content) {
                    ContentEntity(
                        storyId = storyId,
                        type = type,
                        url = url,
                        accessibilityText = accessibilityText,
                        text = text,
                    )
                }
            } ?: emptyList()
        }
    }
}
