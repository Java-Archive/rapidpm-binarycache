package junit.org.rapidpm.binarycache.client.connect.inmemory;

import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;
import javax.inject.Inject;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class BinaryCacheInmemoryClientTest {

  public static final String DEFAULT_CACHE = "default";
  @Inject
  BinaryCacheClient client;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @Test
  public void test001() throws Exception {
    final Cache<CacheKey, CacheByteArray> test = client.createCache("test");
    assertNotNull(test);
    final Cache<CacheKey, CacheByteArray> cache = client.getCache(DEFAULT_CACHE);
    assertNotNull(cache);
  }

  @Test
  public void test003() throws Exception {
    final DefaultCacheKey key = new DefaultCacheKey("123");
    final byte[] value = "test".getBytes();

    client.cacheBinary(DEFAULT_CACHE, key, new CacheByteArray(value));
    final Optional<CacheByteArray> cachedElement = client.getCachedElement(DEFAULT_CACHE, key);

    assertTrue(cachedElement.isPresent());
  }
}