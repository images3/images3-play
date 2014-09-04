import com.google.inject.Provider;
import com.images3.ImageS3;
import com.images3.ImageS3Server;
import com.images3.core.infrastructure.ImageContentAccessProvider;
import com.images3.core.infrastructure.ImageProcessorProvider;
import com.images3.core.infrastructure.ObjectSegmentAccessProvider;


public class ImageS3Provider implements Provider<ImageS3> {

    private ImageContentAccessProvider imageContentAccessProvider;
    private ImageProcessorProvider imageProcessorProvider;
    private ObjectSegmentAccessProvider objectSegmentAccessProvider;
    
    public ImageS3Provider(String imgContentConf, String imgProcessorConf, String mongodbConf) {
        imageContentAccessProvider = new ImageContentAccessProvider(imgContentConf);
        imageProcessorProvider = new ImageProcessorProvider(imgProcessorConf);
        objectSegmentAccessProvider = new ObjectSegmentAccessProvider(mongodbConf);
    }
    
    @Override
    public ImageS3 get() {
        return  new ImageS3Server.Builder()
            .setImageContentAccess(imageContentAccessProvider.getImageContentAccess())
            .setImageProcessor(imageProcessorProvider.getImageProcessor())
            .setImageAccess(objectSegmentAccessProvider.getImageAccess())
            .setImagePlantAccess(objectSegmentAccessProvider.getImagePlantAccess())
            .setTempalteAccess(objectSegmentAccessProvider.getTemplateAccess())
            .setImageMetricsService(objectSegmentAccessProvider.getImageMetricsService())
            .build();
    }


}
