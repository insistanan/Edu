package com.anan.eduService.controller;


import com.anan.commonUtils.R;
import com.anan.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author anan_
 * @since 2022-12-21
 */
@RestController
@RequestMapping("/eduService/edu-subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的Excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

}

