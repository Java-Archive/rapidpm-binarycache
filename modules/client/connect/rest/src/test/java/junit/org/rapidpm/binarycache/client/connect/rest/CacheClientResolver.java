package junit.org.rapidpm.binarycache.client.connect.rest;

import org.rapidpm.binarycache.api.BinaryCacheClient;
import org.rapidpm.binarycache.provider.ehcache.EhCacheImpl;
import org.rapidpm.ddi.ResponsibleFor;
import org.rapidpm.ddi.implresolver.ClassResolver;

@ResponsibleFor(BinaryCacheClient.class)
public class CacheClientResolver implements ClassResolver<BinaryCacheClient> {
  @Override
  public Class<? extends BinaryCacheClient> resolve(Class<BinaryCacheClient> aClass) {
    // return the provider needed to start the cache endpoint
    return EhCacheImpl.class;
  }
}
