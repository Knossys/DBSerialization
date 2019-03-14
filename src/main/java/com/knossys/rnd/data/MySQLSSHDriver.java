package com.knossys.rnd.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;

/**
 * @author vvelsen
 */
public class MySQLSSHDriver {

	private static Logger M_log = Logger.getLogger(MySQLSSHDriver.class.getName());

	protected String tunneledHost = "";
	protected String SSHUser = "sshuser"; // SSH username
	protected String SSHPassword = "sshpassword"; // SSH password
	protected int lport = 5656; // Local SSH tunnel port
	//protected int rport = 3306; // MySQL port
	protected Session session = null; // SSH session

	protected String useSSL = "false";

	protected String driverName = "com.mysql.cj.jdbc.Driver";

	protected Connection conn = null;

	protected String dbHost = ""; // configured for docker-compose
	protected int dbPort = 3306; // configured for docker-compose
	protected String dbName = "";
	protected String dbTable = "";
	protected String dbUsername = "";
	protected String dbPassword = "";

	protected String url = "jdbc:mysql://localhost:" + lport + "/db";

	protected java.util.Properties config = new java.util.Properties();

	private Boolean dbTunnel = false;

	/**
	 * @return
	 */
	public int getPort() {
		return (dbPort);
	}
	
	/**
	 * @param aPort
	 */
	public void setPort (int aPort) {
		dbPort=aPort;
	}

	/**
	 * 
	 */
	public String getHost() {
		return (dbHost);
	}

	/**
	 * 
	 */
	public String getUsername() {
		return (dbUsername);
	}

	/**
	 * 
	 */
	public String getPassword() {
		return (dbPassword);
	}

	/**
	 * @return
	 */
	public Boolean getUseTunnel() {
		return dbTunnel;
	}

	/**
	 * @param useTunnel
	 */
	public void setUseTunnel(Boolean useTunnel) {
		this.dbTunnel = useTunnel;
	}

	/**
	 * 
	 */
	public void init() {
		M_log.info("init ()");
		// Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
		config.put("StrictHostKeyChecking", "no");
	}

	/**
	 * The software listening on port 11211 doesn't support IPv6. Since localhost is an entry in the hosts file that tries an 
	 * IPv6 address first, you only fail back to IPv4 after IPv6 fails. You can solve the issue by either getting the 
	 * software updated (or properly configured to listen on IPv6 if it's an option in the config file) or changing your 
	 * command to telnet 127.0.0.1 instead of telnet localhost
	 * 
	 * @return
	 */
	public Boolean openTunnel() {
		M_log.info("openTunnel ()");
		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		if (dbTunnel == true) {
			tunneledHost = dbHost;
			dbHost = "127.0.0.1";
			dbPort = lport;

			M_log.info("Setting up tunnel to: " + tunneledHost);
		} else {
			M_log.info("This database connection does not require tunneling");
			return (true);
		}

		JSch jsch = new JSch();

		M_log.info("Creating SSH session ...");

		try {
			session = jsch.getSession(SSHUser, tunneledHost, 22);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			M_log.info("Error: unable to setup tunnel session to " + tunneledHost + ": " + e.getMessage());
			return (false);
		}
		session.setPassword(SSHPassword);
		session.setConfig(config);

		M_log.info("Making SSH connection ...");

		try {
			session.connect();
		} catch (JSchException e) {
			//e.printStackTrace();
			M_log.info("Error: unable to setup tunnel connection to " + tunneledHost + ": " + e.getMessage());
			return (false);
		}

		M_log.info("Tunnel connected, ready for db connection");

		try {
			dbPort = session.setPortForwardingL(lport, "127.0.0.1", dbPort);
		} catch (JSchException e) {
			e.printStackTrace();
			return (false);
		}

		M_log.info(dbHost + ":" + dbPort + " -> " + tunneledHost + ":" + dbPort);
		M_log.info("Port Forwarded");

		url = "jdbc:mysql://" + dbHost + ":" + lport + "/" + dbName;

		M_log.info("Setting jdbc url to: " + url);

	  //useSSL="true";

		return (true);
	}

	/**
	 * @return
	 */
	public Boolean closeTunnel() {
		M_log.info("closeTunnel ()");

		if (session != null && session.isConnected()) {
			M_log.info("Closing SSH Connection");
			session.disconnect();
		}

		return (true);
	}

	/**
	 * @return
	 */
	public Boolean connect() {
		M_log.info("connect ()");

		openTunnel();

		try {
			Class.forName(driverName).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (false);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (false);
		}

		try {
			conn = DriverManager.getConnection(url, dbUsername, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (false);
		}

		M_log.info("Database connection established");

		// closeTunnel ();

		return (true);
	}

	/**
	 * 
	 */
	public Boolean close() {
		M_log.info("close ()");

		if (dbTunnel == true) {
			closeTunnel();
		}
		return (true);
	}
}
