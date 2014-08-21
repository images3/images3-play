import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Provider;
import com.images3.ResizingConfig;
import com.images3.ResizingUnit;
import com.images3.TemplateIdentity;
import com.images3.TemplateResponse;
import com.images3.TemplateUpdateRequest;
import com.images3.rest.codec.ResizingConfigDeserializer;
import com.images3.rest.codec.ResizingConfigSerializer;
import com.images3.rest.codec.ResizingUnitDeserializer;
import com.images3.rest.codec.ResizingUnitSerializer;
import com.images3.rest.codec.TemplateIdentityDeserializer;
import com.images3.rest.codec.TemplateIdentitySerializer;
import com.images3.rest.codec.TemplateResponseSerializer;
import com.images3.rest.codec.TemplateUpdateRequestDeserializer;


public class ObjectMapperProvider implements Provider<ObjectMapper> {

    @Override
    public ObjectMapper get() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ResizingUnit.class, new ResizingUnitSerializer());
        module.addSerializer(ResizingConfig.class, new ResizingConfigSerializer());
        module.addSerializer(TemplateIdentity.class, new TemplateIdentitySerializer());
        module.addSerializer(TemplateResponse.class, new TemplateResponseSerializer());
        
        module.addDeserializer(ResizingUnit.class, new ResizingUnitDeserializer());
        module.addDeserializer(ResizingConfig.class, new ResizingConfigDeserializer());
        module.addDeserializer(TemplateIdentity.class, new TemplateIdentityDeserializer());
        module.addDeserializer(TemplateUpdateRequest.class, new TemplateUpdateRequestDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

}
