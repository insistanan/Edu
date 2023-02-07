package com.anan.ucenterService.controller;

import com.anan.commonUtils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService/user")
//解决跨域问题
@CrossOrigin
public class EduLoginController {

    /**
     * 登录
     *
     * @return {@link R}
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    /**
     * 信息info
     *
     * @return {@link R}
     */
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
