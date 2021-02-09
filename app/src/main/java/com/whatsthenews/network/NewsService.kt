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

package com.whatsthenews.network

import com.skydoves.sandwich.ApiResponse
import com.whatsthenews.model.NewsResponse
import com.whatsthenews.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

  @GET("/v2/top-headlines")
  suspend fun fetchTopHeadlines(
          @Query("country") country: String = "id",
          @Query("apiKey") apiKey: String = "8d905cdaa8a24cc989d4b10b44e11fb9"
  ): ApiResponse<NewsResponse>
}
