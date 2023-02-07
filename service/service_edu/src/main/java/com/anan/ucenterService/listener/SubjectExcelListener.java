package com.anan.ucenterService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.anan.ucenterService.entity.EduSubject;
import com.anan.ucenterService.entity.excel.SubjectData;
import com.anan.ucenterService.service.EduSubjectService;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取excel内容，一行一行读取
     *
     * @param subjectData     主题数据
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new ananException(20001,"文件数据为空");
        }else {
            //一行一行读取，每一行有两个值，第一个值为一级分类，第二个值为二级分类
            //判断一级分类是否重复
            EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
            if (existOneSubject == null){
                //没有相同一级分类，不重复，进行添加
                existOneSubject = new EduSubject();
                existOneSubject.setParentId("0");
                existOneSubject.setTitle(subjectData.getOneSubjectName());
                subjectService.save(existOneSubject);
            }
            //判断二级分类是否重复
            String pid = existOneSubject.getId();
            EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
            if (existTwoSubject == null){
                existTwoSubject = new EduSubject();
                existTwoSubject.setParentId(pid);
                existTwoSubject.setTitle(subjectData.getTwoSubjectName());
                subjectService.save(existTwoSubject);
            }
        }
    }

    /**
     * 判断一级分类重复添加
     *
     * @param subjectService 主题服务
     * @param name           名字
     * @return {@link EduSubject}
     */
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }
    /**
     * 判断二级分类重复添加
     *
     * @param subjectService 主题服务
     * @param name           名字
     * @return {@link EduSubject}
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
