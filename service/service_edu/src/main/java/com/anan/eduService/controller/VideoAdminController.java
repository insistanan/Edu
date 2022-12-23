package com.anan.eduService.controller;

import com.anan.commonUtils.R;
import com.anan.eduService.entity.VideoInfoForm;
import com.anan.eduService.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(description = "课时信息管理")
@RequestMapping("/admin/edu/video")
public class VideoAdminController {
    @Autowired
    private EduVideoService videoService;

    @ApiOperation("新增课时")
    @PostMapping("sava-video-info")
    public R save(
            @ApiParam(name = "videoForm",value = "课时对象",required = true)
            @RequestBody VideoInfoForm videoInfoForm){
        videoService.saveVideoInfo(videoInfoForm);
        return R.ok();
    }

    @ApiOperation("根据ID查询课时")
    @GetMapping("video-info/{id}")
    public R getVideoInfoById(
            @ApiParam(name = "id",value = "课时id",required = true)
            @PathVariable String id
    ){
        VideoInfoForm videoInfoFormById = videoService.getVideoInfoFormById(id);
        return R.ok().data("item",videoInfoFormById);
    }

    @ApiOperation("更新课时")
    @PostMapping("update-video-info/{id}")
    public R updateCourseInfoById(
            @PathVariable String id,
            @ApiParam(name = "videoInfoForm",value = "课时基本信息",required = true)
            @RequestBody VideoInfoForm videoInfoForm){
        videoService.updateVideoInfoById(videoInfoForm);
        return R.ok();
    }

    @ApiOperation("根据课时id进行删除")
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id){
        boolean removeVideoById = videoService.removeVideoById(id);
        if (removeVideoById) {
            return R.ok();
        }
        return R.error();
    }
}
