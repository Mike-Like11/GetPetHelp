package com.mikelike.getpethelp.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CloudinaryService {
    String uploadFile(MultipartFile file);
}
