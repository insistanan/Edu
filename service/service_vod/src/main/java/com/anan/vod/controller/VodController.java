package com.anan.vod.controller;

import com.anan.commonUtils.R;
import com.anan.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description = "阿里云视频点播微服务")
@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    /**
     * 阿里巴巴上传视频 上传视频到阿里云
     *
     * @param file 文件
     * @return {@link R}
     */
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file){
        String videoId = vodService.uploadVideoALY(file);
        return R.ok().message("视频上传成功").data("videoId",videoId);
    }

    /**
     * 根据视频id删除视频
     * 视频id
     *
     * @param videoid vedioid
     * @return {@link R}
     */
    @DeleteMapping("{videoid}")
    public R deleteVideoByVideoId(@PathVariable String videoid){
        Boolean b = vodService.deleteVideoById(videoid);
        if (b){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }
    }
    @DeleteMapping("delete-batch")
    @ApiOperation("删除多个视频")
    public R deleteBatch(@RequestParam List<String> videoIdList){
        vodService.removeVideos(videoIdList);
        return R.ok();
    }
}
