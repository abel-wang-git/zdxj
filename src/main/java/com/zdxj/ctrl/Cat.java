package com.zdxj.ctrl;

import com.zdxj.Constant;
import com.zdxj.croe.Dbconnect;
import com.zdxj.croe.MyStartupRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Cat {


    @GetMapping(value = "/")
    public String toIndex(Model model){
        model.addAttribute("datasource",MyStartupRunner.source);
        return "admin";
    }
    @GetMapping(value = "/cat/{id}")
    public String Cat(@PathVariable int id,Model model){
        Connection connect=Dbconnect.dbConnect(Dbconnect.getDataSource(id));
        List datafile= Dbconnect.query(connect,Constant.datafile);

        model.addAttribute("datafile",datafile);

        List segment = Dbconnect.query(connect,Constant.segment);

        model.addAttribute("segment",segment);

        List tablespace = Dbconnect.query(connect,Constant.tablespace);

        model.addAttribute("tablespace",tablespace);

        List session = Dbconnect.query(connect,Constant.session);

        model.addAttribute("session",session);

        List avticeSession = Dbconnect.query(connect,Constant.avtiveSession);

        model.addAttribute("avticese",avticeSession);

        List sessionGroupUser = Dbconnect.query(connect,Constant.sessionGroupUser);

        model.addAttribute("sessGrop",sessionGroupUser);



        return "detail";
    }

}
