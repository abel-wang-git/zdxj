package com.zdxj.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Cat {
    @GetMapping(value = "/")
    public String toIndex(){
        return "admin";
    }

}
