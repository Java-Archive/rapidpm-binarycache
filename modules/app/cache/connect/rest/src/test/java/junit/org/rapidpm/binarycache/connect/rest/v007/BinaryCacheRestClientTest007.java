package junit.org.rapidpm.binarycache.connect.rest.v007;

import junit.org.rapidpm.binarycache.connect.rest.BaseBinaryCacheRestTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.rapidpm.binarycache.api.defaultkey.DefaultCacheKey;
import org.rapidpm.binarycache.connect.rest.BinaryCacheRestClient;
import org.rapidpm.ddi.DI;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;

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
 * Created by m.lang - RapidPM - Team on 12.07.2017.
 */
public class BinaryCacheRestClientTest007 extends BaseBinaryCacheRestTest {

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void test001() throws Exception {
    final BinaryCacheRestClient client = DI.activateDI(BinaryCacheRestClient.class);
    final DefaultCacheKey key = new DefaultCacheKey("123");
    final File file = folder.newFile();
    final FileInputStream inputStream = new FileInputStream(file);
    inputStream.close();
    final Response response = client.cacheBinary("notThere", encodedKey(key), inputStream);
    Assert.assertEquals(500, response.getStatus());
  }

  @Test
  public void test002() throws Exception {
    final BinaryCacheRestClient client = DI.activateDI(BinaryCacheRestClient.class);
    final DefaultCacheKey key = new DefaultCacheKey("123");
    final File file = folder.newFile();
    final FileInputStream inputStream = new FileInputStream(file);
    inputStream.close();
    final Response response = client.cacheBinaryIfAbsent("notThere", encodedKey(key), inputStream);
    Assert.assertEquals(500, response.getStatus());
  }

}
