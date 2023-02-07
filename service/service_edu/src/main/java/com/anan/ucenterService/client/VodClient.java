package com.anan.ucenterService.client;

import com.anan.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义调用的方法路径

    /**
     * 远程删除视频,视频id
     *
     * @param videoid videoid
     * @return {@link R}
     */
    @DeleteMapping("/eduVod/video/{videoid}")
    public R deleteVideoByVideoId(@PathVariable("videoid") String videoid);

    /**
     * 远程批处理删除视频
     *
     * @param videoIdList 视频id列表
     * @return {@link R}
     */
    @DeleteMapping("/eduVod/video/delete-batch")
    public R deleteBatch(@RequestParam List<String> videoIdList);
}
