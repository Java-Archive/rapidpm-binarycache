package junit.org.rapidpm.binarycache.api.defaultkey.v002;

import com.google.gson.JsonDeserializer;
import org.rapidpm.binarycache.api.CacheKeyAdapter;
import org.rapidpm.ddi.ResponsibleFor;
import org.rapidpm.ddi.implresolver.ClassResolver;

@ResponsibleFor(JsonDeserializer.class)
public class DeserializerResolver implements ClassResolver<JsonDeserializer> {
  @Override
  public Class<? extends JsonDeserializer> resolve(Class<JsonDeserializer> interf) {
    return CacheKeyAdapter.class;
  }
}
