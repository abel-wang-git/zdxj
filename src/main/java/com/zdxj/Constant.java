package com.zdxj;

public class Constant {

    public static final  String tablespace = "select c.tablespace_name tablespace_name, round(a.bytes/1048576,2) total_mb,round((a.bytes-b.bytes)/1048576,2) used_mb,round(b.bytes/1048576,2) free_mb, round(b.bytes/a.bytes * 100,2)||'%' free_pct from (select tablespace_name,sum(a.bytes) bytes from sys.dba_data_files a group by tablespace_name) a, (select a.tablespace_name, nvl(sum(b.bytes),0) bytes from sys.dba_data_files a, sys.dba_free_space b where a.tablespace_name = b.tablespace_name (+) and a.file_id = b.file_id (+) group by a.tablespace_name) b, sys.dba_tablespaces c where a.tablespace_name = b.tablespace_name(+) and a.tablespace_name = c.tablespace_name order by free_pct;";
    public static final  String datafile="select sum(bytes)/1024/1024/1024 GB from dba_data_files;";
    public static final String segment="select sum(bytes)/1024/1024/1024 GB from dba_data_files;";
}
