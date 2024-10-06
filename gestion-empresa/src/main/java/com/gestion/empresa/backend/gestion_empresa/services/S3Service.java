package com.gestion.empresa.backend.gestion_empresa.services;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file, String fileName);
    String createPresignedGetUrl(String key);
}
