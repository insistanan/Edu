package com.anan.ucenterService.service.impl;

import com.anan.ucenterService.client.VodClient;
import com.anan.ucenterService.entity.EduVideo;
import com.anan.ucenterService.entity.VideoInfoForm;
import com.anan.ucenterService.mapper.EduVideoMapper;
import com.anan.ucenterService.service.EduVideoService;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private VodClient vodClient;

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

    @Override
    public boolean removeByCourseId(Integer courseId) {
        //根据视频id查询课程所有的视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<EduVideo> videos = baseMapper.selectList(wrapper);

        List<String> videoIdList = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            String id = videos.get(i).getVideoSourceId();
            if (!StringUtils.isEmpty(id)){
                videoIdList.add(id);
            }
        }
        //删除多个视频
        if (videoIdList.size()>0){
            vodClient.deleteBatch(videoIdList);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        Integer delete = baseMapper.delete(queryWrapper);
        return null != delete && delete > 0;
    }
}
