package junit.org.rapidpm.binarycache.connect.rest.v002;

import junit.org.rapidpm.binarycache.connect.rest.BaseBinaryCacheRestTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
 * Created by m.lang - RapidPM - Team on 23.03.2017.
 */
public class BinaryCacheClientRestTest002 extends BaseBinaryCacheRestTest {

  private String testString;
  private DefaultCacheKey key;
  private String targetUrl;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    testString = "123";
    key = new DefaultCacheKey("123");
    targetUrl = generateTestUrl(url, encodedKey(key));
  }

  @Test
  public void test001_a() throws Exception {
    final Response put = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(testString, MediaType.APPLICATION_OCTET_STREAM));
    Assert.assertEquals(200, put.getStatus());

    final Response get01 = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .get();
    Assert.assertEquals(200, get01.getStatus());

    final Response delete = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .delete();
    Assert.assertEquals(200, delete.getStatus());

    final Response get02 = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .get();
    Assert.assertEquals(404, get02.getStatus());
  }

  @Test
  public void test001_b() throws Exception {
    final Response put = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(testString, MediaType.APPLICATION_OCTET_STREAM));
    Assert.assertEquals(200, put.getStatus());

    final Response delete01 = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .delete();
    Assert.assertEquals(200, delete01.getStatus());

    final Response delete02 = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .delete();
    Assert.assertEquals(304, delete02.getStatus());
  }
}
