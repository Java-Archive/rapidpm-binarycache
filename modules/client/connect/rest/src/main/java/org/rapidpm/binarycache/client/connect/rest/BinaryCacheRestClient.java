package org.rapidpm.binarycache.client.connect.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.rapidpm.binarycache.api.*;

import javax.cache.Cache;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.Objects;
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
 * Created by Sven Ruppert - RapidPM - Team on 07.03.17.
 */
public class BinaryCacheRestClient implements BinaryCacheClient {

  @Inject
  private CacheKeyAdapter adapter;

  public static final String DEFAULT_PORT = "8080";
  public static final String DEFAULT_IP = "127.0.0.1";
  private final String serverIp;
  private final String serverPort;

  public BinaryCacheRestClient() {
    serverIp = DEFAULT_IP;
    serverPort = DEFAULT_PORT;
  }

  public BinaryCacheRestClient(final String serverIp, final String serverPort) {
    if (Objects.isNull(serverIp)) throw new NullPointerException("serverIP is null");
    if (Objects.isNull(serverPort)) throw new NullPointerException("serverPort is null");
    this.serverIp = serverIp;
    this.serverPort = serverPort;
  }

  public BinaryCacheRestClient(final int serverPort) {
    this.serverIp = DEFAULT_IP;
    this.serverPort = String.valueOf(serverPort);
  }

  public BinaryCacheRestClient(final String serverIp) {
    if (Objects.isNull(serverIp)) throw new NullPointerException("serverIP is null");
    this.serverIp = serverIp;
    this.serverPort = DEFAULT_PORT;
  }


  //create REST request methods , delegate to binaryCacheClient

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(final String cacheName) {
    throw new RuntimeException("not allowed remote");
  }

  @Override
  public Result cacheBinary(final String cacheName, final CacheKey cacheKey, final CacheByteArray binary) {
    final String encodedKey = encodeKey(cacheKey);
    final String targetUrl = buildBaseUrl(cacheName);
    final Client client = ClientBuilder.newClient();

    final Response response = client.target(targetUrl + "/" + encodedKey)
        .request()
        .put(Entity.entity(binary.byteArray, MediaType.APPLICATION_OCTET_STREAM));

    client.close();
    return response.getStatus() == Response.Status.OK.getStatusCode() ? Result.OK : Result.FAILED;
  }

  @Override
  public Result cacheBinaryIfAbsent(final String cacheName, final CacheKey cacheKey, final CacheByteArray binary) {
    final String encodedKey = encodeKey(cacheKey);
    final String targetUrl = buildBaseUrl(cacheName) + "/ifabsent";
    final Client client = ClientBuilder.newClient();

    final Response response = client.target(targetUrl + "/" + encodedKey)
        .request()
        .put(Entity.entity(binary.byteArray, MediaType.APPLICATION_OCTET_STREAM));

    client.close();
    return response.getStatus() == Response.Status.OK.getStatusCode() ? Result.OK : Result.FAILED;
  }

  @Override
  public Optional<CacheByteArray> getCachedElement(final String cacheName, final CacheKey cacheKey) {
    final String encodedKey = encodeKey(cacheKey);
    final Client client = ClientBuilder.newClient();

    final String targetUrl = buildBaseUrl(cacheName);

    final Response response = client.target(targetUrl + "/" + encodedKey)
        .request()
        .get();
    final byte[] bytes = response.readEntity(byte[].class);

    client.close();
    return bytes.length > 0 ? Optional.of(new CacheByteArray(bytes)) : Optional.empty();
  }

  @Override
  public Result clearCache(final String cacheName) {
    final Client client = ClientBuilder.newClient();
    final String targetUrl = buildBaseUrl(cacheName);

    final Response response = client.target(targetUrl)
        .request()
        .delete();
    client.close();

    return response.getStatus() == Response.Status.OK.getStatusCode() ? Result.OK : Result.FAILED;
  }

  @Override
  public Result removeEntry(final String cacheName, final CacheKey cacheKey) {
    final String encodedKey = encodeKey(cacheKey);
    final Client client = ClientBuilder.newClient();
    final String targetUrl = buildBaseUrl(cacheName);

    final Response response = client.target(targetUrl + "/" + encodedKey)
        .request()
        .delete();
    client.close();

    return response.getStatus() == Response.Status.OK.getStatusCode() ? Result.OK : Result.FAILED;
  }

  private String encodeKey(CacheKey cacheKey) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(CacheKey.class, adapter)
        .create();
    final String jsonString = gson.toJson(cacheKey, CacheKey.class);
    return new String(Base64.getUrlEncoder().encode(jsonString.getBytes()));
  }

  private String buildBaseUrl(String cacheName) {
    return String.format("http://%s:%s/rest/cache/%s", serverIp, serverPort, cacheName);
  }


}
