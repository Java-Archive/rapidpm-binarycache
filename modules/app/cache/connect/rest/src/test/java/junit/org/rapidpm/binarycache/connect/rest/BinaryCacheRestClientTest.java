package junit.org.rapidpm.binarycache.connect.rest;

import com.google.gson.Gson;
import org.junit.*;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Base64;
import java.util.UUID;

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

  private static String url;
  private final RestUtils restUtils = new RestUtils();

  @BeforeClass
  public static void setUpClass() {
    System.setProperty(MainUndertow.REST_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY, new PortUtils().nextFreePortForTest() + "");
    url = "http://127.0.0.1:" + System.getProperty(MainUndertow.SERVLET_PORT_PROPERTY) + MainUndertow.CONTEXT_PATH_REST; //from Annotation Servlet
    System.out.println("url = " + url);
  }

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
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

    final SimpleCacheKey cacheKey = new SimpleCacheKey();
    final String json = new Gson().toJson(cacheKey);
    final byte[] encodedKey = Base64.getUrlEncoder().encode(json.getBytes());

    final String testUrl = String.format("http://%s:%s/%s/%s/%s/%s",
        "127.0.0.1",
        System.getProperty(MainUndertow.REST_PORT_PROPERTY),
        MainUndertow.CONTEXT_PATH_REST,
        "cache",
        "testcache",
        new String(encodedKey));

    System.out.println(testUrl);

    final javax.ws.rs.core.Response response = ClientBuilder.newClient()
        .target(testUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(testString.getBytes(), MediaType.APPLICATION_OCTET_STREAM));

    Assert.assertEquals(200, response.getStatus());
  }
}

class SimpleCacheKey implements CacheKey {
  private String key;

  public SimpleCacheKey() {
    this.key = UUID.randomUUID().toString();
  }

  @Override
  public String keyAsString() {
    return key;
  }
}