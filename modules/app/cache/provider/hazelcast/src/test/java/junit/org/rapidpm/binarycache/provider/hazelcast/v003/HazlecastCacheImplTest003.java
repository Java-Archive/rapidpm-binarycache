package junit.org.rapidpm.binarycache.provider.hazelcast.v003;

import com.hazelcast.cache.CacheNotExistsException;
import com.hazelcast.core.Hazelcast;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.provider.hazelcast.HazelcastCacheImpl;
import org.rapidpm.ddi.DI;

import javax.cache.Cache;

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
 * Created by m.lang - RapidPM - Team on 30.03.2017.
 */
public class HazlecastCacheImplTest003 {

  private HazelcastCacheImpl client;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
  }

  @After
  public void tearDown() throws Exception {
    Hazelcast.getHazelcastInstanceByName(client.getInstanceName()).shutdown();
    DI.clearReflectionModel();
    System.clearProperty(HazelcastCacheImpl.CACHE_CONFIG_PROPERTY);
  }

  @Test(expected = CacheNotExistsException.class)
  public void test001() throws Exception {
    DI.activateDI(this);
    client = DI.activateDI(HazelcastCacheImpl.class);
    final Cache<CacheKey, CacheByteArray> cache01 = client.getCache("default");
    assertNotNull(cache01);
    final Cache<CacheKey, CacheByteArray> cache02 = client.getCache("myCache");
  }

  @Test
  public void test002() throws Exception {
    final String path = HazlecastCacheImplTest003.class.getResource("hazelcast.xml").getPath();
    System.setProperty(HazelcastCacheImpl.CACHE_CONFIG_PROPERTY, path);

    DI.activateDI(this);
    client = DI.activateDI(HazelcastCacheImpl.class);

    final Cache<CacheKey, CacheByteArray> cache01 = client.getCache("myCache");
    assertNotNull(cache01);
  }
}
