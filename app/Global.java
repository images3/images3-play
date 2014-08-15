

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.images3.DuplicatedImagePlantNameException;
import com.images3.ImageS3;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Http;
import play.mvc.Results;

public class Global extends GlobalSettings {

    private Injector injector;
    
    public void onStart(Application app) {
        final ImageS3Provider imageS3Provider = new ImageS3Provider(
                app.configuration().getString("imagecontent.conf"),
                app.configuration().getString("imageprocessor.conf"),
                app.configuration().getString("mongodb.conf")
                );
        injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(ImageS3.class).toProvider(imageS3Provider).asEagerSingleton();
            }
            
        });
        Logger.info("Application has started");
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
    
    public <T> T getControllerInstance(Class<T> clazz) throws Exception {
        return injector.getInstance(clazz);
    }
    
    public Promise<Result> onError(Http.RequestHeader request, Throwable t) {
        if (DuplicatedImagePlantNameException.class.isInstance(t.getCause())) {
            DuplicatedImagePlantNameException exception = (DuplicatedImagePlantNameException) t.getCause();
            String message = "ImagePlant name, \'" + exception.getName() + "\' has been taken.";
            return Promise.<Result>pure(Results.badRequest(message));
        }
        return Promise.<Result>pure(Results.internalServerError());
    }
}
