package com.anan.cms.controller;

import com.anan.cms.entity.CrmBanner;
import com.anan.cms.service.CrmBannerService;
import com.anan.commonUtils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台banner管理控制器
 *
 * @author anan_
 * @date 2023/01/02
 */
@RestController
@RequestMapping("/eduCms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    CrmBannerService bannerService;

    /**
     * 分页查询所有
     *
     * @param page  页面
     * @param limit 限制
     * @return {@link R}
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    /**
     * 添加banner
     *
     * @param crmBanner crm横幅
     * @return {@link R}
     */
    @PostMapping("addBanner")
    @ApiOperation("添加banner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }
    @GetMapping("getBanner/{id}")
    @ApiOperation("根据id获取banner")
    public R getBanner(@PathVariable long id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }
    @PutMapping("updateBanner")
    @ApiOperation("修改banner")
    public R updateBanner(@RequestBody CrmBanner banner){
        bannerService.updateById(banner);
        return R.ok();
    }

    @DeleteMapping("deleteBanner/{id}")
    @ApiOperation("根据id删除banner")
    public R deleteBanner(@PathVariable long id){
        bannerService.removeById(id);
        return R.ok();
    }
}
