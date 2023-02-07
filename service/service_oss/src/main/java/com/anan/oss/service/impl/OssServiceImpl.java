package com.anan.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.anan.oss.service.OssService;
import com.anan.oss.util.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatat(MultipartFile file) {
        //通过工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {
            //创建OSS实例
            OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String originalFilename = file.getOriginalFilename();
            //在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            originalFilename = uuid+originalFilename;
            //把文件按照日期进行分类
            // 1.SimpleDateFormat 2.joda-time依赖
            /*
            1.SimpleDateFormat
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String time = simpleDateFormat.format(date.getTime());
            */
            //2.joda-time依赖
            String date = new DateTime().toString("yyyy年MM月dd日");
            originalFilename = date + "/" + originalFilename;

            //调用oss方法实现上传
            //第一个参数  bucket名称
            //第二个参数  上传到oss文件路径和文件名称   /aa/bb/1.jpg
            //这里加了avatar目录 意思就是上传到/avatar/originalFilename
            //第三个参数  文件输入流
            oss.putObject(bucketName,"avatar/"+originalFilename,inputStream);
            //关闭OSS
            oss.shutdown();
            //把上传之后的文件路径返回（自己拼接）
            //https://anan-edu.oss-cn-hangzhou.aliyuncs.com/avatar/01.jpg
            String url = "https://" + bucketName + "." + endpoint + "/avatar/" + originalFilename;
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
