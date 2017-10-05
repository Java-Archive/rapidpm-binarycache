package org.rapidpm.binarycache.provider.ehcache;

import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.io.File;
import java.net.URI;
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

  public static final String CACHE_CONFIG_PROPERTY = "binarycache.config";
  private static final String CONFIG_EHCACHE_XML = "/config/ehcache.xml";
  private CacheManager manager;

  @PostConstruct
  public void init() throws URISyntaxException {
    final CachingProvider cachingProvider = Caching.getCachingProvider();
    manager = cachingProvider.getCacheManager(
        getUriToConfig(),
        getClass().getClassLoader()
    );
  }

  private URI getUriToConfig() throws URISyntaxException {
    final String property = System.getProperty(CACHE_CONFIG_PROPERTY);
    URI uri;
    if (property != null && !property.isEmpty()) {
      uri = new File(property).toURI();
    } else {
      uri = getClass().getResource(CONFIG_EHCACHE_XML).toURI();
    }
    return uri;
  }

  @PreDestroy
  public void destroy() {
    manager.close();
  }

  @Override
  public Cache<CacheKey, CacheByteArray> createCache(String cacheName) {
    MutableConfiguration<CacheKey, CacheByteArray> config
        = new MutableConfiguration<CacheKey, CacheByteArray>()
        .setTypes(CacheKey.class, CacheByteArray.class)
        .setExpiryPolicyFactory(
            AccessedExpiryPolicy.factoryOf(Duration.ONE_HOUR));
    return manager.createCache(cacheName, config);
  }

  @Override
  public Cache<CacheKey, CacheByteArray> createCache(String cacheName, Configuration<CacheKey, CacheByteArray> configuration) {
    return manager.createCache(cacheName, configuration);
  }

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(String cacheName) {
    return manager.getCache(cacheName, CacheKey.class, CacheByteArray.class);
  }

  @Override
  public Result removeEntry(String cacheName, CacheKey cacheKey) {
    return manager.getCache(cacheName, CacheKey.class, CacheByteArray.class).remove(cacheKey) ? Result.OK : Result.FAILED;
  }

}
