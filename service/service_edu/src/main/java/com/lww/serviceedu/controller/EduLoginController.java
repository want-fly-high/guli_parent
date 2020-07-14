package com.lww.serviceedu.controller;

import com.lww.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduservice/user")
@CrossOrigin
public class EduLoginController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles", "admin")
                .data("name","admin")
                .data("avatar", "https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2278899512,2945856051&fm=26&gp=0.jpg");
    }
}
