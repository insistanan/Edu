package com.anan.eduService.entity.vo.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVO {
    private String id;
    private String title;

    private List<VideoVO> children = new ArrayList<>();
}
