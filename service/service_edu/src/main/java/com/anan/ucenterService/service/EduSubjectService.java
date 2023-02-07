package com.anan.ucenterService.service;

import com.anan.ucenterService.entity.EduSubject;
import com.anan.ucenterService.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author anan_
 * @since 2022-12-21
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 上传课程
     *
     * @param file              文件
     * @param eduSubjectService edu主题服务
     */
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 获取全部课程
     *
     * @return {@link List}<{@link OneSubject}>
     */
    List<OneSubject> getAllOneTwoSubject();
}
