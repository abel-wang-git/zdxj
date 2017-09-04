package com.zdxj.ctrl;

import com.zdxj.Constant;
import com.zdxj.croe.Dbconnect;
import com.zdxj.croe.MyStartupRunner;
import com.zdxj.croe.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.*;
import java.util.*;

@Controller
public class Cat {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/")
    public String toIndex(Model model){
        model.addAttribute("datasource",MyStartupRunner.source);
        return "admin";
    }
    @GetMapping(value = "/cat/{id}")
    public String Cat(@PathVariable int id,Model model){
        Connection connect=null;
        List datafile=null;
        try{
            connect=Dbconnect.dbConnect(Dbconnect.getDataSource(id));
            datafile= Dbconnect.query(connect,Constant.datafile);
        model.addAttribute("datafile",datafile);

        List segment = Dbconnect.query(connect,Constant.segment);

        model.addAttribute("segment",segment);

        List tablespace = Dbconnect.query(connect,Constant.tablespace);

        model.addAttribute("tablespace",tablespace);

        List session = Dbconnect.query(connect,Constant.session);

        model.addAttribute("sessiondb",session);

        List avticeSession = Dbconnect.query(connect,Constant.avtiveSession);

        model.addAttribute("avticese",avticeSession);

        List sessionGroupUser = Dbconnect.query(connect,Constant.sessionGroupUser);

        model.addAttribute("sessGrop",sessionGroupUser);

        List  memory= Dbconnect.query(connect,Constant.memory);

       model.addAttribute("memory",memory);

        List sga = Dbconnect.query(connect,Constant.sga);

        model.addAttribute("sga",sga);

        List pga = Dbconnect.query(connect,Constant.pga);

        model.addAttribute("pga",pga);

        List dataName = Dbconnect.query(connect,Constant.databaseName);

        model.addAttribute("dataName",dataName);

        List instance=Dbconnect.query(connect,Constant.instanceName);

        model.addAttribute("instance",instance);

        List version = Dbconnect.query(connect,Constant.version);

        model.addAttribute("version",version);

        List psu = Dbconnect.query(connect,Constant.psu);

        model.addAttribute("psu",psu);

        List dbid = Dbconnect.query(connect,Constant.dbid);

        model.addAttribute("dbid",dbid);

        List contronfile = Dbconnect.query(connect,Constant.controlfile);

        model.addAttribute("controlfile",contronfile);

        List resourceLimit = Dbconnect.query(connect,Constant.resourceLimit);

        model.addAttribute("resourceLimit",resourceLimit);

        List chart = Dbconnect.query(connect,Constant.cahrt);

        model.addAttribute("chart",chart);

        List nchart = Dbconnect.query(connect,Constant.ncahrt);

        model.addAttribute("nchart",nchart);

        List logSize = Dbconnect.query(connect,Constant.logSize);

        model.addAttribute("logSize",logSize);

        List logCount = Dbconnect.query(connect,Constant.logCount);

        model.addAttribute("logCount",logCount);

        List logMember = Dbconnect.query(connect,Constant.logMember);

        model.addAttribute("logMember",logMember);

        List logMode = Dbconnect.query(connect,Constant.logMode);

        model.addAttribute("logMode",logMode);

        List archFile = Dbconnect.query(connect,Constant.archFile);

        model.addAttribute("archFile",archFile);

       // List envnt = Dbconnect.query(connect,Constant.envnt);

        //model.addAttribute("envnt",envnt);

        List isBadBlock = Dbconnect.query(connect,Constant.isBadBlock);

        model.addAttribute("isBadBlock",isBadBlock);

        List instancestat= Dbconnect.query(connect,Constant.instanceStatus);

        model.addAttribute("instat",instance);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("数据库链接失败");
        }finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "detail";
    }
    @GetMapping(value = "/sys/{id}")
    public String CatSys(@PathVariable int id,Model model){

        Shell shell=new Shell(Dbconnect.getDataSource(id));
        String mem=shell.execute(Constant.catMemory);

        model.addAttribute("sysMem",mem);

        String cpu=shell.execute(Constant.catCpu);

        model.addAttribute("cpu",cpu);

        String disk = shell.execute(Constant.disk);
        List<String> str=Arrays.asList(disk.split("\n"));
        List<String[]> dis=new ArrayList<String[]>();
        for (int i = 0;i<str.size();i++) {
            dis.add(str.get(i).split(","));
        }
        model.addAttribute("disk",dis);

        String lsnrstat=shell.execute(Constant.lsnrctl);

        model.addAttribute("lsnrstat",lsnrstat);

        return "sysDetail";
    }

}
