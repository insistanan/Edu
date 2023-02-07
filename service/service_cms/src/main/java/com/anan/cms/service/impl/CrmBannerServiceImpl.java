package com.anan.cms.service.impl;

import com.anan.cms.entity.CrmBanner;
import com.anan.cms.mapper.CrmBannerMapper;
import com.anan.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2023-01-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 查询所有banner
     *
     * @return {@link List}<{@link CrmBanner}>
     */
    @Cacheable(key = "selectIndexList",value = "banner")
    @Override
    public List<CrmBanner> selectIndexList() {
        //根据id进行降序排列，显示排列之后前两条数据
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
