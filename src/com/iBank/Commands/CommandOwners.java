package com.iBank.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.iBank.system.Bank;
import com.iBank.system.BankAccount;
import com.iBank.system.Configuration;
import com.iBank.system.Handler;
import com.iBank.system.MessageManager;

/**
 *  /bank owners <ACCOUNT> - Show the owners of the account
 *  /bank owners <ACCOUNT> a(dd) <NAME> - Add owner <NAME> to account
 *  /bank owners <ACCOUNT> d(el) <NAME> - Remove owner <NAME> (as owner) from account
 * @author steffengy
 *
 */
public class CommandOwners extends Handler {
	public void handle(CommandSender sender, String[] arguments) { 
		boolean console = false;
		if(!(sender instanceof Player)) {
			console = true;
		}
		if(arguments.length == 0) {
			MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorWrongArguments.toString());
			return;
		}
		
		if(Bank.hasAccount(arguments[0])) {
		if(arguments.length == 1) {
				MessageManager.send(sender, "&w&"+Configuration.StringEntry.GeneralInfo.toString().replace("$type$","Account").replace("$name$", arguments[0]));
				String owners = Bank.getAccount(arguments[0]).getOwners().toString();
				MessageManager.send(sender, "&w&"+Configuration.StringEntry.GeneralOwners.toString()+" : "+owners);
			}else if(arguments.length == 3) {
				BankAccount tmp = Bank.getAccount(arguments[0]);
				
				if(!console && !tmp.isOwner(((Player)sender).getName())) {
					MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorNoAccess.getValue());
					return;
				}
				
				if(arguments[1].equalsIgnoreCase("a") || arguments[1].equalsIgnoreCase("add")) {
						if(!tmp.isOwner(arguments[2])) { 
							if(Bukkit.getOfflinePlayer(arguments[2]) != null) {
								tmp.addOwner(arguments[2]);
							}else{
								MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorNotExist.toString().replace("$name$", arguments[2]));
							}
						} else {
							MessageManager.send(sender, "&r&" + Configuration.StringEntry.ErrorAlready.getValue().replace("$name$", arguments[0]).replace("$type$", Configuration.StringEntry.GeneralOwners.toString()));
							return;
						}
							MessageManager.send(sender, "&g&"+Configuration.StringEntry.SuccessMod.getValue().replace("$name$", arguments[0]));
					
				}else if(arguments[1].equalsIgnoreCase("d") || arguments[1].equalsIgnoreCase("del")) {
					if(tmp.isOwner(arguments[2])) {
						if(Bukkit.getOfflinePlayer(arguments[2]) != null) {
							tmp.removeOwner(arguments[2]);
						}else{
							MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorNotExist.toString().replace("$name$", arguments[2]));
						}
					} else {
						MessageManager.send(sender, "&r&" + Configuration.StringEntry.ErrorNot.getValue().replace("$name$", arguments[2]).replace("$type$", Configuration.StringEntry.GeneralOwners.toString()));
						return;
					}
					MessageManager.send(sender, "&g&"+Configuration.StringEntry.SuccessMod.getValue().replace("$name$", arguments[0]));
				}else{
					MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorWrongArguments.toString());
				}
			}else{
				MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorWrongArguments.toString());
			}
		}else{
			MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorNotExist.toString().replace("$name$", arguments[0]));
		}
	}
}
