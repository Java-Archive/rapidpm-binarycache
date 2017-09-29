package junit.org.rapidpm.binarycache.connect.rest.util;

import org.junit.Test;
import org.rapidpm.binarycache.connect.rest.util.ByteUtils;

import static org.junit.Assert.assertEquals;

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
public class ByteUtilsTest {

  @Test
  public void test001() throws Exception {
    final byte[] testByteArray = "testByteArray".getBytes();
    final Byte[] nonPrimitive = ByteUtils.fromPrimitive(testByteArray);
    assertEquals(nonPrimitive.length, testByteArray.length);

    final byte[] primitive = ByteUtils.toPrimitive(nonPrimitive);
    assertEquals(primitive.length, testByteArray.length);
    assertEquals("testByteArray", new String(primitive));
  }
}
