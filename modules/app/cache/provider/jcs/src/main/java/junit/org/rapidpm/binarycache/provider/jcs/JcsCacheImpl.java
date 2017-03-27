package junit.org.rapidpm.binarycache.provider.jcs;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.api.CacheByteArray;
import org.rapidpm.binarycache.api.CacheKey;
import org.rapidpm.binarycache.api.Result;

import javax.cache.Cache;
import javax.cache.configuration.Configuration;
import java.util.Optional;

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
 * Created by RapidPM - Team on 09.03.2017.
 */
public class JcsCacheImpl implements BinaryCacheClient {

  @Override
  public Cache<CacheKey, CacheByteArray> getCache(String cacheName) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public Cache<CacheKey, CacheByteArray> createCache(String cacheName) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public Cache<CacheKey, CacheByteArray> createCache(String cacheName, Configuration<CacheKey, CacheByteArray> configuration) {
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public Result cacheBinary(String cacheName, CacheKey cacheKey, CacheByteArray binary) {
    final CacheAccess<Object, Object> instance = JCS.getInstance(cacheName);
    instance.put(cacheKey, binary);
    return Result.OK;
  }

  @Override
  public Result cacheBinaryIfAbsent(String cacheName, CacheKey cacheKey, CacheByteArray binary) {
    final CacheAccess<CacheKey, CacheByteArray> instance = JCS.getInstance(cacheName);
    try {
      instance.putSafe(cacheKey, binary);
    } catch (CacheException e) {
      // maybe log that it was already there
    }
    return Result.OK;
  }

  @Override
  public Optional<CacheByteArray> getCachedElement(String cacheName, CacheKey cacheKey) {
    final CacheAccess<CacheKey, CacheByteArray> instance = JCS.getInstance(cacheName);
    return Optional.ofNullable(instance.get(cacheKey));
  }

  @Override
  public Result clearCache(String cacheName) {
    try {
      JCS.getInstance(cacheName).clear();
    } catch (CacheException e) {
      // log
      return Result.FAILED;
    }
    return Result.OK;
  }

  @Override
  public Result removeEntry(String cacheName, CacheKey cacheKey) {
    JCS.getInstance(cacheName).remove(cacheKey);
    return Result.OK;
  }
}
