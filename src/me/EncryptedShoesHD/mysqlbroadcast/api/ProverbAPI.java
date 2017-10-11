/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.EncryptedShoesHD.mysqlbroadcast.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.EncryptedShoesHD.mysqlbroadcast.MySQLBroadcast;

/**
 *
 * @author user
 */
public class ProverbAPI {
    
    MySQLBroadcast core;
    public ProverbAPI(MySQLBroadcast core) {
        this.core = core;
    }
    
    public boolean existsProverb(int id) {
        return getProverb(id) != null;
    }
    
    public String getProverb(int ID) {
        try{
            ResultSet rs;
            rs = core.getMySQL().getResult("SELECT Pregovor FROM pregovori WHERE ID='" + ID + "';");
            if(rs.next() && rs.getString("Pregovor") != null) {
                return rs.getString("Pregovor");
            }
            return null;
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void addProverb(String proverb) {
        core.getMySQL().update("INSERT INTO pregovori(ID, Pregovor) VALUES ('0', '" + proverb + "');");
    }
    
    public void removeProverb(int id) {
        core.getMySQL().update("DELETE FROM pregovori WHERE ID='" + id + "';");
    }
    
}
