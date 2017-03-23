package junit.org.rapidpm.binarycache.connect.rest.v004;

import junit.org.rapidpm.binarycache.connect.rest.BaseBinaryCacheRestTest;
import org.junit.Test;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
public class BinaryCacheRestClientTest004 extends BaseBinaryCacheRestTest {


  @Test
  public void test002() throws Exception {
    final String id = "1a2b3c";
    final String testString = "teststring";
    final DefaultCacheKey cacheKey = new DefaultCacheKey(id);

    final String ifAbsentTestUrl = generateIfAbsentTestUrl(url, encodedKey(cacheKey));
    final String testUrl = generateTestUrl(url, encodedKey(cacheKey));

    ClientBuilder.newClient()
        .target(ifAbsentTestUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(testString, MediaType.APPLICATION_OCTET_STREAM));
    final Response response = ClientBuilder.newClient()
        .target(testUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .get();
    assertEquals(200, response.getStatus());

    final byte[] entity = response.readEntity(byte[].class);
    assertNotNull(entity);
    assertEquals(testString, new String(entity));
  }

  private String generateIfAbsentTestUrl(String url, String key) {
    return String.format("%s/%s/%s/%s",
        url,
        "default",
        "ifabsent",
        key);
  }
}


