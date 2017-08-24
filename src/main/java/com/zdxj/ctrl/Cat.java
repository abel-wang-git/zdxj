package com.zdxj.ctrl;

import com.zdxj.croe.Dbconnect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;

@Controller
public class Cat {


    @GetMapping(value = "/")
    public String toIndex(){
        Connection c=Dbconnect.dbConnect("","","");
        return "admin";
    }

}
