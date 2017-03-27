package org.rapidpm.binarycache.client.connect.rest;

import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;

import javax.cache.Cache;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
 * Created by Sven Ruppert - RapidPM - Team on 07.03.17.
 */
public class BinaryCacheRestClient implements BinaryCacheClient {

  //create REST request methods , delegate to binaryCacheClient

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(final String cacheName) {
    throw new RuntimeException("not allowed remote");
  }

  @Override
  public Result cacheBinary(final String cacheName, final CacheKey cacheKey, final CacheByteArray binary) {
    return null;
  }

  @Override
  public Result cacheBinaryIfAbsent(final String cacheName, final CacheKey cacheKey, final CacheByteArray binary) {
    return null;
  }

  @Override
  public Optional<CacheByteArray> getCachedElement(final String cacheName, final CacheKey cacheKey) {
    return null;
  }

  @Override
  public Result clearCache(final String cacheName) {
    Client client = ClientBuilder.newClient();
    final String generateBasicReqURL = "http://" + "127.0.0.1" + ":" + "8090" + "/" + "rest" + "/" + "REST-APP" + "/" + "params";
    String val = client
        .target(generateBasicReqURL)
        .request()
        .get(String.class);
    client.close();

    //may check something...
    return Result.OK;
  }

  @Override
  public Result removeEntry(final String cacheName, final CacheKey cacheKey) {
    return null;
  }


}
