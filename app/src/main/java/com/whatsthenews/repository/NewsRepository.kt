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

package com.whatsthenews.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import com.whatsthenews.mapper.ErrorResponseMapper
import com.whatsthenews.mapper.NewsResponseMapper
import com.whatsthenews.network.NewsClient
import com.whatsthenews.persistence.NewsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(
  private val newsClient: NewsClient,
  private val newsDao: NewsDao,
) : Repository {

  @WorkerThread
  fun fetchNewsList(
      country: String = "id",
    onSuccess: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
      var newsList = newsDao.getNewsList()
      if (newsList.isEmpty()) {
          val response = newsClient.fetchNewsList(country)
          response.suspendOnSuccess {
              data.whatIfNotNull { response ->
                  newsList = map(NewsResponseMapper)
                  newsDao.deleteAllNews()
                  newsDao.insertNewsList(newsList)
                  emit(newsDao.getNewsList())
                  onSuccess()
              }
          }
              .onError {
                  map(ErrorResponseMapper) { onError("[Code: $code]: $message") }
              }
              .onException { onError(message) }
      } else {
          emit(newsDao.getNewsList())
          onSuccess()
      }
  }.flowOn(Dispatchers.IO)
}
