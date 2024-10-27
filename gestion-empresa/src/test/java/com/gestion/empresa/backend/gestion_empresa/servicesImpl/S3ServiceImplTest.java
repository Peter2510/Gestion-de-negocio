package com.gestion.empresa.backend.gestion_empresa.servicesImpl;

import com.gestion.empresa.backend.gestion_empresa.services.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class S3ServiceImplTest {

    @InjectMocks
    private S3ServiceImpl s3Service;

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    @Mock
    private MultipartFile mockFile;

    @Value("${aws.bucketName}")
    private String bucketName = "test-bucket";

    @Value("${aws.keyName}")
    private String keyName = "test/key";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        s3Service = new S3ServiceImpl(s3Client, s3Client);
    }

    @Test
    public void testUploadFile_Success() throws IOException {
        String fileName = "test.txt";
        byte[] fileContent = "preuba texto!".getBytes();
        when(mockFile.getBytes()).thenReturn(fileContent);

        String response = s3Service.uploadFile(mockFile, fileName);

        assertEquals("Archivo subido correctamente", response);
    }

    @Test
    public void testUploadFile_Failure() throws IOException {
        String fileName = "test.txt";
        when(mockFile.getBytes()).thenThrow(new IOException("Error al obtener los bytes"));

        String response = s3Service.uploadFile(mockFile, fileName);

        assertNull(response);
    }

    @Test
    void testCreatePresignedGetUrlHandlesException() {

        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class)))
                .thenThrow(new RuntimeException("S3 error"));

        String result = s3Service.createPresignedGetUrl(keyName);

        assertNull(result);
    }

}
