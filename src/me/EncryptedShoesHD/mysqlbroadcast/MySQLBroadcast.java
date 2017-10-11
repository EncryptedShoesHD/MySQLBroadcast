/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.EncryptedShoesHD.mysqlbroadcast;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import me.EncryptedShoesHD.mysqlbroadcast.api.FileManager;
import me.EncryptedShoesHD.mysqlbroadcast.api.MySQL;
import me.EncryptedShoesHD.mysqlbroadcast.api.NumericAPI;
import me.EncryptedShoesHD.mysqlbroadcast.api.ProverbAPI;
import me.EncryptedShoesHD.mysqlbroadcast.command.Pregovor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
/**
 *
 * @author user
 */
public class MySQLBroadcast extends JavaPlugin {
    
    private static MySQLBroadcast instance;
    private FileManager fm;
    private MySQL mysql;
    private ProverbAPI proverb;
    private NumericAPI numeric;
    private ArrayList<Integer> proverbs;
    int i = 0, sendTiming = 0;
    
    @Override
    public void onEnable() {
        instance = this;
        proverbs = new ArrayList<>();
        fm = new FileManager(this);
        mysql = new MySQL(this);
        proverb = new ProverbAPI(this);
        numeric = new NumericAPI(this);
        File config = fm.getFile("config.yml", "plugins/MySQLBroadcast");
        if(!config.exists()) createConfigFile(config.getName(), "plugins/MySQLBroadcast");
        FileConfiguration cfg = fm.getConfiguration(config);
        sendTiming = (int) cfg.get("SendTiming");
        mysql.connect();
        if(mysql.isConnected()) {
            mysql.update("CREATE TABLE IF NOT EXISTS pregovori(ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT, Pregovor VARCHAR(256));");
            try {
                fetchAllProverbs("pregovori");
            } catch (SQLException ex) {
                getServer().getConsoleSender().sendMessage("§cCould not fetch any proverbs!");
                ex.printStackTrace();
            }
        }
        getCommand("pregovor").setExecutor(new Pregovor(this));
        if(!proverbs.isEmpty()) {
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
                @Override
                public void run() {
                    if(i <= proverbs.size() - 1) {
                        if(getProverbAPI().existsProverb(proverbs.get(i))) {
                            for(Player p : getServer().getOnlinePlayers()) {
                                p.sendMessage("§3§lPregovor pravi§f: §a" + getProverbAPI().getProverb(proverbs.get(i)));
                            }
                            i++;
                        }
                    }else{
                        i = 0;
                    }
                }
                
            }, 20L, sendTiming*20L);
        }
    }
    
    @Override
    public void onDisable() {
        
    }
    
    private void createConfigFile(String name, String path) {
    	getFileManager().createNewFile(name, path);
        File f = getFileManager().getFile(name, path);
        FileConfiguration cfg = getFileManager().getConfiguration(f);
        cfg.options().copyDefaults(true);
        cfg.addDefault("username", "sunka");
        cfg.addDefault("password", "vojko");
        cfg.addDefault("database", "slemi");
        cfg.addDefault("host", "snajpa");
        cfg.addDefault("port", "3306");
        cfg.addDefault("SendTiming", 300);
        try{
            cfg.save(f);
        }catch(IOException ex) {
            getServer().getConsoleSender().sendMessage("§cCould not save file 'config.yml'.");
        }
    }
    
    private void fetchAllProverbs(String table) throws SQLException {
        ResultSet rs = getMySQL().getResult("SELECT * FROM " + table + ";");
        while (rs.next()) {
            proverbs.add(rs.getInt("ID"));
        }
        rs.close();
    }
    
    public static MySQLBroadcast getInstance() {
        return instance;
    }
    
    public FileManager getFileManager() {
        return fm;
    }
    
    public MySQL getMySQL() {
        return mysql;
    }
    
    public ProverbAPI getProverbAPI() {
        return proverb;
    }
    
    public NumericAPI getNumericAPI() {
        return numeric;
    }
    
}
