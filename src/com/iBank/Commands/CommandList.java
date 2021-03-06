package com.iBank.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.iBank.iBank;
import com.iBank.system.Bank;
import com.iBank.system.Configuration;
import com.iBank.system.Handler;
import com.iBank.system.MessageManager;
import com.iBank.utils.StringUtils;

/**
 *  /bank LIST (NAME)
 *  If name is given, all accounts of NAME will be shown
 *  else all accounts will be shown
 * @author steffengy
 *
 */
public class CommandList extends Handler {
	
	public void handle(CommandSender sender, String[] arguments) {
		if((sender instanceof Player) && !iBank.canExecuteCommand(((Player)sender))) {
			MessageManager.send(sender, "&r&"+Configuration.StringEntry.ErrorNotRegion.toString());
			return;
		}
		// Show list of accounts#
		List<String> owner;
		List<String> user;
		if(arguments.length > 0 && arguments[0] != null) {
			owner = Bank.getAccountsByOwner(arguments[0]);
			user = Bank.getAccountsByUser(arguments[0]);
			MessageManager.send(sender, "&blue&Owner &y&User");
		}else{
			owner = Bank.getAccounts();
			user = new ArrayList<String>();
		}
		
		if(owner.size() == 0 && user.size() == 0) {
			MessageManager.send(sender, "&r&" + Configuration.StringEntry.GeneralNoAccounts.toString());
			return;
		}
		owner = owner == null ? new ArrayList<String>() : owner;
		user = user == null ? new ArrayList<String>() : user;
		MessageManager.send(sender, "&blue&"+StringUtils.join(owner, "&w&,&blue&"), "");
		MessageManager.send(sender, "&y&"+StringUtils.join(user, "&w&,&y&"), "");
	}
}
