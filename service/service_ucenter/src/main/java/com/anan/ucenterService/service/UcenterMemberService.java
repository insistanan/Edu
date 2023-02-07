package com.anan.ucenterService.service;

import com.anan.ucenterService.entity.VO.LoginVo;
import com.anan.ucenterService.entity.VO.RegisterVo;
import com.anan.ucenterService.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author anan_
 * @since 2023-02-06
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);
}
