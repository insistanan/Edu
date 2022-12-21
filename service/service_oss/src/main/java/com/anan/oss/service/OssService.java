package com.anan.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {
    public String uploadFileAvatat(MultipartFile file);
}
