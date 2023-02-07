package com.anan.ucenterService.service.impl;

import com.anan.ucenterService.entity.EduCourse;
import com.anan.ucenterService.entity.EduCourseDescription;
import com.anan.ucenterService.entity.vo.CourseInfoVO;
import com.anan.ucenterService.entity.vo.CoursePublishVO;
import com.anan.ucenterService.entity.vo.CourseQuery;
import com.anan.ucenterService.mapper.EduCourseMapper;
import com.anan.ucenterService.service.EduChapterService;
import com.anan.ucenterService.service.EduCourseDescriptionService;
import com.anan.ucenterService.service.EduCourseService;
import com.anan.ucenterService.service.EduVideoService;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;
    @Override
    public String saveCourseInfo(CourseInfoVO courseInfoVO) {
        //1 向课程表里添加课程基本信息
        //将CourseInfoVOid对象转换为eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0){
            //添加失败
            throw new ananException(20001,"添加课程信息失败");
        }

        //获取添加之后的课程id
        String cid = eduCourse.getId();

        //2 向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CoursePublishVO selectCoursePublishVOById(String id) {
        return baseMapper.selectCoursePublishVOById(id);
    }

    @Override
    public void publishCourseById(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        baseMapper.updateById(eduCourse);
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        //查询课程对象为空的情况下，将该页所有数据返回
        if (courseQuery == null){
            baseMapper.selectPage(pageParam,queryWrapper);
        }else {
        //不为空的情况下，执行下面的操作
            String title = courseQuery.getTitle();
            String teacherId = courseQuery.getTeacherId();
            String subjectParentId = courseQuery.getSubjectParentId();
            String subjectId = courseQuery.getSubjectId();
            //对这些参数进行非空判断
            if (!StringUtils.isEmpty(title)) {
                queryWrapper.like("title", title);
            }

            if (!StringUtils.isEmpty(teacherId) ) {
                queryWrapper.eq("teacher_id", teacherId);
            }

            if (!StringUtils.isEmpty(subjectParentId)) {
                queryWrapper.ge("subject_parent_id", subjectParentId);
            }

            if (!StringUtils.isEmpty(subjectId)) {
                queryWrapper.ge("subject_id", subjectId);
            }
            //进行查询
            baseMapper.selectPage(pageParam,queryWrapper);
        }
    }

    @Override
    public boolean removeByCourseId(Integer courseId) {
        //首先删除video记录，然后删除chapter记录，最后删除Course记录
        boolean video = videoService.removeByCourseId(courseId);
        boolean chapter = chapterService.removeByCourseId(courseId);
        Integer course = baseMapper.deleteById(courseId);
        return null != course && course > 0;
    }

    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse,courseInfoVO);
        //查询描述表
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(description.getDescription());
        return courseInfoVO;
    }

    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.update(eduCourse, null);
        if (update==0){
            throw new ananException(20001,"修改课程信息失败");
        }
        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVO publishCourseInfo(String id) {
        CoursePublishVO coursePublishVO = baseMapper.selectCoursePublishVOById(id);
        return coursePublishVO;
    }

    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        videoService.removeVideoById(courseId);
        //2 根据课程id删除章节
        chapterService.removeChapterById(courseId);
        //3 根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);
        //4 根据课程id删除课程本身
        Integer delete = baseMapper.deleteById(courseId);
        if (delete == 0){
            throw new ananException(20001,"删除失败");
        }
    }
}
