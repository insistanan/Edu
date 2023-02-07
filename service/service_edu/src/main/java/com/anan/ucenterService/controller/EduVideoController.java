package com.anan.ucenterService.controller;


import com.anan.commonUtils.R;
import com.anan.ucenterService.client.VodClient;
import com.anan.ucenterService.entity.EduVideo;
import com.anan.ucenterService.service.EduVideoService;
import com.anan.serviceBase.exceptionHandler.ananException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    //注入暴露的依赖

    @Autowired
    private VodClient vodClient;

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
        //根据小节id获取视频id
        //调用方法根据视频id删除视频
        EduVideo video = videoService.getById(id);
        //删除视频
        if (!StringUtils.isEmpty(video.getVideoSourceId())){
            //根据视频id，远程调用实现视频删除
            R r = vodClient.deleteVideoByVideoId(video.getVideoSourceId());
            if (r.getCode() == 20001){
                throw new ananException(20001,"删除视频失败，熔断器执行了......");
            }
        }
        //删除小节
        boolean remove = videoService.removeById(id);
        if (remove) {
            return R.ok();
        }
        return R.error();
    }
}

