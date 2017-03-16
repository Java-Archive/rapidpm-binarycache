package junit.org.rapidpm.binarycache.api.defaultkey.v001;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.ddi.DI;

import javax.inject.Inject;

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
public class DefaultCacheKeyAdapterTest001 {

  @Inject
  JsonDeserializer<CacheKey> deserializer;
  @Inject
  JsonSerializer<CacheKey> serializer;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    final String testKey = "test";
    final CacheKey defaultCacheKey = new DefaultCacheKey(testKey);
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(CacheKey.class, serializer)
        .registerTypeAdapter(CacheKey.class, deserializer)
        .create();

    final String json = gson.toJson(defaultCacheKey);

    final CacheKey cacheKey = gson.fromJson(json, CacheKey.class);
    Assert.assertNotNull(cacheKey);
    Assert.assertEquals(testKey, cacheKey.keyAsString());
  }



}