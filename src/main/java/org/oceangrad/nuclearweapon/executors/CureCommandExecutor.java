package org.oceangrad.nuclearweapon.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oceangrad.nuclearweapon.NuclearWeapon;

public class CureCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("can be run only by players");
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0){
            NuclearWeapon.cureZoneAtPlayer(player);
            return true;
        }

        switch (args[0]){
            case "all":
                NuclearWeapon.cureAllZonesAtPlayer(player);
                return true;
            case "this":
                NuclearWeapon.cureZoneAtPlayer(player);
                return true;
            default:
                player.sendMessage("Invalid argument");
                return true;
        }
    }
}
