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
package com.images3.rest.base;

import com.images3.common.AmazonS3Bucket;
import com.images3.common.ImageIdentity;
import com.images3.data.spi.ImageContentAccess;

import java.io.File;

public class DemoImageContentAccessImpl implements ImageContentAccess {

    private String imageContentDownloadDir;

    public DemoImageContentAccessImpl(String imageContentDownloadDir) {
        checkForDirExistence(imageContentDownloadDir);
        this.imageContentDownloadDir = imageContentDownloadDir;
    }
    
    private void checkForDirExistence(String path) {
        File folder = new File(path);
        if (!folder.exists() 
                || !folder.isDirectory()) {
            throw new IllegalArgumentException("Directory doesn't exists " + path);
        }
    }

    @Override
    public boolean testBucketAccessibility(AmazonS3Bucket bucket) {
        return true;
    }

    @Override
    public File insertImageContent(ImageIdentity id, AmazonS3Bucket bucket,
            File content) {
        File imageFile = new File(generateFilePath(id));
        content.renameTo(imageFile);
        return imageFile;
    }

    @Override
    public void deleteImageContent(ImageIdentity id, AmazonS3Bucket bucket) {
        File imageFile = new File(generateFilePath(id));
        imageFile.delete();
    }

    @Override
    public void deleteImageContentByImagePlantId(String imagePlantId,
            AmazonS3Bucket bucket) {
    }

    @Override
    public File selectImageContent(ImageIdentity id, AmazonS3Bucket bucket) {
        File imageContent = new File(generateFilePath(id));
        return imageContent;
    }
    
    private String generateFilePath(ImageIdentity id) {
       String path = imageContentDownloadDir + File.separator + id.getImagePlantId();
       File folder = new File(path);
       if (!folder.exists()) {
           folder.mkdir();
       }
       return path + File.separator + id.getImageId();
    }

}
