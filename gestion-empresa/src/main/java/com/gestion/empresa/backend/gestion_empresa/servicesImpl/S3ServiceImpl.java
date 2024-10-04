package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client S3CLIENT;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    public S3ServiceImpl(S3Client S3CLIENT) {
        this.S3CLIENT = S3CLIENT;
    }

    public String uploadFile(MultipartFile file) {
        try {

            String fileName = file.getOriginalFilename();
            PutObjectRequest putObjectAclRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            S3CLIENT.putObject(putObjectAclRequest, RequestBody.fromBytes(file.getBytes()));

            return "Archivo subido correctamente";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
