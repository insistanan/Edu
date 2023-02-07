package com.anan.ucenterService.service.impl;

import com.anan.ucenterService.entity.EduChapter;
import com.anan.ucenterService.entity.EduVideo;
import com.anan.ucenterService.entity.vo.chapter.ChapterVO;
import com.anan.ucenterService.entity.vo.chapter.VideoVO;
import com.anan.ucenterService.mapper.EduChapterMapper;
import com.anan.ucenterService.service.EduChapterService;
import com.anan.ucenterService.service.EduVideoService;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVO> getChapterVideos(String courseId) {
        //最终返回数据列表（chapterVideo）
        ArrayList<ChapterVO> chapterVOArrayList = new ArrayList<>();

        //查询章节信息
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        wrapper1.orderByAsc("sort","id");
        List<EduChapter> chapters = baseMapper.selectList(wrapper1);
        //获取课时信息
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",courseId);
        wrapper2.orderByAsc("sort","id");
        List<EduVideo> videos = videoService.list(wrapper2);

        //填充章节vo数据
        for (int i = 0; i < chapters.size(); i++) {
            EduChapter chapter = chapters.get(i);
            //创建章节VO对象
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter,chapterVO);
            chapterVOArrayList.add(chapterVO);

            //填充课时VO数据
            ArrayList<VideoVO> videoVOArrayList = new ArrayList<>();
            for (int j = 0; j < videos.size(); j++) {
                EduVideo video = videos.get(j);
                if (chapter.getId().equals(video.getChapterId())){
                    //创建课时VO对象
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(video,videoVO);
                    videoVOArrayList.add(videoVO);
                }
            }
            chapterVO.setChildren(videoVOArrayList);
        }
        return chapterVOArrayList;
    }

    @Override
    public boolean removeChapterById(String chapterId) {
        //根据id查询是否存在视频，如果有则提示用户有子节点
        if (videoService.getCountByChapterId(chapterId)){
            throw new ananException(20001,"该分章节下存在视频课程，请先删除视频课程");
        }
        Integer result = baseMapper.deleteById(chapterId);
        return null != result && result > 0 ;
    }

    @Override
    public boolean removeByCourseId(Integer courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        Integer delete = baseMapper.delete(wrapper);
        return null != delete && delete > 0;
    }
}
