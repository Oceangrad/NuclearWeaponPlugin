package org.oceangrad.nuclearweapon.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oceangrad.nuclearweapon.NuclearWeapon;

import java.util.Objects;

public class CureWorldCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0 && Objects.equals(args[0], "all")){
            NuclearWeapon.cureAllWorlds();
            sender.sendMessage("Cured ALL worlds");
            return true;
        }

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 0){
                NuclearWeapon.cureWorld(player.getWorld());
                player.sendMessage("Cured current world");
            }
            else{
                try{
                    NuclearWeapon.cureWorld(Bukkit.getWorld(args[0]));
                    player.sendMessage("Cured %s");
                }
                catch (NullPointerException e){
                    player.sendMessage("No such world found");
                    return true;
                }
            }

            return true;
        }

        if(args.length == 0){
            sender.sendMessage("Command can be executed only by player");
        }
        else{
            try{
                NuclearWeapon.cureWorld(Bukkit.getWorld(args[0]));
                sender.sendMessage("Cured %s");
            }
            catch (NullPointerException e){
                sender.sendMessage("No such world found");
                return true;
            }
        }

        return true;
    }
}
