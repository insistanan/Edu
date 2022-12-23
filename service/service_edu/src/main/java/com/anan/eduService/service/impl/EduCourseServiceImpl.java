package com.anan.eduService.service.impl;

import com.anan.eduService.entity.EduCourse;
import com.anan.eduService.entity.EduCourseDescription;
import com.anan.eduService.entity.vo.CourseInfoVO;
import com.anan.eduService.entity.vo.CoursePublishVO;
import com.anan.eduService.mapper.EduCourseMapper;
import com.anan.eduService.service.EduCourseDescriptionService;
import com.anan.eduService.service.EduCourseService;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
