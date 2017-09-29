package junit.org.rapidpm.binarycache.api.defaultkey.v002;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.CacheKeyAdapter;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.ddi.DI;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class DefaultCacheKeyAdapterTest002 {

  @Inject private CacheKeyAdapter adapter;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages("junit.org.rapidpm");
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    final String id = "a1b2c3";
    final String doctype = "thumbnail";
    final int pageNumber = 1;
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(CacheKey.class, adapter)
        .create();

    CacheKey defaultKey = new DefaultCacheKey(id);
    final String jsonDefaultKey = gson.toJson(defaultKey, CacheKey.class);
    defaultKey = gson.fromJson(jsonDefaultKey, CacheKey.class);

    assertEquals("{\"CLASS\":\"org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey\",\"CONTENT\":{\"key\":\"a1b2c3\"}}", jsonDefaultKey);
    assertNotNull(defaultKey);
    assertEquals(id, defaultKey.keyAsString());

    CacheKey extendedKey = new ExtendedCacheKey(id, doctype, pageNumber);
    final String jsonExtendedKey = gson.toJson(extendedKey, CacheKey.class);
    extendedKey = gson.fromJson(jsonExtendedKey, CacheKey.class);

    assertEquals("{\"CLASS\":\"junit.org.rapidpm.binarycache.api.defaultkey.v002.ExtendedCacheKey\",\"CONTENT\":{\"id\":\"a1b2c3\",\"doctype\":\"thumbnail\",\"pageNumber\":1}}", jsonExtendedKey);
    assertNotNull(extendedKey);
    assertEquals(String.format("%s_%s_%d", doctype, id, pageNumber), extendedKey.keyAsString());
  }

}


class ExtendedCacheKey implements CacheKey {

  private String id;
  private String doctype;
  private int pageNumber;

  public ExtendedCacheKey(String id, String doctype, int pageNumber) {
    this.id = id;
    this.doctype = doctype;
    this.pageNumber = pageNumber;
  }

  @Override
  public String keyAsString() {
    return String.format("%s_%s_%d", doctype, id, pageNumber);
  }
}
