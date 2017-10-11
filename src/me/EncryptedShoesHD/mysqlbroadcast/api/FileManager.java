package me.EncryptedShoesHD.mysqlbroadcast.api;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.EncryptedShoesHD.mysqlbroadcast.MySQLBroadcast;

public class FileManager {
	
    MySQLBroadcast ma;
    public FileManager(MySQLBroadcast ma) {
	this.ma = ma;
    }
	
    public File createNewFile(String fileName, String path) {
        File filePath = new File(path);
        if(!filePath.exists()) filePath.mkdirs();
        File file = new File(filePath, fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ma.getServer().getConsoleSender().sendMessage("§c" + e.getMessage());
            }
        }
        return file;
    }

    public File getFile(String fileName, String path) {
        File file = new File(path, fileName);
        return file;
    }

    public void deleteFile(String filename, String path) {
        File file = new File(path, filename);
        file.delete();
    }

    public FileConfiguration getConfiguration(File f) {
        return YamlConfiguration.loadConfiguration(f);
    }
}