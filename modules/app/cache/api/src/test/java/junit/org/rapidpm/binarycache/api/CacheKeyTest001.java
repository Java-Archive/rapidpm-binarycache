package junit.org.rapidpm.binarycache.api;

import org.junit.Test;
import org.rapidpm.binarycache.api.CacheKey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
 * Created by m.lang - RapidPM - Team on 04.05.2017.
 */
public class CacheKeyTest001 {

  @Test
  public void test001() throws Exception {
    assertNotNull(ValidatingKey.getInstance("1_d"));
    assertNull(ValidatingKey.getInstance("d_1"));
    assertNull(ValidatingKey.getInstance(""));
  }
}

class ValidatingKey implements CacheKey {

  private String key;

  private ValidatingKey(String key) {
    this.key = key;
  }

  public static CacheKey getInstance(String key) {
    return key.matches("\\d+_\\w+") ? new ValidatingKey(key) : null;
  }

  @Override
  public String keyAsString() {
    return this.key;
  }
}

