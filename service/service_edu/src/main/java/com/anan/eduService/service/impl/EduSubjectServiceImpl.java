package com.anan.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.anan.eduService.entity.EduSubject;
import com.anan.eduService.entity.excel.SubjectData;
import com.anan.eduService.listener.SubjectExcelListener;
import com.anan.eduService.mapper.EduSubjectMapper;
import com.anan.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2022-12-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
