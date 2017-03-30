package junit.org.rapidpm.binarycache.provider.hazelcast.v001;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;
import javax.inject.Inject;

import java.util.Optional;

import static org.junit.Assert.*;

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
 * Created by m.lang - RapidPM - Team on 30.03.2017.
 */
public class HazelcastCacheImplTest001 {

  public static final String CACHE_NAME = "default";
  @Inject
  BinaryCacheClient cacheClient;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    final Cache<CacheKey, CacheByteArray> cache = cacheClient.getCache("default");
    assertNotNull(cache);
  }

  @Test
  public void test002() throws Exception {
    final DefaultCacheKey key = new DefaultCacheKey("002");
    final CacheByteArray value = new CacheByteArray("test".getBytes());

    final Result cacheBinary = cacheClient.cacheBinary(CACHE_NAME, key, value);
    assertEquals(Result.OK, cacheBinary);

    final Optional<CacheByteArray> cachedElement = cacheClient.getCachedElement(CACHE_NAME, key);
    assertTrue(cachedElement.isPresent());
    assertEquals("test", new String(cachedElement.get().byteArray));

    final Result removeEntry = cacheClient.removeEntry(CACHE_NAME, key);
    assertEquals(Result.OK, removeEntry);

    final Optional<CacheByteArray> removedElement = cacheClient.getCachedElement(CACHE_NAME, key);
    assertFalse(removedElement.isPresent());
  }
}
