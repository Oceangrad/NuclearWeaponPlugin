package org.oceangrad.nuclearweapon.executors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.oceangrad.nuclearweapon.NuclearWeapon;

public class ClearSideEffectsCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 0){
                player.sendMessage(NuclearWeapon.clearPlayerSideEffect(player) ? "clear %s's side-effect" : "no side-effects found");
            }
            else{
                try{
                    player.sendMessage(NuclearWeapon.clearPlayerSideEffect(Bukkit.getPlayer(args[0])) ? "clear %s's side-effect" : "no side-effects found");
                }
                catch (NullPointerException e){
                    player.sendMessage("No such player found");
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
                sender.sendMessage(NuclearWeapon.clearPlayerSideEffect(Bukkit.getPlayer(args[0])) ? "clear %s's side-effect" : "no side-effects found");
            }
            catch (NullPointerException e){
                sender.sendMessage("No such player found");
                return true;
            }
        }

        return true;
    }
}
