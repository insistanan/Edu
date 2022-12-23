package com.anan.eduService.controller;


import com.anan.commonUtils.R;
import com.anan.eduService.entity.vo.CourseInfoVO;
import com.anan.eduService.entity.vo.CoursePublishVO;
import com.anan.eduService.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/eduService/edu-course")
@Api(description = "课程管理")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    /**
     * 添加课程信息
     *
     * @param courseInfoVO 课程信息签证官
     * @return {@link R}
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVO courseInfoVO){
        String cid = courseService.saveCourseInfo(courseInfoVO);
        return R.ok().data("courseId",cid);
    }
    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVOById(
            @ApiParam(name = "id",value = "课程ID",required = true)
            @PathVariable String id
            ){
        CoursePublishVO coursePublishVO = courseService.selectCoursePublishVOById(id);
        return R.ok().data("item",coursePublishVO);
    }
    @ApiOperation("根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(@PathVariable String id){
        courseService.publishCourseById(id);
        return R.ok();
    }
}

