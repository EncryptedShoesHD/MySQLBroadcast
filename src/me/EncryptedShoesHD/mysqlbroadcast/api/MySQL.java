package me.EncryptedShoesHD.mysqlbroadcast.api;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import me.EncryptedShoesHD.mysqlbroadcast.MySQLBroadcast;

public class MySQL {

    private static MySQLBroadcast plugin;
    private static final String pr = "§3MySQL§8> ";
    private static String HOST = "";
    private static String DATABASE ="";
    private static String USER = "";
    private static String PASSWORD = "";
    private static String PORT = "";

    private static java.sql.Connection con;
    private boolean isConnected;
    
    public MySQL(MySQLBroadcast jp) {
        plugin = jp;
    }

    public void connect() {
        FileConfiguration cfg = plugin.getFileManager().getConfiguration(plugin.getFileManager().getFile("config.yml", "plugins/MySQLBroadcast"));
        HOST = cfg.getString("host");
        DATABASE = cfg.getString("database");
        USER = cfg.getString("username");
        PASSWORD = cfg.getString("password");
        PORT = cfg.getString("port");
        if(!isConnected) {
            try{
        	con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
        	plugin.getServer().getConsoleSender().sendMessage(pr + "§aSuccessfully connected to MySQL database!");
            }catch(SQLException ex) {
        	plugin.getServer().getConsoleSender().sendMessage(pr + "§cCould not connect to MySQL database, please check your MySQL settings!");
            }
        }
    }

    public void close() {
    	if(isConnected()) {
            try{
                con.close();
    		con = null;
    		plugin.getServer().getConsoleSender().sendMessage(pr + "§aSuccessfully closed MySQL connection!");
            }catch(SQLException ex) {
    		ex.printStackTrace();
            }
    	}
    }
    
    public boolean isConnected() {
    	return con != null;
    }

    public void update(String qry) {
    	if(isConnected()) {
            try{
    		con.createStatement().executeUpdate(qry);
            }catch(SQLException ex) {
    		ex.printStackTrace();
            }
    	}
    }

    public ResultSet getResult(String qry) {
    	if(isConnected()){
            try{
    		return con.createStatement().executeQuery(qry);
            }catch(SQLException ex) {
    		ex.printStackTrace();
            }
    	}
    	return null;
    }
    
}
