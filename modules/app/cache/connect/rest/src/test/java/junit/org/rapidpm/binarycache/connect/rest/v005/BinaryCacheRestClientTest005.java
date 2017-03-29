package junit.org.rapidpm.binarycache.connect.rest.v005;

import junit.org.rapidpm.binarycache.connect.rest.BaseBinaryCacheRestTest;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;

import javax.ws.rs.client.Client;
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
public class BinaryCacheRestClientTest005 extends BaseBinaryCacheRestTest {

  private static final int ITERATIONS = 200;
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  private File value;
  private String targetUrl;
  private Client client;
  private File tempFile;
  private Response response;

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();

    value = new File(this.getClass().getResource("testpic.png").toURI());
    client = ClientBuilder.newClient();
    tempFile = temporaryFolder.newFile();
  }

  @Test
  public void test001() throws Exception {
    for (int i = 0; i < ITERATIONS; i++) {
      final DefaultCacheKey key = new DefaultCacheKey(i + "");
      targetUrl = generateTestUrl(url, encodedKey(key));
      System.out.println("Iteration = " + i);
      client
          .target(targetUrl)
          .request(MediaType.APPLICATION_OCTET_STREAM)
          .put(Entity.entity(value, MediaType.APPLICATION_OCTET_STREAM));

      response = client
          .target(targetUrl)
          .request(MediaType.APPLICATION_OCTET_STREAM)
          .get();

      assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
      final byte[] result = response.readEntity(byte[].class);

      final Path path = Files.write(Paths.get(tempFile.toURI()), result);
      final boolean contentEquals = FileUtils.contentEquals(value, path.toFile());
      assertTrue(contentEquals);
      System.out.println("contentEquals = " + contentEquals);

      client.target(targetUrl)
          .request()
          .delete();

      response = client
          .target(targetUrl)
          .request(MediaType.APPLICATION_OCTET_STREAM)
          .get();

      assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }
  }


}


