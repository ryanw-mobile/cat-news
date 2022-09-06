/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.data.remote

import uk.ryanwong.skycatnews.newslist.data.remote.model.LinksDto
import uk.ryanwong.skycatnews.newslist.data.remote.model.NewsItemDto
import uk.ryanwong.skycatnews.newslist.data.remote.model.NewsListDto
import uk.ryanwong.skycatnews.newslist.data.remote.model.TeaserImageDto
import uk.ryanwong.skycatnews.newslist.data.remote.model.UrlDto

internal object NewsListServiceTestData {

    const val mockJsonResponse = """{
   "title":"Sky Cat News",
   "data":[
      {
         "id":"1",
         "type":"story",
         "headline":"Story Headline",
         "teaserText":"Story teaser text",
         "creationDate":"2020-11-18T00:00:00Z",
         "modifiedDate":"2020-11-19T00:00:00Z",
         "teaserImage":{
            "_links":{
               "url":{
                  "href":"",
                  "templated":true,
                  "type":"image/jpeg"
               }
            },
            "accessibilityText":"Image content description"
         }
      },
      {
         "id":"2",
         "type":"story",
         "headline":"Story Headline",
         "teaserText":"Story teaser text",
         "creationDate":"2020-11-18T00:00:00Z",
         "modifiedDate":"2020-11-19T00:00:00Z",
         "teaserImage":{
            "_links":{
               "url":{
                  "href":"",
                  "templated":true,
                  "type":"image/jpeg"
               }
            },
            "accessibilityText":"Image content description"
         }
      },
      {
         "type":"advert",
         "url":"advert/url"
      },
      {
         "id":"3",
         "type":"weblink",
         "headline":"Weblink headline",
         "weblinkUrl":"weblink url",
         "creationDate":"2020-11-18T00:00:00Z",
         "modifiedDate":"2020-11-19T00:00:00Z",
         "teaserImage":{
            "_links":{
               "url":{
                  "href":"",
                  "templated":true,
                  "type":"image/jpeg"
               }
            },
            "accessibilityText":"Image content description"
         }
      },
      {
         "id":"4",
         "type":"story",
         "headline":"Story headline",
         "teaserText":"Story teaser text",
         "creationDate":"2020-11-18T00:00:00Z",
         "modifiedDate":"2020-11-19T00:00:00Z",
         "teaserImage":{
            "_links":{
               "url":{
                  "href":"",
                  "templated":true,
                  "type":"image/jpeg"
               }
            },
            "accessibilityText":"Image content description"
         }
      },
      {
         "id":"5",
         "type":"weblink",
         "headline":"Weblink headline",
         "weblinkUrl":"",
         "creationDate":"2020-11-18T00:00:00Z",
         "modifiedDate":"2020-11-19T00:00:00Z",
         "teaserImage":{
            "_links":{
               "url":{
                  "href":"",
                  "templated":true,
                  "type":"image/jpeg"
               }
            },
            "accessibilityText":"Image content description"
         }
      }
   ]
}
"""

    val mockNewsListDto by lazy {
        NewsListDto(
            news = listOf(
                NewsItemDto(
                    creationDate = "2020-11-18T00:00:00Z",
                    headline = "Story Headline",
                    id = 1,
                    modifiedDate = "2020-11-19T00:00:00Z",
                    teaserImage = TeaserImageDto(
                        links = LinksDto(
                            url = UrlDto(
                                href = "",
                                templated = true,
                                type = "image/jpeg"
                            )
                        ),
                        accessibilityText = "Image content description"
                    ),
                    teaserText = "Story teaser text",
                    type = "story",
                    advertUrl = null,
                    weblinkUrl = null
                ),
                NewsItemDto(
                    creationDate = "2020-11-18T00:00:00Z",
                    headline = "Story Headline",
                    id = 2,
                    modifiedDate = "2020-11-19T00:00:00Z",
                    teaserImage = TeaserImageDto(
                        links = LinksDto(
                            url = UrlDto(
                                href = "",
                                templated = true,
                                type = "image/jpeg"
                            )
                        ),
                        accessibilityText = "Image content description"
                    ),
                    teaserText = "Story teaser text",
                    type = "story",
                    advertUrl = null,
                    weblinkUrl = null
                ),
                NewsItemDto(
                    creationDate = null,
                    headline = null,
                    id = null,
                    modifiedDate = null,
                    teaserImage = null,
                    teaserText = null,
                    type = "advert",
                    advertUrl = "advert/url",
                    weblinkUrl = null
                ),
                NewsItemDto(
                    creationDate = "2020-11-18T00:00:00Z",
                    headline = "Weblink headline",
                    id = 3,
                    modifiedDate = "2020-11-19T00:00:00Z",
                    teaserImage = TeaserImageDto(
                        links = LinksDto(
                            url = UrlDto(
                                href = "",
                                templated = true,
                                type = "image/jpeg"
                            )
                        ),
                        accessibilityText = "Image content description"
                    ),
                    teaserText = null,
                    type = "weblink",
                    advertUrl = null,
                    weblinkUrl = "weblink url"
                ),
                NewsItemDto(
                    creationDate = "2020-11-18T00:00:00Z",
                    headline = "Story headline",
                    id = 4,
                    modifiedDate = "2020-11-19T00:00:00Z",
                    teaserImage = TeaserImageDto(
                        links = LinksDto(
                            url = UrlDto(
                                href = "",
                                templated = true,
                                type = "image/jpeg"
                            )
                        ),
                        accessibilityText = "Image content description"
                    ),
                    teaserText = "Story teaser text",
                    type = "story",
                    advertUrl = null,
                    weblinkUrl = null
                ),
                NewsItemDto(
                    creationDate = "2020-11-18T00:00:00Z",
                    headline = "Weblink headline",
                    id = 5,
                    modifiedDate = "2020-11-19T00:00:00Z",
                    teaserImage = TeaserImageDto(
                        links = LinksDto(
                            url = UrlDto(
                                href = "",
                                templated = true,
                                type = "image/jpeg"
                            )
                        ),
                        accessibilityText = "Image content description"
                    ),
                    teaserText = null,
                    type = "weblink",
                    advertUrl = null,
                    weblinkUrl = ""
                )
            ),
            title = "Sky Cat News"
        )
    }
}
