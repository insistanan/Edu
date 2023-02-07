package com.anan.ucenterService.mapper;

import com.anan.ucenterService.entity.EduCourse;
import com.anan.ucenterService.entity.vo.CoursePublishVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVO selectCoursePublishVOById(String id);
}
