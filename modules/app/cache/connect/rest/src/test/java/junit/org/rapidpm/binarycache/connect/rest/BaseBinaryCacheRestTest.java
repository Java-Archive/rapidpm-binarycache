package junit.org.rapidpm.binarycache.connect.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.CacheKeyAdapter;
import org.rapidpm.ddi.DI;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;

import javax.inject.Inject;
import java.util.Base64;

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
 * Created by RapidPM - Team on 21.03.2017.
 */
public class BaseBinaryCacheRestTest {

  @Inject
  private CacheKeyAdapter adapter;

  protected static String url;

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
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }


  protected String encodedKey(CacheKey cacheKey) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(CacheKey.class, adapter)
        .create();
    final String jsonString = gson.toJson(cacheKey, CacheKey.class);
    return new String(Base64.getUrlEncoder().encode(jsonString.getBytes()));
  }

  protected String generateTestUrl(String url, String key) {
    return String.format("%s/%s/%s",
        url,
        "default",
        key);
  }

}
