package junit.org.rapidpm.binarycache.client.connect.inmemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;
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
 * Created by RapidPM - Team on 09.03.2017.
 */
public class BinaryCacheInmemoryClientTest {

  @Inject
  BinaryCacheClient client;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @Test
  public void test001() throws Exception {
    final Cache<CacheKey, Byte[]> test = client.createCache("test");
    Assert.assertNotNull(test);
  }
}