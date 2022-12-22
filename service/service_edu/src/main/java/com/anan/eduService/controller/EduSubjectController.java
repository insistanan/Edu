package com.anan.eduService.controller;


import com.anan.commonUtils.R;
import com.anan.eduService.entity.EduSubject;
import com.anan.eduService.entity.subject.OneSubject;
import com.anan.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 添加课程
     *
     * @param file 文件
     * @return {@link R}
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的Excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    /**
     * 课程分类列表（树形）
     *
     * @return {@link R}
     */
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合中的泛型是一级分类  因为一级分类中有它本身，还有二级分类
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

