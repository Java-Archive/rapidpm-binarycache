package org.rapidpm.binarycache.provider.hazelcast;

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

  public static final String CONFIG_FILENAME = "/config/hazelcast.xml";
  private HazelcastInstance instance;

  @PostConstruct
  public void init() throws FileNotFoundException {
    final File file = new File(getClass().getResource(CONFIG_FILENAME).getFile());
    final Config config = new FileSystemXmlConfig(file);
    instance = Hazelcast.newHazelcastInstance(config);
  }

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(String cacheName) {
    return instance.getCacheManager().getCache(cacheName);
  }
}
