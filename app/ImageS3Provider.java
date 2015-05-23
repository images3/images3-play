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

import com.google.inject.Provider;
import com.images3.ImageS3;
import com.images3.ImageS3Server;
import com.images3.data.impl.ImageProcessorProvider;
import com.images3.data.impl.MongoDBAccessProvider;
import com.images3.rest.base.DemoImageContentAccessProvider;


public class ImageS3Provider implements Provider<ImageS3> {

    private DemoImageContentAccessProvider imageContentAccessProvider;
    private ImageProcessorProvider imageProcessorProvider;
    private MongoDBAccessProvider accessProvider;
    
    public ImageS3Provider(String imgContentConf, String imgProcessorConf, String mongodbConf) {
        imageContentAccessProvider = new DemoImageContentAccessProvider(imgContentConf);
        imageProcessorProvider = new ImageProcessorProvider(imgProcessorConf);
        accessProvider = new MongoDBAccessProvider(mongodbConf);
    }
    
    @Override
    public ImageS3 get() {
        return  new ImageS3Server.Builder()
            .setImageContentAccess(imageContentAccessProvider.getImageContentAccess())
            .setImageProcessor(imageProcessorProvider.getImageProcessor())
            .setImageAccess(accessProvider.getImageAccess())
            .setImagePlantAccess(accessProvider.getImagePlantAccess())
            .setTempalteAccess(accessProvider.getTemplateAccess())
            .setImageMetricsService(accessProvider.getImageMetricsService())
            .build();
    }


}
