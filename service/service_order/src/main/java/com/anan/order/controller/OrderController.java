package com.anan.order.controller;

import com.anan.commonUtils.JwtUtils;
import com.anan.commonUtils.R;
import com.anan.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/order")
@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    private TOrderService orderService;

    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request){
        String orderId = orderService.saveorder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }
}
