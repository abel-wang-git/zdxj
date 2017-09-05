package com.zdxj.croe;


import com.alibaba.fastjson.JSON;
import com.zdxj.po.Datasoruce;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wanghuiwen on 17-2-12.
 * 服务启动执行
 */
@Component
//@Order(value=2) 多个CommandLineRunner时 控制顺序
public class MyStartupRunner implements CommandLineRunner {
    public static  final List<Datasoruce> source = new ArrayList<Datasoruce>();
    public static  String conf=null;

    public void run(String... strings) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+System.getProperty("file.separator")+"datasource.conf"));
        String s;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            String[] dbs=s.split(",");

            Datasoruce d= new Datasoruce();
            d.setName(dbs[0]);
            d.setIP(dbs[1]);
            d.setProt(dbs[2]);
            d.setSid(dbs[3]);
            d.setUserName(dbs[4]);
            d.setPasswd(dbs[5]);
            d.setId((Integer.parseInt(dbs[6])));
            d.setSysUser(dbs[7]);
            d.setSysPwd(dbs[8]);
            source.add(d);
        }
        System.out.print(JSON.toJSON(source));
    }
}
