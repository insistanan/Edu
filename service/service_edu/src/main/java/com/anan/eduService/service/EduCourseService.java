package com.anan.eduService.service;

import com.anan.eduService.entity.EduCourse;
import com.anan.eduService.entity.vo.CourseInfoVO;
import com.anan.eduService.entity.vo.CoursePublishVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程信息
     *
     * @param courseInfoVO 课程信息签证官
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);

    CoursePublishVO selectCoursePublishVOById(String id);

    void publishCourseById(String id);
}
