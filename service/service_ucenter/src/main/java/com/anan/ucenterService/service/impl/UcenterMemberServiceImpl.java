package com.anan.ucenterService.service.impl;

import com.anan.commonUtils.JwtUtils;
import com.anan.serviceBase.exceptionHandler.ananException;
import com.anan.ucenterService.entity.VO.LoginVo;
import com.anan.ucenterService.entity.VO.RegisterVo;
import com.anan.ucenterService.entity.UcenterMember;
import com.anan.ucenterService.mapper.UcenterMemberMapper;
import com.anan.ucenterService.service.UcenterMemberService;
import com.anan.ucenterService.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author anan_
 * @since 2023-02-06
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 登录
     *
     * @param loginVo 登录签证官
     * @return {@link String}
     */
    @Override
    public String login(LoginVo loginVo) {
        //获取登录手机号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile)) {
            throw new ananException(20001,"error");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        //获取会员
        UcenterMember member = baseMapper.selectOne(wrapper);
        if(null == member) {
            throw new ananException(20001,"error");
        }

        //校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new ananException(20001,"error");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new ananException(20001,"error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    /**
     * 注册
     *
     * @param registerVo 注册签证官
     */
    @Override
    public void register(RegisterVo registerVo) {
//获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new ananException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new ananException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new ananException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://anan-edu.oss-cn-hangzhou.aliyuncs.com/avatar/mojing.png");
        baseMapper.insert(member);
    }
}
