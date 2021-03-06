package com.originaldreams.publicservicecenter.controller;

import com.originaldreams.common.response.MyResponse;
import com.originaldreams.common.response.MyServiceResponse;
import com.originaldreams.common.util.StringUtils;
import com.originaldreams.common.util.ValidUserName;
import com.originaldreams.publicservicecenter.entity.EmailEntity;
import com.originaldreams.publicservicecenter.utils.SendEmailUtils;
import com.originaldreams.publicservicecenter.utils.SendSMSUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/email")
public class EmailController {
    @RequestMapping(value = "/sendText",method = RequestMethod.GET)
    public ResponseEntity sendText(String emailAddress,String title,String content){
        try{
            if(emailAddress == null || title == null || content == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("请求参数异常！！！");
            }else{
                SendEmailUtils.sendSimpleMail(emailAddress,title,content);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).contentType(MediaType.APPLICATION_JSON).body("服务异常！！！" + e.getMessage());

        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("base");
    }

    @RequestMapping(value = "/sendVerificationCode" ,method = RequestMethod.GET)
    public ResponseEntity sendVerificationCode(String email){
        if(StringUtils.isEmpty(email) || !ValidUserName.isValidEmailAddress(email)){
            return MyResponse.badRequest();
        }
        MyServiceResponse response =new MyServiceResponse();

        EmailEntity entity = SendEmailUtils.sendVerificationCode(email);
        return MyResponse.ok(response);
    }
}
