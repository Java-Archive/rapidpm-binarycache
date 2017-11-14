package org.rapidpm.binarycache.api;

import java.io.Serializable;

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
 * Created by RapidPM - Team on 27.03.2017.
 */
public class CacheByteArray implements Serializable {

  private byte[] byteArray;

  public CacheByteArray(byte[] byteArray) {
    this.byteArray = byteArray;
  }

  public byte[] getByteArray() {
    return byteArray;
  }

}
