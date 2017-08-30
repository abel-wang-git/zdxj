package com.zdxj;

import com.zdxj.croe.Dbconnect;
import com.zdxj.po.Datasoruce;
import org.junit.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public class test {
    @Test
    public void test(){
        Datasoruce d= new Datasoruce();
        d.setSid("orcl");
        d.setIP("192.168.88.33");
        d.setProt("1521");
        d.setUserName("system");
        d.setPasswd("123456");
        Connection c=Dbconnect.dbConnect(d);

        try {
            Statement s = c.createStatement();
            ResultSet res =  s.executeQuery(
                    "\n" +
                            "select name,total,round(total-free,2) used, round(free,2) free,round((total-free)/total*100,2) pctused from (select 'SGA' name,(select sum(value/1024/1024) from v$sga) total,(select sum(bytes/1024/1024) from v$sgastat where name='free memory')free from dual)union select name,total,round(used,2)used,round(total-used,2)free,round(used/total*100,2)pctused from (select 'PGA' name,(select value/1024/1024 total from v$pgastat where name='aggregate PGA target parameter')total,(select value/1024/1024 used from v$pgastat where name='total PGA allocated')used from dual)");
            ResultSetMetaData md = res.getMetaData();
            int num = md.getColumnCount();
            while (res.next()) {
                Map mapOfColValues = new HashMap(num);
                for (int i = 1; i <= num; i++) {
                    System.out.print(md.getColumnName(i)+res.getObject(i));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
