/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whatsthenews.mapper

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ApiSuccessModelMapper
import com.whatsthenews.model.News
import com.whatsthenews.model.NewsResponse

object NewsResponseMapper : ApiSuccessModelMapper<NewsResponse, List<News>> {

  override fun map(apiErrorResponse: ApiResponse.Success<NewsResponse>): List<News> {
    val newsList = mutableListOf<News>()
    apiErrorResponse.data?.articles?.forEach {
      val news = News(
             title = it.title?: "",
             description = it.description?: "",
              imageUrl = it.urlToImage?: "",
              newsUrl = it.url?: "")
      newsList.add(news)
    }
    return newsList
  }
}
