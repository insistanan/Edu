package com.anan.eduService.controller;


import com.anan.commonUtils.R;
import com.anan.eduService.entity.EduVideo;
import com.anan.eduService.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/eduService/edu-video")
@Api(description = "课时小节管理")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @PostMapping("addVideo")
    @ApiOperation("添加小节")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = videoService.save(eduVideo);
        if (save) {
            return R.ok();
        }
        return R.error();
    }
    @DeleteMapping("deleteVideo/{id}")
    @ApiOperation("删除小节")
    public R deleteVideo(@PathVariable String id){
        boolean remove = videoService.removeById(id);
        if (remove) {
            return R.ok();
        }
        return R.error();
    }
}

