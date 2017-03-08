package org.rapidpm.binarycache.connect.rest;

import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;

import javax.cache.Cache;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Copyright (C) 2017 RapidPM - Sven Ruppert
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Sven Ruppert - RapidPM - Team on 08.03.17.
 */
public class BinaryCacheRestClient {

  private @Inject BinaryCacheClient binaryCacheClient;


  public Result cacheBinary(final String cacheName, final CacheKey cacheKey, final Byte[] binary) {
    return binaryCacheClient.cacheBinary(cacheName, cacheKey, binary);
  }

  public Result cacheBinaryIfAbsent(final String cacheName, final CacheKey cacheKey, final Byte[] binary) {
    return binaryCacheClient.cacheBinaryIfAbsent(cacheName, cacheKey, binary);
  }

  public Optional<Byte[]> getCachedElement(final String cacheName, final CacheKey cacheKey) {
    return binaryCacheClient.getCachedElement(cacheName, cacheKey);
  }

  public Result clearCache(final String cacheName) {
    return binaryCacheClient.clearCache(cacheName);
  }

  public Result removeEntry(final String cacheName, final CacheKey cacheKey) {
    return binaryCacheClient.removeEntry(cacheName, cacheKey);
  }
}
