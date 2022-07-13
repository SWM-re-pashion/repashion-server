package rePashion.server.domain.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rePashion.server.domain.user.exception.FileConvertingException;
import rePashion.server.domain.user.model.StyleImage;
import rePashion.server.domain.user.repository.StyleImageRepository;
import rePashion.server.global.error.exception.ErrorCode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3UploaderService {

    private final AmazonS3Client amazonS3Client;
    private final StyleImageRepository styleRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException{
        File uploadFile = convert(multipartFile).orElseThrow(() -> new FileConvertingException(ErrorCode.FILE_CONVERTING_ERROR));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName){
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        saveDb(uploadImageUrl);
        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile){
        if(targetFile.delete())
            System.out.println("File deleting success");
        else System.out.println("File deleting failed");
    }

    private void saveDb(String imageUrl){
        StyleImage image = new StyleImage(imageUrl, "");
        styleRepository.save(image);
    }

    private String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException{
        File convertFile = new File(multipartFile.getOriginalFilename());
        if(convertFile.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
