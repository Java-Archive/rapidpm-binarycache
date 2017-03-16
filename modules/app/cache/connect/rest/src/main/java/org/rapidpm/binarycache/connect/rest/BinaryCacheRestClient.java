package org.rapidpm.binarycache.connect.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.Optional;

/**
 * Copyright (C) 2017 RapidPM - Sven Ruppert
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
 * Created by Sven Ruppert - RapidPM - Team on 08.03.17.
 */
@Path("/cache")
public class BinaryCacheRestClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(BinaryCacheRestClient.class);

  @Inject
  private BinaryCacheClient binaryCacheClient;
  @Inject
  private

  @PUT
  @Path("/{cacheName}/{key}")
  @Consumes(MediaType.APPLICATION_OCTET_STREAM)
  public Response cacheBinary(@PathParam("cacheName") final String cacheName,
                              @PathParam("key") final String key,
                              final InputStream inputStream) {

    final CacheKey cacheKey = decodeCacheKey(key);

    try (final Base64InputStream bis = new Base64InputStream(inputStream)){
      final byte[] bytes = IOUtils.toByteArray(bis);
      binaryCacheClient.cacheBinary(cacheName, cacheKey, fromPrimitive(bytes));
      bis.close();
      return Response.ok().build();
    } catch (IOException e) {
      LOGGER.error(String.format("failed to cache binary to cache <%s> with key <%s>", cacheName, cacheKey), e);
    }

    return Response.serverError().build();
  }

  @PUT
  @Path("/cacheBinaryIfAbsent")
  public Response cacheBinaryIfAbsent(@QueryParam("cacheName") final String cacheName,
                                      @QueryParam("key") final String cacheKey,
                                      @QueryParam("value") final String binary) {
    return null;
    //return binaryCacheClient.cacheBinaryIfAbsent(cacheName, cacheKey, binary);
  }

  @GET
  @Path("getCachedElement")
  public Response getCachedElement(@QueryParam("cacheName") final String cacheName,
                                   @QueryParam("key") final String cacheKey) {
    final byte[] decode = Base64.getUrlDecoder().decode(cacheKey);
    try (final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(decode))) {
      CacheKey decodedKey = (CacheKey) ois.readObject();
      final Optional<Byte[]> cachedElement = binaryCacheClient.getCachedElement(cacheName, decodedKey);
      return Response
          .status(Response.Status.OK)
          .entity(cachedElement)
          .build();
    } catch (IOException | ClassNotFoundException e) {
      // logging
      e.printStackTrace();
    }

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
  }

  @GET
  @Path("clearCache")
  public Response clearCache(final String cacheName) {
    return null;
    //return binaryCacheClient.clearCache(cacheName);
  }

  @GET
  @Path("removeEntry")
  public Response removeEntry(final String cacheName, final CacheKey cacheKey) {
    return null;
    //return binaryCacheClient.removeEntry(cacheName, cacheKey);
  }


  private CacheKey decodeCacheKey(String key) {
    final byte[] decode = Base64.getUrlDecoder().decode(key);
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(CacheKey.class, new CacheKeyRequestCreator());
    Gson parser = builder.create();


    return gson.fromJson(new String(decode), CacheKey.class);
  }

  // TODO move to util class
  private Byte[] fromPrimitive(byte[] primitive) {
    Byte[] bytes = new Byte[primitive.length];

    int i = 0;
    for (byte b : primitive)
      bytes[i++] = b;

    return bytes;
  }

  private byte[] toPrimitive(Byte[] bytes) {
    byte[] primitive = new byte[bytes.length];

    int i = 0;
    for (Byte b : bytes)
      primitive[i++] = b;

    return primitive;
  }

}
