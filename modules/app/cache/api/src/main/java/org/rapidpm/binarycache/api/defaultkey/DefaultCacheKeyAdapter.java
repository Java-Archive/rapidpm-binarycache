package org.rapidpm.binarycache.api.defaultkey;

import com.google.gson.*;
import org.rapidpm.binarycache.api.CacheKey;

import java.lang.reflect.Type;

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
public class DefaultCacheKeyAdapter implements JsonSerializer<CacheKey>, JsonDeserializer<CacheKey>{

  @Override
  public CacheKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return context.deserialize(json, DefaultCacheKey.class);
  }

  @Override
  public JsonElement serialize(CacheKey src, Type typeOfSrc, JsonSerializationContext context) {
    final JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("CacheKey", context.serialize(src, DefaultCacheKey.class).getAsString());
    return jsonObject;
  }

}
