package org.rapidpm.binarycache.provider.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import java.io.File;
import java.io.FileNotFoundException;

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
 * Created by RapidPM - Team on 30.03.2017.
 */
public class HazelcastCacheImpl implements BinaryCacheClient {

  public static final String CACHE_CONFIG_PROPERTY = "binarycache.config";
  private static final String CONFIG_FILENAME = "org/rapidpm/binarycache/provider/hazelcast/hazelcast.xml";
  private HazelcastInstance instance;

  @PostConstruct
  public void init() throws FileNotFoundException {
    final Config config = getConfig();
    instance = Hazelcast.newHazelcastInstance(config);
  }

  private Config getConfig() throws FileNotFoundException {
    final String property = System.getProperty(CACHE_CONFIG_PROPERTY);
    Config config;
    if (property != null && !property.isEmpty()) {
      final File file = new File(property);
      config = new FileSystemXmlConfig(file);
    } else {
      config = new ClasspathXmlConfig(CONFIG_FILENAME);
    }
    return config;
  }

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(String cacheName) {
    return instance.getCacheManager().getCache(cacheName);
  }

  @Override
  public Cache<CacheKey, CacheByteArray> createCache(String cacheName) {
    throw new UnsupportedOperationException();
  }

  public String getInstanceName() {
    return instance.getName();
  }

}
