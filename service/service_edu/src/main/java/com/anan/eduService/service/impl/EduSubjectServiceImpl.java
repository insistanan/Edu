package com.anan.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.anan.eduService.entity.EduSubject;
import com.anan.eduService.entity.excel.SubjectData;
import com.anan.eduService.entity.subject.OneSubject;
import com.anan.eduService.entity.subject.TwoSubject;
import com.anan.eduService.listener.SubjectExcelListener;
import com.anan.eduService.mapper.EduSubjectMapper;
import com.anan.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2022-12-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> one = baseMapper.selectList(wrapperOne);

        //查询所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> two = baseMapper.selectList(wrapperTwo);

        //创建list集合，存储最终封装数据
        ArrayList<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        //查询出来的所有一级分类list集合遍历，得到每个一级分类对象，获取，每个一级分类对象值
        //封装到要求的list集合里面
        for (int i = 0; i < one.size(); i++) {
            //得到one的每一个eduSubject对象
            EduSubject eduSubject = one.get(i);
            OneSubject oneSubject = new OneSubject();
            /*oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());*/
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);
            //封装二级分类
            //在一级分类下循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            ArrayList<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < two.size(); j++) {
                //获取每个二级分类
                EduSubject eduSubject1 = two.get(j);
                //判断属于哪个一级分类
                if (eduSubject1.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject1,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有的二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
