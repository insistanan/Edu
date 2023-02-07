package com.anan.ucenterService.controller;


import com.anan.commonUtils.R;
import com.anan.ucenterService.entity.EduChapter;
import com.anan.ucenterService.entity.vo.chapter.ChapterVO;
import com.anan.ucenterService.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/eduService/edu-chapter")
@Api(description = "章节管理")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @GetMapping("getCharpterVideo/{courseId}")
    @ApiOperation("根据课程id获取章节和小节数据列表")
    public R getCharpterVideo(@PathVariable String courseId){
        List<ChapterVO> chapterVideoList = eduChapterService.getChapterVideos(courseId);
        return R.ok().data("items",chapterVideoList);
    }
    @PostMapping("addChapter")
    @ApiOperation("添加章节")
    public R addChapter(@RequestBody EduChapter chapter){
        boolean save = eduChapterService.save(chapter);
        if (save) {
            return R.ok();
        }
        return R.error();
    }
    @GetMapping("getChapter/{chapterId}")
    @ApiOperation("根据id查询章节")
    public R getChapter(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }
    @PostMapping("updateChapter")
    @ApiOperation("修改章节")
    public R updateChapter(@RequestBody EduChapter chapter){
        boolean update = eduChapterService.update(chapter, null);
        if (update) {
            return R.ok();
        }
        return R.error();
    }
    @DeleteMapping("deleteChapter/{chapterId}")
    @ApiOperation("删除章节")
    public R deleteChapter(@PathVariable String chapterId){
        boolean remove = eduChapterService.removeChapterById(chapterId);
        if (remove) {
            return R.ok();
        }
        return R.error().message("delete error");
    }
}

