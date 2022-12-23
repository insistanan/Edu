package com.anan.eduService.service;

import com.anan.eduService.entity.EduVideo;
import com.anan.eduService.entity.VideoInfoForm;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 判断某章节下是否有课程
     *
     * @param chapterId 章id
     * @return boolean
     */
    boolean getCountByChapterId(String chapterId);

    void saveVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    boolean removeVideoById(String id);
}
