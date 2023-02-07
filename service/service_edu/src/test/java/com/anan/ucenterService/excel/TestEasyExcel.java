package com.anan.ucenterService.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //1 设置文件夹地址和excel文件名称
        String filename = "D:\\work\\jetbrain\\idea\\EduOnline\\service\\service_edu\\src\\test\\resources\\test.xlsx";

        //2 调用easyExcel中的方法实现写操作
        //write 方法里面有两个参数  第一个参数是文件路径名称  第二个参数是实体类class
        //EasyExcel.write(filename, ExcelData.class).sheet("学生列表").doWrite(getData());

        //2 读操作
        EasyExcel.read(filename, ExcelData.class,new ExcelListener()).sheet().doRead();
    }

    /**
     * 获取数据
     *
     * @return {@link List}<{@link ExcelData}>
     */
    private  static List<ExcelData> getData(){
        ArrayList<ExcelData> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelData data = new ExcelData();
            data.setSno(i);
            data.setSname("anan"+i);
            arrayList.add(data);
        }
        return arrayList;
    }
}
