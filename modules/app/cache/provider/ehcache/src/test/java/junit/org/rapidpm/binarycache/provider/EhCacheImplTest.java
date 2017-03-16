package junit.org.rapidpm.binarycache.provider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;
import org.rapidpm.binarycache.provider.ehcache.EhCacheImpl;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

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
 * Created by RapidPM - Team on 09.03.2017.
 */
public class EhCacheImplTest {

  public static final String TEST_CACHE = "testCache";


  @Inject
  EhCacheImpl cacheClient;
  public static final Byte[] BYTES = new Byte[]{Byte.valueOf("123")};

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
    //cacheClient.init();
    cacheClient.createCache(TEST_CACHE);
  }

  @After
  public void tearDown() throws Exception {
    cacheClient.clearCache(TEST_CACHE);
    cacheClient.getCache(TEST_CACHE).close();
  }

  @Test
  public void test001() throws Exception {
    final Cache<CacheKey, Byte[]> cache = cacheClient.getCache(TEST_CACHE);
    assertNotNull(cache);
  }

  @Test
  public void test002() throws Exception {
    final Result result = cacheClient.cacheBinary(TEST_CACHE, new SimpleCacheKey(), BYTES);
    assertEquals(Result.OK, result);
  }

  @Test
  public void test003() throws Exception {
    final SimpleCacheKey cacheKey = new SimpleCacheKey();
    final Result result01 = cacheClient.cacheBinary(TEST_CACHE, cacheKey, BYTES);
    assertEquals(Result.OK, result01);

    final Result result02 = cacheClient.cacheBinaryIfAbsent(TEST_CACHE, cacheKey, BYTES);
    assertEquals(Result.OK, result02);
  }

  @Test
  public void test004_a() throws Exception {
    final SimpleCacheKey cacheKey = new SimpleCacheKey();
    final Result resultPut = cacheClient.cacheBinary(TEST_CACHE, cacheKey, BYTES);
    assertEquals(Result.OK, resultPut);

    final Optional<Byte[]> cachedElement = cacheClient.getCachedElement(TEST_CACHE, cacheKey);
    assertTrue(cachedElement.isPresent());

    final Optional<Byte[]> notCachedElement = cacheClient.getCachedElement(TEST_CACHE, new SimpleCacheKey());
    assertFalse(notCachedElement.isPresent());
  }

  @Test
  public void test004_b() throws Exception {
    final SimpleCacheKey cacheKey = new SimpleCacheKey();
    final Result resultPut = cacheClient.cacheBinary(TEST_CACHE, cacheKey, BYTES);
    assertEquals(Result.OK, resultPut);

    final Result resultRemove = cacheClient.removeEntry(TEST_CACHE, cacheKey);
    assertEquals(Result.OK, resultRemove);

    final Optional<Byte[]> cachedElement = cacheClient.getCachedElement(TEST_CACHE, cacheKey);
    assertFalse(cachedElement.isPresent());
  }

}

class SimpleCacheKey implements CacheKey {

  private String key;

  public SimpleCacheKey() {
    this.key = UUID.randomUUID().toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SimpleCacheKey that = (SimpleCacheKey) o;

    return key != null ? key.equals(that.key) : that.key == null;
  }

  @Override
  public int hashCode() {
    return key != null ? key.hashCode() : 0;
  }

  @Override
  public String keyAsString() {
    return this.key;
  }
}