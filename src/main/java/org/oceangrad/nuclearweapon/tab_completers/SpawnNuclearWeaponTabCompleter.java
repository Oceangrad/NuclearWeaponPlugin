package org.oceangrad.nuclearweapon.tab_completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class SpawnNuclearWeaponTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))
            return null;

        Player player = (Player) commandSender;

        return List.of(String.format("%s %s %s", (float) player.getLocation().getX(), (float) player.getLocation().getY(), (float) player.getLocation().getZ()));
    }
}
