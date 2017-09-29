package org.rapidpm.binarycache.connect.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.rapidpm.binarycache.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
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

  @Inject private BinaryCacheClient binaryCacheClient;
  @Inject private CacheKeyAdapter adapter;
  private static final int BUFFER_SIZE = 1024;

  @PUT
  @Path("{cacheName}/{key}")
  @Consumes(MediaType.APPLICATION_OCTET_STREAM)
  public Response cacheBinary(@PathParam("cacheName") final String cacheName,
                              @PathParam("key") final String key,
                              final InputStream inputStream) {
    final CacheKey cacheKey = decodeCacheKey(key);

    try {
      final byte[] byteArray = receiveBytes(inputStream);

      binaryCacheClient.cacheBinary(cacheName, cacheKey, new CacheByteArray(byteArray));
      return Response.ok().build();
    } catch (IOException e) {
      LOGGER.error(String.format("failed to cache binary to cache <%s> with key <%s>", cacheName, cacheKey), e);
    }

    return Response.serverError().build();
  }

  @PUT
  @Path("{cacheName}/ifabsent/{key}")
  public Response cacheBinaryIfAbsent(@PathParam("cacheName") final String cacheName,
                                      @PathParam("key") final String key,
                                      final InputStream inputStream) {
    final CacheKey cacheKey = decodeCacheKey(key);

    try {
      final byte[] byteArray = receiveBytes(inputStream);
      binaryCacheClient.cacheBinaryIfAbsent(cacheName, cacheKey, new CacheByteArray(byteArray));
      return Response.ok().build();
    } catch (IOException e) {
      LOGGER.error(String.format("failed to cache binary to cache <%s> with key <%s>", cacheName, cacheKey), e);
    }

    return Response.serverError().build();
  }

  @GET
  @Path("{cacheName}/{key}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getCachedElement(@PathParam("cacheName") final String cacheName,
                                   @PathParam("key") final String key) {
    final CacheKey decodedKey = decodeCacheKey(key);
    final Optional<CacheByteArray> cachedElement = binaryCacheClient.getCachedElement(cacheName, decodedKey);
    return cachedElement
        .map(cacheByteArray -> Response.ok(new ByteArrayInputStream(cacheByteArray.byteArray), MediaType.APPLICATION_OCTET_STREAM).build())
        .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
  }

  @DELETE
  @Path("{cacheName}")
  public Response clearCache(@PathParam("cacheName") final String cacheName) {
    binaryCacheClient.clearCache(cacheName);
    return Response.ok().build();
  }

  @DELETE
  @Path("{cacheName}/{key}")
  public Response removeEntry(@PathParam("cacheName") final String cacheName,
                              @PathParam("key") final String cacheKey) {
    final Result result = binaryCacheClient.removeEntry(cacheName, decodeCacheKey(cacheKey));
    if (result.equals(Result.OK))
      return Response.ok().build();
    else
      return Response.notModified().build();
  }

  private CacheKey decodeCacheKey(String key) {
    final byte[] decode = Base64.getUrlDecoder().decode(key);
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(CacheKey.class, adapter)
        .create();

    return gson.fromJson(new String(decode), CacheKey.class);
  }

  private byte[] receiveBytes(InputStream inputStream) throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE * BUFFER_SIZE);
    byte[] buffer = new byte[BUFFER_SIZE * BUFFER_SIZE];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) > -1) {
      baos.write(buffer, 0, bytesRead);
    }
    inputStream.close();
    return baos.toByteArray();
  }


}
