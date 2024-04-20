/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote

import uk.ryanwong.catnews.data.dto.ContentDto
import uk.ryanwong.catnews.data.dto.HeroImageDto
import uk.ryanwong.catnews.data.dto.StoryDto

internal object StoryServiceTestData {

    const val SAMPLE_JAPANESE_RESPONSE = """{
   "id":"1",
   "headline":"Cat story headline",
   "heroImage":{
      "imageUrl":"",
      "accessibilityText":"Image content description"
   },
   "creationDate":"2020-11-18T00:00:00Z",
   "modifiedDate":"2020-11-19T00:00:00Z",
   "contents":[
      {
         "type":"paragraph",
         "text":"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
      },
      {
         "type":"paragraph",
         "text":"Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
      },
      {
         "type":"image",
         "url":"",
         "accessibilityText":""
      },
      {
         "type":"paragraph",
         "text":"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo."
      },
      {
         "type":"image",
         "url":"",
         "accessibilityText":""
      },
      {
         "type":"paragraph",
         "text":"Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt."
      }
   ]
}
"""

    val storyDto = StoryDto(
        contents = listOf(
            ContentDto(
                accessibilityText = null,
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
                type = "paragraph",
                url = null,
            ),
            ContentDto(
                accessibilityText = null,
                text = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                type = "paragraph",
                url = null,
            ),
            ContentDto(accessibilityText = "", text = null, type = "image", url = ""),
            ContentDto(
                accessibilityText = null,
                text = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.",
                type = "paragraph",
                url = null,
            ),
            ContentDto(accessibilityText = "", text = null, type = "image", url = ""),
            ContentDto(
                accessibilityText = null,
                text = "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.",
                type = "paragraph",
                url = null,
            ),
        ),
        creationDate = "2020-11-18T00:00:00Z",
        headline = "Cat story headline",
        heroImage = HeroImageDto(
            accessibilityText = "Image content description",
            imageUrl = "",
        ),
        id = 1,
        modifiedDate = "2020-11-19T00:00:00Z",
    )
}
