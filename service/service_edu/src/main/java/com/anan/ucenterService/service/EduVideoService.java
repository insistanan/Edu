package com.anan.ucenterService.service;

import com.anan.ucenterService.entity.EduVideo;
import com.anan.ucenterService.entity.VideoInfoForm;
import com.baomidou.mybatisplus.extension.service.IService;

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

    boolean removeByCourseId(Integer courseId);
}
