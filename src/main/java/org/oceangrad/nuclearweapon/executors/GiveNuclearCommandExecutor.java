package org.oceangrad.nuclearweapon.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.oceangrad.nuclearweapon.domain.Item_stacks.NuclearItemStack;

public class GiveNuclearCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        boolean isPlayer = sender instanceof Player;

        if(!isPlayer){
            sender.sendMessage("Command requires sender to be a player");
            return true;
        }

        player = (Player) sender;

        ItemStack nuclear = NuclearItemStack.getNuclearStack(player.getLocation().toVector(), player.getLocation().toVector());

        player.getInventory().addItem(nuclear);
        return true;
    }
}
