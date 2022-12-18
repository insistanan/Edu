package com.anan.serviceBase.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   //生成无参构造
@AllArgsConstructor  //生成有参构造
public class ananException extends RuntimeException{
    //异常状态码
    private Integer code;
    //异常信息
    private String msg;


}
