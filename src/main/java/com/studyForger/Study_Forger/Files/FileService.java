package com.studyForger.Study_Forger.Files;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    // Implement the methods here
    String uploadImage(MultipartFile file, String path) throws IOException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;

    void deleteImage(String path, String fileName) throws IOException;
}
