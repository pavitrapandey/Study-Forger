package com.studyForge.Study_Forge.Files.Service.ServiceImpl;

import com.studyForge.Study_Forge.Exception.BadApiRequest;
import com.studyForge.Study_Forge.Files.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new BadApiRequest("File name is invalid");
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        if (extension.equalsIgnoreCase(".png") ||
                extension.equalsIgnoreCase(".jpg") ||
                extension.equalsIgnoreCase(".jpeg")) {

            String randomName = UUID.randomUUID().toString();
            String newFileName = randomName + extension;

            File dir = new File(path);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("Failed to create directory: " + path);
            }

            String fullPath = path + File.separator + newFileName;
            Files.copy(file.getInputStream(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);

            return newFileName;

        } else {
            throw new BadApiRequest("Invalid file format");
        }
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }

    @Override
    public void deleteImage(String path, String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        java.nio.file.Path filePath = Paths.get(path, fileName);
        try {
            boolean deleted = Files.deleteIfExists(filePath);
            if (!deleted) {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (SecurityException e) {
            throw new IOException("Permission denied while deleting file: " + fileName, e);
        } catch (IOException e) {
            throw new IOException("Error deleting file: " + fileName, e);
        }
    }
}
