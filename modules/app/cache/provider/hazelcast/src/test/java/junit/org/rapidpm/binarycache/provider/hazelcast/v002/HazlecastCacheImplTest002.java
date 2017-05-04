package junit.org.rapidpm.binarycache.provider.hazelcast.v002;

import com.hazelcast.core.Hazelcast;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.binarycache.provider.hazelcast.HazelcastCacheImpl;
import org.rapidpm.ddi.DI;

import javax.inject.Inject;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
public class HazlecastCacheImplTest002 {

  private static final String CACHE_NAME = "default";
  @Inject
  BinaryCacheClient client01;
  @Inject
  BinaryCacheClient client02;

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    Hazelcast.getHazelcastInstanceByName(((HazelcastCacheImpl) client01).getInstanceName()).shutdown();
    Hazelcast.getHazelcastInstanceByName(((HazelcastCacheImpl) client02).getInstanceName()).shutdown();
    DI.clearReflectionModel();
  }

  @Test
  public void test001() throws Exception {
    final DefaultCacheKey key = new DefaultCacheKey("001");
    final CacheByteArray value = new CacheByteArray("test".getBytes());
    // cache value in !client01!
    client01.cacheBinary(CACHE_NAME, key, value);
    // retrieve value from !client02!
    final Optional<CacheByteArray> cachedElement = client02.getCachedElement(CACHE_NAME, key);

    assertTrue(cachedElement.isPresent());
    assertEquals(new String(value.byteArray), new String(cachedElement.get().byteArray));
  }
}
