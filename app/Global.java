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
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.images3.ImageS3;
import com.images3.rest.exceptions.AmazonS3BucketAccessFailedExceptionMapper;
import com.images3.rest.exceptions.DuplicateImagePlantNameExceptionMapper;
import com.images3.rest.exceptions.DuplicateImageVersionExceptionMapper;
import com.images3.rest.exceptions.DuplicateTemplateNameExceptionMapper;
import com.images3.rest.exceptions.ExceptionMapper;
import com.images3.rest.exceptions.IllegalImagePlantNameLengthExceptionMapper;
import com.images3.rest.exceptions.IllegalImageVersioningExceptionMapper;
import com.images3.rest.exceptions.IllegalResizingDimensionsExceptionMapper;
import com.images3.rest.exceptions.IllegalTemplateNameExceptionMapper;
import com.images3.rest.exceptions.IllegalTemplateNameLengthExceptionMapper;
import com.images3.rest.exceptions.NoSuchEntityFoundExceptionMapper;
import com.images3.rest.exceptions.UnachievableTemplateExceptionMapper;
import com.images3.rest.exceptions.UnremovableTemplateExceptionMapper;
import com.images3.rest.exceptions.UnsupportedImageFormatExceptionMapper;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http;

public class Global extends GlobalSettings {

    private Injector injector;
    private ExceptionMapper exceptionHandler;
    
    public void onStart(Application app) {
        initExceptionHandlers();
        final ImageS3Provider imageS3Provider = new ImageS3Provider(
                app.getFile(app.configuration().getString("images3.conf")).getAbsolutePath(),
                app.getFile(app.configuration().getString("imageprocessor.conf")).getAbsolutePath(),
                app.getFile(app.configuration().getString("mongodb.conf")).getAbsolutePath()
                );
        injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(ImageS3.class).toProvider(imageS3Provider).asEagerSingleton();
                bind(ObjectMapper.class).toProvider(new ObjectMapperProvider()).asEagerSingleton();;
            }
            
        });
        Logger.info("Application has started");
    }
    
    private void initExceptionHandlers() {
        exceptionHandler = new AmazonS3BucketAccessFailedExceptionMapper(null);
        exceptionHandler = new DuplicateImagePlantNameExceptionMapper(exceptionHandler);
        exceptionHandler = new DuplicateImageVersionExceptionMapper(exceptionHandler);
        exceptionHandler = new DuplicateTemplateNameExceptionMapper(exceptionHandler);
        exceptionHandler = new IllegalImagePlantNameLengthExceptionMapper(exceptionHandler);
        exceptionHandler = new IllegalResizingDimensionsExceptionMapper(exceptionHandler);
        exceptionHandler = new IllegalTemplateNameLengthExceptionMapper(exceptionHandler);
        exceptionHandler = new IllegalTemplateNameExceptionMapper(exceptionHandler);
        exceptionHandler = new NoSuchEntityFoundExceptionMapper(exceptionHandler);
        exceptionHandler = new UnachievableTemplateExceptionMapper(exceptionHandler);
        exceptionHandler = new UnremovableTemplateExceptionMapper(exceptionHandler);
        exceptionHandler = new UnsupportedImageFormatExceptionMapper(exceptionHandler);
        exceptionHandler = new IllegalImageVersioningExceptionMapper(exceptionHandler);
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
    
    public <T> T getControllerInstance(Class<T> clazz) throws Exception {
        return injector.getInstance(clazz);
    }
    
    public Promise<Result> onError(Http.RequestHeader request, Throwable t) {
        Throwable excep = (t.getCause() == null ? t : t.getCause());
        return Promise.<Result>pure(exceptionHandler.toResult(excep));
    }
    
    //public Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
        //return Promise.<Result>pure(Results.notFound("/404.html"));
    //}
    
}
