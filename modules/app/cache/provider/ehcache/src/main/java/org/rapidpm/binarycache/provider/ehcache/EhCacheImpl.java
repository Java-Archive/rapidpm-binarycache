package org.rapidpm.binarycache.provider.ehcache;

import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by RapidPM - Team on 09.03.2017.
 */
public class EhCacheImpl implements BinaryCacheClient {

  public static final String CONFIG_EHCACHE_XML = "/config/ehcache.xml";
  private javax.cache.CacheManager cacheManager;

  @PostConstruct
  public void init() throws URISyntaxException {
    final CachingProvider cachingProvider = Caching.getCachingProvider();
    cacheManager = cachingProvider.getCacheManager(
        getClass().getResource(CONFIG_EHCACHE_XML).toURI(),
        getClass().getClassLoader()
    );
  }

  @PreDestroy
  public void destroy() {
    cacheManager.close();
  }

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(String cacheName) {
    return cacheManager.getCache(cacheName, CacheKey.class, CacheByteArray.class);
  }

  @Override
  public Result removeEntry(String cacheName, CacheKey cacheKey) {
    return cacheManager.getCache(cacheName, CacheKey.class, CacheByteArray.class).remove(cacheKey) ? Result.OK : Result.FAILED;
  }



}
