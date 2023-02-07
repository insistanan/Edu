package com.anan.ucenterService.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelData> {
    /**
     * 读取表头内容
     *
     * @param headMap 头图
     * @param context 上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头："+headMap);
    }

    /**
     * 一行一行读取excel内容
     *
     * @param excelData       excel数据
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        System.out.println("****"+excelData);
    }

    /**
     * 读取完成之后
     *
     * @param analysisContext 分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
