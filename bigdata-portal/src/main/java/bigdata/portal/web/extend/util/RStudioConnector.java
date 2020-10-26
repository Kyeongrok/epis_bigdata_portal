package bigdata.portal.web.extend.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RStudioConnector extends ExternalMakeSession {

	private Session session = null;
	private Channel channel = null;
	private int port = 0;

	private String host = null;
	private String userName = null;
	private String userPassword = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RStudioConnector.class);

	public RStudioConnector(String host, String userName, String userPassword, int port) {
		this.host = host;
		this.userName = userName;
		this.userPassword = userPassword;
		this.port = port;
	}

	public RStudioConnector(String destURL) {
		super.setDestUrl(destURL);
	}

	public String getRStudioPublicKey() {

		HttpURLConnection connection = this.connect("GET");
		StringBuilder sb = new StringBuilder();

		try {

			InputStream in = connection.getInputStream();
			InputStreamReader isw = new InputStreamReader(in);

			int data = isw.read();
			while (data != -1) {
				char current = (char) data;
				data = isw.read();
				sb.append(current);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return sb.toString();
	}

	private void sshConfig() throws JSchException {
		
		System.out.println("======== ssh connect start =========");
		JSch jsch = new JSch();
		session = jsch.getSession(userName, host, port);
		session.setPassword(userPassword);

		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		
	}

	public void makeAccount(String userId) throws JSchException, IOException {
		sshConfig();
		session.connect();

		channel = session.openChannel("exec");
		ChannelExec channelExec = (ChannelExec) channel;
		channelExec.setPty(true);
		
		System.out.println("====== make account ======");
		
		// 실서버
		//channelExec.setCommand(String.format("ssh -p %d bigdata-datanode003 useradd %s\n", this.port, userId));
		
		// 테스트 서버
		channelExec.setCommand(String.format("ssh -p %d datanode003 useradd %s\n", this.port, userId));

		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();

		channelExec.setErrStream(System.err);
		channelExec.connect();
		
		System.out.println("make Account");
		
		String[] commands = {"yes", userPassword+"\n"};
		
		write(commands, out);
		
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				System.out.println(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				System.out.println("exit-status: %s" + channel.getExitStatus());
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {

			}
		}

		channel.disconnect();
		session.disconnect();
	}

	public void makeRStudioAccount(String userId, String password) throws JSchException, IOException {

	}
	

	public void changeRStudioPassword(String userId, String password) throws JSchException, IOException {
		sshConfig();
		session.connect();

		channel = session.openChannel("exec");
		ChannelExec channelExec = (ChannelExec) channel;
		channelExec.setPty(true);
		
		System.out.println("====== make account ======");
		
		// 실서버
		//channelExec.setCommand(String.format("ssh -p %d bigdata-datanode003 passwd %s\n", this.port, userId));
		
		// 테스트 서버
		channelExec.setCommand(String.format("ssh -p %d datanode003 passwd %s\n", this.port, userId));

		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();
		
		// System.out.println("password");
		
		channelExec.setErrStream(System.err);
		channelExec.connect();
		
		// 실서버
		//String[] commands = {"yes", userPassword+"\n", String.format("%s\n", password), String.format("%s\n", password)};
		
		// 테스트서버
		String[] commands = {String.format("%s\n", password), String.format("%s\n", password)};
		
		write(commands, out);

		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
			}
			if (channel.isClosed()) {
				System.out.println(String.format("exit-status: %s", channel.getExitStatus()));
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {

			}
		}

		channel.disconnect();
		session.disconnect();

	}
	
	public void write (String[] commands, OutputStream out) throws IOException {
		
		for(int i=0; i<commands.length; i++) {
			out.write(commands[i].getBytes());
			out.flush();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void removeRStudioAccount(String userId) throws JSchException, IOException {
		sshConfig();
		session.connect();

		channel = session.openChannel("exec");
		ChannelExec channelExec = (ChannelExec) channel;
		channelExec.setPty(true);

		
		// 실서버
		//channelExec.setCommand(String.format("ssh -p %d bigdata-datanode003 userdel -r %s\n", this.port, userId));
		
		// 테스트서버
		channelExec.setCommand(String.format("ssh -p %d datanode003 userdel -r %s\n", this.port, userId));

		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();
		
		channelExec.setErrStream(System.err);
		channelExec.connect();		
		
		
		String[] commands = {"yes", userPassword+"\n"};
		
		write(commands, out);		
		
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
			}
			if (channel.isClosed()) {
				System.out.println(String.format("exit-status: %s", channel.getExitStatus()));
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {

			}
		}
		
		channel.disconnect();
		session.disconnect();
		
	}
}

