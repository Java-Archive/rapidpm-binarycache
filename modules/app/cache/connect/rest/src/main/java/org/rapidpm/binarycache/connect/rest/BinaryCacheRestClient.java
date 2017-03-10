package org.rapidpm.binarycache.connect.rest;

import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheKey;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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

  @Inject
  private BinaryCacheClient binaryCacheClient;

  // how to handle really large files
  @POST
  @Path("/cacheBinary")
  public Response cacheBinary(@QueryParam("cacheName") final String cacheName,
                              @QueryParam("key") final String cacheKey,
                              @QueryParam("value") final String binary) {
    return null;
  }

  @POST
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
    final byte[] decode = Base64.getDecoder().decode(cacheKey);
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
}
