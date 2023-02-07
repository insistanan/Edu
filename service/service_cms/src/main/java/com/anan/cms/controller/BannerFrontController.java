package com.anan.cms.controller;


import com.anan.cms.entity.CrmBanner;
import com.anan.cms.service.CrmBannerService;
import com.anan.commonUtils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台banner控制器
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author anan_
 * @date 2023/01/02
 * @since 2023-01-02
 */
@RestController
@RequestMapping("/eduCms/bannerFront")
@CrossOrigin
@Api(description = "网站首页banner列表")
public class BannerFrontController {
    @Autowired
    CrmBannerService bannerService;

    /**
     * 查询所有banner
     *
     * @return {@link R}
     */
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner>bannerList = bannerService.selectIndexList();
        return R.ok().data("list",bannerList);
    }

}

