package com.anan.eduService.controller;


import com.anan.commonUtils.R;
import com.anan.eduService.entity.EduTeacher;
import com.anan.eduService.entity.vo.TeacherQuery;
import com.anan.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author anan_
 * @since 2022-12-17
 */

@RestController
@RequestMapping("/eduService/edu-teacher")
@Api(description = "讲师管理")
@CrossOrigin
public class EduTeacherController {

    //http://localhost:8001/eduService/edu-teacher/findAllTeacher
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 找到所有老师
     *
     * @return {@link List}<{@link EduTeacher}>
     */
    @GetMapping("findAllTeacher")
    @ApiOperation("查找所有讲师")
    public List<EduTeacher> findAllTeacher(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return teachers;
    }

    /**
     * 逻辑删除教师通过id
     *
     * @param id id
     * @return boolean
     */
    @DeleteMapping("deleteTeacherByID/{id}")
    @ApiOperation("根据id逻辑删除讲师")
    public boolean deleteTeacherByID(
            @ApiParam(name="id",value="讲师ID",required = true)
            @PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        return result;
    }

    //改成统一返回结果
    @GetMapping("findAll")
    @ApiOperation("查询所有讲师R")
    public R findAll(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("teachers",teachers);
    }

    @DeleteMapping("deleteTeacher/{id}")
    @ApiOperation("根据id逻辑删除讲师R")
    public R deleteTeacherByIDR(
            @ApiParam(name="id",value="讲师IDR",required = true)
            @PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //分页查询讲师的方法
//    @GetMapping("pageTeacher/{page}/{limit}")
//    public R pageListTeacher(
//            @PathVariable long page,
//            @PathVariable long limit){
//        //创建page对象
//        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
//        eduTeacherService.page(pageTeacher,null);
//        long total = pageTeacher.getTotal();  //总记录数
//        List<EduTeacher> records = pageTeacher.getRecords();  //数据list集合
//        return R.ok().data("total",total).data("rows",records);
//    }
    //多条件组合查询带分页
    @PostMapping("multiPage/{page}/{limit}")
    public R multiPage(@PathVariable long page,
                       @PathVariable long limit,
                       @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }if (!StringUtils.isEmpty(level)){
            //构建条件
            wrapper.eq("level",level);
        }if (!StringUtils.isEmpty(begin)){
            //构建条件
            wrapper.gt("gmt_create",begin);
        }if (!StringUtils.isEmpty(end)){
            //构建条件
            wrapper.le("gmt_create",end);
        }
        //调用方法实现条件查询
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();  //总记录数
        List<EduTeacher> records = pageTeacher.getRecords();  //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }
        return R.error();
    }
    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher",byId);
    }
    //根据id进行修改讲师
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean updateById = eduTeacherService.updateById(eduTeacher);
        if (updateById){return R.ok();}
        return R.error();
    }
}

