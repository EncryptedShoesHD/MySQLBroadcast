/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.EncryptedShoesHD.mysqlbroadcast.command;

import me.EncryptedShoesHD.mysqlbroadcast.MySQLBroadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Pregovor implements CommandExecutor {
    
    MySQLBroadcast core;
    public Pregovor(MySQLBroadcast core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            cs.sendMessage("§cWrong usage. Please use /pregovor <add/remove> <pregovor/id>. ID should only be used whenever removing a proverb.");
        }else{
            if(args[0].equalsIgnoreCase("add")) {
                if(cs.hasPermission("mysqlbroadcast.add")) {
                    String msg = "";
                    for(int i = 1; i < args.length; i++) {
                        msg += " " + args[i];
                    }
                    if(msg.length()>256) {
                        cs.sendMessage("§cProverbs should only be 256 characters long. Contact a developer to increase the limit.");
                    }else {
                        core.getProverbAPI().addProverb(msg);
                        cs.sendMessage("§aProverb has been added successfully.");
                    }
                }
            }else if(args[0].equalsIgnoreCase("remove")) {
                if(cs.hasPermission("mysqlbroadcast.remove")) {
                    if(args.length == 2) {
                        if(core.getNumericAPI().isNumeric(args[1])) {
                            int proverbID = Integer.parseInt(args[1]);
                            if(core.getProverbAPI().existsProverb(proverbID)) {
                                core.getProverbAPI().removeProverb(proverbID);
                                cs.sendMessage("§aProverb has been removed successfully.");
                            }else{
                                cs.sendMessage("§cSorry, but this proverb does not exist.");
                            }
                        }else{
                            cs.sendMessage("§cSorry, but your must enter a number as a proverb ID.");
                        }
                    }else{
                        cs.sendMessage("§cWrong usage. Please use /pregovor <add/remove> <pregovor/id>. ID should only be used whenever removing a proverb.");
                    }
                }
            }
        }
        return false;
    }
    
}
