/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.EncryptedShoesHD.mysqlbroadcast.api;

import me.EncryptedShoesHD.mysqlbroadcast.MySQLBroadcast;

/**
 *
 * @author user
 */
public class NumericAPI {
    
    MySQLBroadcast core;
    public NumericAPI(MySQLBroadcast core) {
        this.core = core;
    }
	
    public boolean isNumeric(String number) {
        try{
            Integer.parseInt(number);
        }catch(NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
}
