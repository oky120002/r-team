/**
 * 
 */
package com.r.core.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

/**
 * http://localhost:8082
 * 
 * @author oky
 */
public class StartH2Service {
	private static Server tcpServer;
	private String port = "9094";
	private String dbDir = "D:/rainteam/sts-3.5.1.RELEASE/h2db/loverlover";
	private String user = "heyu";
	private String password = "heyulovelian1990";

	public void startServer() {
		try {
			System.out.println("正在启动h2..tcp..");
			tcpServer = Server.createTcpServer(new String[] { "-tcpPort", port }).start();
			System.out.println("正在启动h2..web..");
			Server.createWebServer(new String[] { "-webAllowOthers" }).start();
		} catch (SQLException e) {
			System.out.println("启动h2出错：" + e.toString());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void stopServer() {
		if (tcpServer != null) {
			System.out.println("正在关闭h2...");
			tcpServer.stop();
			System.out.println("关闭成功.");
		}
	}

	public void useH2() {
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir, user, password);
			Statement stat = conn.createStatement();
			// insert data
			stat.execute("CREATE TABLE TEST(NAME VARCHAR)");
			stat.execute("INSERT INTO TEST VALUES('Hello World')");

			// use data
			ResultSet result = stat.executeQuery("select name from test ");
			int i = 1;
			while (result.next()) {
				System.out.println(i++ + ":" + result.getString("name"));
			}
			result.close();
			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		StartH2Service h2 = new StartH2Service();
		h2.startServer();
		// h2.useH2();
		// h2.stopServer();
		// System.out.println("==END==");
	}
}
