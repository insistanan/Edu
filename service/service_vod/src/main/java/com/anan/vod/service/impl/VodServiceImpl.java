package com.anan.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.anan.vod.service.VodService;
import com.anan.vod.util.AliyunVodSDKUtil;
import com.anan.vod.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoALY(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title,filename,inputStream
            );
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();

            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new ananException(20001, errorMessage);
                }
            }

            return videoId;
        }catch (Exception e){
            throw new ananException(20001,"anan vod服务上传失败");
        }

    }

    @Override
    public Boolean deleteVideoById(String vedioid) {
        Boolean flag = false;
        try{
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET
            );
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(vedioid);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " +response.getRequestId() + "\n");
            if (response.getRequestId()!=null) {
                flag = true;
            }
        }catch (Exception e){
            throw new ananException(20001,"视频删除失败");
        }
        return flag;
    }

    @Override
    public void removeVideos(List videoIdList) {
        try{
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET
            );
            DeleteVideoRequest request = new DeleteVideoRequest();
            //String collect = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            String collect = (String) videoIdList.stream().collect(Collectors.joining(","));
            request.setVideoIds(collect);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " +response.getRequestId() + "\n");
            if (response.getRequestId()!=null) {
            }
        }catch (Exception e){
            throw new ananException(20001,"视频删除失败");
        }
    }
}
