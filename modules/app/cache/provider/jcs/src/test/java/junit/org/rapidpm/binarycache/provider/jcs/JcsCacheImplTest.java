package junit.org.rapidpm.binarycache.provider.jcs;

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
/*

public class JcsCacheImplTest {

  private static final String TEST_CACHE = "testCache";
  private static final String TEST_STRING = "123";

  @Inject
  JcsCacheImpl binaryCache;

  @Before
  public void setUp() throws Exception {
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    binaryCache.clearCache(TEST_CACHE);
  }

  @Test @Ignore // not implemented
  public void test001() throws Exception {
    final Cache<CacheKey, CacheByteArray> cache = binaryCache.getCache(TEST_CACHE);
    assertNotNull(cache);
    assertEquals(TEST_CACHE, cache.getName());
  }

  @Test
  public void test002() throws Exception {
    final CacheByteArray bytes = {Byte.decode(TEST_STRING)};
    final SimpleCacheKey cacheKey = new SimpleCacheKey();
    final Result result = binaryCache.cacheBinary(TEST_CACHE, cacheKey, bytes);
    assertEquals(Result.OK, result);

    final Optional<CacheByteArray> cachedElement = binaryCache.getCachedElement(TEST_CACHE, cacheKey);
    assertTrue(cachedElement.isPresent());

    final CacheByteArray byteResult = cachedElement.get();
    assertTrue(bytes.equals(byteResult));
    assertEquals(TEST_STRING, byteResult[0].toString());
  }


  @Test
  public void test003() throws Exception {
    final CacheByteArray bytes = {Byte.valueOf(TEST_STRING)};
    final Result result = binaryCache.cacheBinaryIfAbsent(TEST_CACHE, new SimpleCacheKey(), bytes);
    assertEquals(Result.OK, result);
  }

  @Test
  public void test004() throws Exception {
    final CacheByteArray bytes = {Byte.valueOf(TEST_STRING)};
    final SimpleCacheKey cacheKey = new SimpleCacheKey();
    final Result putResult = binaryCache.cacheBinary(TEST_CACHE, cacheKey, bytes);
    assertEquals(Result.OK, putResult);

    final Result clearResult = binaryCache.clearCache(TEST_CACHE);
    assertEquals(Result.OK, clearResult);

    final Optional<CacheByteArray> cachedElement = binaryCache.getCachedElement(TEST_CACHE, cacheKey);
    assertFalse(cachedElement.isPresent());
  }

}

class SimpleCacheKey implements CacheKey {

  private String key;

  public SimpleCacheKey() {
    this.key = UUID.randomUUID().toString();
  }

  @Override
  public String keyAsString() {
    return this.key;
  }
}
*/
