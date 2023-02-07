package com.anan.ucenterService.service;

import com.anan.ucenterService.entity.EduCourse;
import com.anan.ucenterService.entity.vo.CourseInfoVO;
import com.anan.ucenterService.entity.vo.CoursePublishVO;
import com.anan.ucenterService.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean removeByCourseId(Integer courseId);

    CourseInfoVO getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVO courseInfoVo);

    CoursePublishVO publishCourseInfo(String id);

    void removeCourse(String courseId);
}
