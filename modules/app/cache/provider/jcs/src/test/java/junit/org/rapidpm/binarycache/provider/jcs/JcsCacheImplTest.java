package junit.org.rapidpm.binarycache.provider.jcs;

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
 * Created by RapidPM - Team on 09.03.2017.
 */
public class JcsCacheImplTest {

  private static final String TEST_CACHE = "default";
  private static final String TEST_STRING = "123";

  @Inject
  BinaryCacheClient binaryCache;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    binaryCache.clearCache(TEST_CACHE);
    DI.clearReflectionModel();
  }

  @Test(expected = java.lang.UnsupportedOperationException.class)
  public void test001() throws Exception {
    final Cache<CacheKey, CacheByteArray> cache = binaryCache.getCache(TEST_CACHE);
  }

  @Test
  public void test002() throws Exception {
    final CacheByteArray value = new CacheByteArray(TEST_STRING.getBytes());
    final DefaultCacheKey key = new DefaultCacheKey("002");
    final Result result = binaryCache.cacheBinary(TEST_CACHE, key, value);
    assertEquals(Result.OK, result);

    final Optional<CacheByteArray> cachedElement = binaryCache.getCachedElement(TEST_CACHE, key);
    assertTrue(cachedElement.isPresent());

    final CacheByteArray byteResult = cachedElement.get();
    assertTrue(value.equals(byteResult));
    assertEquals(TEST_STRING, new String(byteResult.getByteArray()));
  }


  @Test
  public void test003() throws Exception {
    final CacheByteArray value = new CacheByteArray(TEST_STRING.getBytes());
    final Result result = binaryCache.cacheBinaryIfAbsent(TEST_CACHE, new DefaultCacheKey("003"), value);
    assertEquals(Result.OK, result);
  }

  @Test
  public void test004() throws Exception {
    final CacheByteArray value = new CacheByteArray(TEST_STRING.getBytes());
    final DefaultCacheKey key = new DefaultCacheKey("004");
    final Result putResult = binaryCache.cacheBinary(TEST_CACHE, key, value);
    assertEquals(Result.OK, putResult);

    final Result clearResult = binaryCache.clearCache(TEST_CACHE);
    assertEquals(Result.OK, clearResult);

    final Optional<CacheByteArray> cachedElement = binaryCache.getCachedElement(TEST_CACHE, key);
    assertFalse(cachedElement.isPresent());
  }

}


