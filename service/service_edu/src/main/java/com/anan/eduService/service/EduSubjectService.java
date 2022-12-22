package com.anan.eduService.service;

import com.anan.eduService.entity.EduSubject;
import com.anan.eduService.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
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
@Service
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
