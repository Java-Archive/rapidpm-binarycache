package junit.org.rapidpm.binarycache.connect.rest.v003;

import junit.org.rapidpm.binarycache.connect.rest.BaseBinaryCacheRestTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
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
public class BinaryCacheRestClientTest003 extends BaseBinaryCacheRestTest {

  @Test
  public void test001() throws Exception {
    Response response;
    response = ClientBuilder.newClient()
        .target(url + "/" + "default")
        .request()
        .delete();
    Assert.assertEquals(200, response.getStatus());

    response = ClientBuilder.newClient()
        .target(url + "/" + "default")
        .request()
        .delete();
    Assert.assertEquals(200, response.getStatus());

  }
}
