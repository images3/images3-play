/*******************************************************************************
 * Copyright 2014 Rui Sun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

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
