package com.anan.ucenterService.controller;

import com.anan.commonUtils.R;
import com.anan.ucenterService.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/course-admin")
@CrossOrigin
@Api(description = "管理员删除课程")
public class CourseAdminController {
    @Autowired
    private EduCourseService courseService;
    @ApiOperation("根据id删除课程")
    @DeleteMapping("remove/{CourseId}")
    public R removeById(@PathVariable Integer CourseId){
        boolean remove = courseService.removeByCourseId(CourseId);
        if (remove){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }

    }
}
