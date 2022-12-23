package com.anan.eduService.service.impl;

import com.anan.eduService.entity.EduVideo;
import com.anan.eduService.entity.VideoInfoForm;
import com.anan.eduService.mapper.EduVideoMapper;
import com.anan.eduService.service.EduVideoService;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        Integer integer = baseMapper.selectCount(wrapper);
        return null!=integer && integer>0;
    }

    @Override
    public void saveVideoInfo(VideoInfoForm videoInfoForm) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,eduVideo);
        int insert = baseMapper.insert(eduVideo);
        if (!(insert > 0) ){
            throw new ananException(20001,"课时信息保存失败");
        }
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        if (eduVideo == null){
            throw new ananException(20001,"数据不存在");
        }
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(eduVideo,videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,eduVideo);
        int updateById = baseMapper.updateById(eduVideo);
        if ( !(updateById>0) ){
            throw new ananException(20001,"课时信息保存失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {
        Integer i = baseMapper.deleteById(id);
        return null != i && i>0;
    }
}
