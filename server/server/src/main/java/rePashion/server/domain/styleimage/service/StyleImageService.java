package rePashion.server.domain.styleimage.service;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rePashion.server.domain.preference.exception.StyleImageNotExistedException;
import rePashion.server.domain.preference.model.StyleImage;
import rePashion.server.domain.styleimage.dto.StyleImageResponseDto;
import rePashion.server.domain.styleimage.repository.StyleImageRepository;
import rePashion.server.global.error.exception.ErrorCode;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StyleImageService {

    private final StyleImageRepository styleImageRepository;

    static boolean created = false;
    @Value("${cloud.aws.credentials.access-key}")
    private String ACCESS_KEY;

    @Value("${cloud.aws.credentials.secret-key}")
    private String SECRET_KEY;

    @Value("${cloud.aws.region.static}")
    private String REGION_NAME;

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET_NAME;

    public void create(){
        if(created) return;
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).withRegion(Regions.fromName(REGION_NAME)).build();
        ObjectListing objectListing = amazonS3Client.listObjects(BUCKET_NAME, "static");
        objectListing.getObjectSummaries().forEach((e) ->{
            String url = amazonS3Client.getUrl(BUCKET_NAME, e.getKey()).toString();
            String pattern = "^.*\\.(png|jpg)$";
            if(Pattern.matches(pattern, url))
                styleImageRepository.save(new StyleImage(url, "합합"));
            }
        );
        created = true;
    }

    public StyleImageResponseDto get(){
        List<StyleImage> styleImages = styleImageRepository.findAll();
        if(styleImages.isEmpty()) throw new StyleImageNotExistedException(ErrorCode.STYLE_IMAGE_NOT_EXISTED);
        return StyleImageResponseDto.fromEntity(styleImages);
    }
}
