package junit.org.rapidpm.binarycache.connect.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.CacheKeyAdapter;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Base64;

import static org.junit.Assert.assertEquals;

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
 * Created by RapidPM - Team on 16.03.2017.
 */
public class BinaryCacheRestClientTest {

  @Inject
  private BinaryCacheClient binaryCacheClient;

  @Inject
  private CacheKeyAdapter adapter;
  private static String url;

  @BeforeClass
  public static void setUpClass() {
    System.setProperty(MainUndertow.REST_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    url = String.format("http://%s:%s/%s/%s",
        "127.0.0.1",
        System.getProperty(MainUndertow.REST_PORT_PROPERTY),
        MainUndertow.CONTEXT_PATH_REST,
        "cache");
  }

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
    binaryCacheClient.createCache("testcache");
    Main.deploy();

  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    final String testString = "my test string";

    final CacheKey cacheKey = new DefaultCacheKey(testString);
    final byte[] encodedKey = encodeKey(cacheKey);

    final String testUrl = String.format("%s/%s/%s",
        url,
        "testcache",
        new String(encodedKey));

    System.out.println(testUrl);

    final javax.ws.rs.core.Response response = ClientBuilder.newClient()
        .target(testUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(testString.getBytes(), MediaType.APPLICATION_OCTET_STREAM));

    assertEquals(200, response.getStatus());
  }

  private byte[] encodeKey(CacheKey cacheKey) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(CacheKey.class, adapter)
        .create();
    final String jsonString = gson.toJson(cacheKey, CacheKey.class);
    return Base64.getUrlEncoder().encode(jsonString.getBytes());
  }
}


