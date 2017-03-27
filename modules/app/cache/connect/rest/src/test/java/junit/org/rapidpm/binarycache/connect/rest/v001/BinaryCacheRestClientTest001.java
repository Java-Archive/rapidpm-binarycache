package junit.org.rapidpm.binarycache.connect.rest.v001;

import junit.org.rapidpm.binarycache.connect.rest.BaseBinaryCacheRestTest;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

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
public class BinaryCacheRestClientTest001 extends BaseBinaryCacheRestTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void test001() throws Exception {
    final String testString = "my test string";
    final CacheKey cacheKey = new DefaultCacheKey(testString);

    final String testUrl = generateTestUrl(url, encodedKey(cacheKey));
    System.out.println("testUrl = " + testUrl);

    final Response response = ClientBuilder.newClient()
        .target(testUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(testString.getBytes(), MediaType.APPLICATION_OCTET_STREAM));

    assertEquals(200, response.getStatus());
  }

  @Test
  public void test002() throws Exception {
    final String id = "1a2b3c";
    final String testString = "teststring";
    final DefaultCacheKey cacheKey = new DefaultCacheKey(id);

    final String testUrl = generateTestUrl(url, encodedKey(cacheKey));
    System.out.println("testUrl = " + testUrl);

    ClientBuilder.newClient()
        .target(testUrl)
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

  @Test
  public void test003() throws Exception {
    final File value = new File(this.getClass().getResource("ideaIU-2017.1.exe").toURI());
    final DefaultCacheKey key = new DefaultCacheKey("123");

    final String targetUrl = generateTestUrl(url, encodedKey(key));

    ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .put(Entity.entity(value, MediaType.APPLICATION_OCTET_STREAM));

    final Response response = ClientBuilder.newClient()
        .target(targetUrl)
        .request(MediaType.APPLICATION_OCTET_STREAM)
        .get();

    assertEquals(200, response.getStatus());

    final byte[] entity = response.readEntity(byte[].class);

    final File file = temporaryFolder.newFile();
    final Path path = Files.write(Paths.get(file.toURI()), entity);
    assertNotNull(entity);
    assertTrue(FileUtils.contentEquals(value, path.toFile()));

  }



}


