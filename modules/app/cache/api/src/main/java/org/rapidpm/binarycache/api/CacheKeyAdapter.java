package org.rapidpm.binarycache.api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CacheKeyAdapter implements JsonSerializer, JsonDeserializer {

  private static final String CLASS = "CLASS";
  private static final String CONTENT = "CONTENT";

  @Override
  public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
    final JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty(CLASS, src.getClass().getName());
    jsonObject.add(CONTENT, context.serialize(src));
    return jsonObject;
  }

  @Override
  public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    final JsonObject jsonObject = json.getAsJsonObject();
    final JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASS);
    final Class aClass = getObjectClass(primitive.getAsString());
    return context.deserialize(jsonObject.get(CONTENT), aClass);
  }

  public Class getObjectClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new JsonParseException(e.getMessage());
    }
  }

}



