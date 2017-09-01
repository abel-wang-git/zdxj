package com.zdxj.croe;

import com.jcraft.jsch.*;
import com.zdxj.po.Datasoruce;
import com.zdxj.po.SysUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class Shell {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ipAddress;

    private String username;

    private String password;

    public static final int DEFAULT_SSH_PORT = 22;

    private Vector<String> stdout;

    public Shell(final String ipAddress, final String username, final String password) {
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
        stdout = new Vector<String>();
    }
    public Shell(Datasoruce datasoruce){
        this.ipAddress=datasoruce.getIP();
        this.username=datasoruce.getSysUser();
        this.password=datasoruce.getPasswd();
        stdout=new Vector<String>();
    }

    public int execute(final String command) {
        int returnCode = 0;
        JSch jsch = new JSch();
        SysUserInfo userInfo = new SysUserInfo();

        try {
            // Create and connect session.
            Session session = jsch.getSession(username, ipAddress, DEFAULT_SSH_PORT);
            session.setPassword(password);
            session.setUserInfo(userInfo);
            session.connect();

            // Create and connect channel.
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader(channel
                    .getInputStream()));

            channel.connect();
            logger.info(command);

            // Get the output of remote command.
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
            input.close();

            // Get the return code only after the channel is closed.
            if (channel.isClosed()) {
                returnCode = channel.getExitStatus();
            }

            // Disconnect the channel and session.
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCode;
    }

    public Vector<String> getStandardOutput() {
        return stdout;
    }

    public String exec(String command){
        String retrun = "";
        this.execute(command);
        for (String str : this.stdout) {
            retrun+=str;
        }
        return retrun;
    }

    public static void main(final String [] args) {
        Shell sshExecutor = new Shell("192.168.1.110", "root", "123456");
        String s=sshExecutor.exec("free | grep Mem |awk  '{print $3\",\"$2}'");
        System.out.println(s);

    }
}
