package com.anan.oss.controller;

import com.anan.commonUtils.R;
import com.anan.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    OssService ossService;
    /**
     * 上传文件到OSS
     *
     * @return {@link R}
     */
    @PostMapping("upload")
    public R uploadOssFile(MultipartFile file){
        //获取上传文件MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatat(file);
        return R.ok().data("url",url);
    }
}
