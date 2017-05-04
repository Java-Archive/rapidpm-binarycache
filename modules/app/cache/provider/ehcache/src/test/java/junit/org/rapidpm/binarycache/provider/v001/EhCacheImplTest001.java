package junit.org.rapidpm.binarycache.provider.v001;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.provider.ehcache.EhCacheImpl;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;
import javax.inject.Inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
 * Created by m.lang - RapidPM - Team on 04.05.2017.
 */
public class EhCacheImplTest001 {

  @Inject
  BinaryCacheClient cacheClient;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    DI.activateDI(this);
    final Cache<CacheKey, CacheByteArray> cache01 = cacheClient.getCache("default");
    assertNotNull(cache01);
    final Cache<CacheKey, CacheByteArray> cache02 = cacheClient.getCache("notThere");
    assertNull(cache02);
  }

  @Test
  public void test002() throws Exception {
    final String path = EhCacheImplTest001.class.getResource("ehcache.xml").getPath();
    System.setProperty(EhCacheImpl.CACHE_CONFIG_PROPERTY, path);
    DI.activateDI(this);
    final Cache<CacheKey, CacheByteArray> cache01 = cacheClient.getCache("default");
    assertNull(cache01);
    final Cache<CacheKey, CacheByteArray> cache02 = cacheClient.getCache("myCache");
    assertNotNull(cache02);

  }
}
