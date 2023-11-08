package org.oceangrad.nuclearweapon.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oceangrad.nuclearweapon.domain.Item_stacks.HazmatStack;

public class GiveHazmatCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Cannot execute command from console");
            return true;
        }

        Player player = (Player) commandSender;

        player.getInventory().addItem(HazmatStack.getHazmatStack());

        return false;
    }
}
