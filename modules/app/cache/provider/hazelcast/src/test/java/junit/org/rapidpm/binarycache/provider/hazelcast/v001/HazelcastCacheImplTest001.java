package junit.org.rapidpm.binarycache.provider.hazelcast.v001;

import com.hazelcast.cache.CacheNotExistsException;
import com.hazelcast.core.Hazelcast;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.binarycache.provider.hazelcast.HazelcastCacheImpl;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;
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

  private static final String CACHE_NAME = "default";
  private HazelcastCacheImpl client;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    client = DI.activateDI(HazelcastCacheImpl.class);
  }

  @After
  public void tearDown() throws Exception {
    Hazelcast.getHazelcastInstanceByName(client.getInstanceName()).shutdown();
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    final Cache<CacheKey, CacheByteArray> cache = client.getCache("default");
    assertNotNull(cache);
  }

  @Test
  public void test002() throws Exception {
    final DefaultCacheKey key = new DefaultCacheKey("002");
    final CacheByteArray value = new CacheByteArray("test".getBytes());

    final Result cacheBinary = client.cacheBinary(CACHE_NAME, key, value);
    assertEquals(Result.OK, cacheBinary);

    final Optional<CacheByteArray> cachedElement = client.getCachedElement(CACHE_NAME, key);
    assertTrue(cachedElement.isPresent());
    assertEquals("test", new String(cachedElement.get().byteArray));

    final Result removeEntry = client.removeEntry(CACHE_NAME, key);
    assertEquals(Result.OK, removeEntry);

    final Optional<CacheByteArray> removedElement = client.getCachedElement(CACHE_NAME, key);
    assertFalse(removedElement.isPresent());
  }

  @Test(expected = CacheNotExistsException.class)
  public void test003() throws Exception {
    final Cache<CacheKey, CacheByteArray> cache = client.getCache("notThere");
  }

  @Test(expected = UnsupportedOperationException.class)
  public void test004() throws Exception {
    client.createCache("newCache");
  }
}
