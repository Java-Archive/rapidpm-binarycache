package junit.org.rapidpm.binarycache.client.connect.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.binarycache.client.connect.rest.BinaryCacheRestClient;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;

import javax.cache.Cache;
import java.io.File;
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
 * Created by m.lang - RapidPM - Team on 04.04.2017.
 */
public class BinaryCacheRestClientTest001 {

  public static final String CACHE_NAME = "default";
  private BinaryCacheRestClient client;
  private File value;

  @BeforeClass
  public static void setUpClass() {
    System.setProperty(MainUndertow.REST_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
  }

  @Before
  public void setUp() throws Exception {
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    DI.activateDI(this);
    Main.deploy();
    client = new BinaryCacheRestClient(Integer.valueOf(System.getProperty(MainUndertow.REST_PORT_PROPERTY)));
    DI.activateDI(client);
    value = new File(this.getClass().getResource("testpic.png").toURI());
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test(expected = java.lang.RuntimeException.class)
  public void test001() throws Exception {
    final Cache<CacheKey, CacheByteArray> cache = client.getCache(CACHE_NAME);
    assertNotNull(cache);
  }

  @Test
  public void test002() throws Exception {
    final Result result = client.cacheBinary(CACHE_NAME, new DefaultCacheKey("123"), new CacheByteArray("123".getBytes()));
    assertEquals(Result.OK, result);
  }

  @Test
  public void test003() throws Exception {
    final DefaultCacheKey key = new DefaultCacheKey("123");
    final CacheByteArray value = new CacheByteArray("123".getBytes());
    client.cacheBinary(CACHE_NAME, key, value);

    final Optional<CacheByteArray> cachedElement = client.getCachedElement(CACHE_NAME, key);
    assertTrue(cachedElement.isPresent());
    assertEquals(new String(value.byteArray), new String(cachedElement.get().byteArray));
  }

  @Test
  public void test004() throws Exception {
    final DefaultCacheKey key = new DefaultCacheKey("notThere");

    final Optional<CacheByteArray> cachedElement = client.getCachedElement(CACHE_NAME, key);
    assertFalse(cachedElement.isPresent());
  }

  @Test
  public void test005() throws Exception {
    final Result result = client.clearCache(CACHE_NAME);
    assertEquals(Result.OK, result);
  }
}

