package com.anan.eduService.entity.vo.chapter;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;
}
