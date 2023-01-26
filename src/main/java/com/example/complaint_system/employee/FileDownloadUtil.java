package com.example.complaint_system.employee;

import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;

public class FileDownloadUtil {
    private Path foundFile;

    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("Pictures");
        return getResource(fileCode, dirPath);
    }

    @Nullable
    private Resource getResource(String fileCode, Path dirPath) throws IOException {
        fileCode = fileCode.substring(3, fileCode.length());
        String finalFileCode = fileCode;
        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(finalFileCode)) {
                foundFile = file;
                return;
            }
        });
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}