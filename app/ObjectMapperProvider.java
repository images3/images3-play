
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Provider;
import com.images3.common.AmazonS3Bucket;
import com.images3.common.ResizingConfig;
import com.images3.common.TemplateIdentity;
import com.images3.ImagePlantAddRequest;
import com.images3.TemplateResponse;
import com.images3.rest.models.AmazonS3BucketModel;
import com.images3.rest.models.ImagePlantAddRequestModel;
import com.images3.rest.models.ResizingConfigModel;
import com.images3.rest.models.TemplateIdentityModel;
import com.images3.rest.models.TemplateResponseModel;


public class ObjectMapperProvider implements Provider<ObjectMapper> {

    @Override
    public ObjectMapper get() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
            WRITE_DATES_AS_TIMESTAMPS , false);
        mapper.addMixInAnnotations(ImagePlantAddRequest.class, ImagePlantAddRequestModel.class);
        mapper.addMixInAnnotations(AmazonS3Bucket.class, AmazonS3BucketModel.class);
        mapper.addMixInAnnotations(ResizingConfig.class, ResizingConfigModel.class);
        mapper.addMixInAnnotations(TemplateIdentity.class, TemplateIdentityModel.class);
        mapper.addMixInAnnotations(TemplateResponse.class, TemplateResponseModel.class);
        return mapper;
    }

}
