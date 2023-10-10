/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uk.ryanwong.skycatnews.storydetail.data.remote.model.StoryDto

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "story_id")
    val storyId: Int,
    val headline: String?,
    @ColumnInfo(name = "hero_image_url")
    val heroImageUrl: String?,
    @ColumnInfo(name = "hero_image_accessibility_text")
    val heroImageAccessibilityText: String?,
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    @ColumnInfo(name = "modified_date")
    val modifiedDate: String,
) {
    companion object {
        fun fromDto(storyDto: StoryDto): StoryEntity {
            with(storyDto) {
                return StoryEntity(
                    storyId = id,
                    headline = headline,
                    heroImageUrl = heroImage?.imageUrl,
                    heroImageAccessibilityText = heroImage?.accessibilityText,
                    creationDate = creationDate,
                    modifiedDate = modifiedDate,
                )
            }
        }
    }
}
