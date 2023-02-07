package com.anan.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    /**
     * 上传视频到阿里
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadVideoALY(MultipartFile file);

    /**
     * 通过id删除视频
     *
     * @param vedioid vedioid
     * @return {@link Boolean}
     */
    Boolean deleteVideoById(String vedioid);


    /**
     * 删除多个视频
     *
     * @param videoIdList 视频id列表
     */
    void removeVideos(List videoIdList);
}
