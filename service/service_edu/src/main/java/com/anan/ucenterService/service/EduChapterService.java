package com.anan.ucenterService.service;

import com.anan.ucenterService.entity.EduChapter;
import com.anan.ucenterService.entity.vo.chapter.ChapterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author anan_
 * @since 2022-12-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideos(String courseId);

    boolean removeChapterById(String chapterId);

    boolean removeByCourseId(Integer courseId);
}
