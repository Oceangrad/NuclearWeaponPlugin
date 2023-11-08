package org.oceangrad.nuclearweapon.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.oceangrad.nuclearweapon.NuclearWeapon;

public class SpawnNuclearWeaponCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player;
        boolean isPlayer = commandSender instanceof Player;

        if(!isPlayer)
            return false;

        float x = Float.parseFloat(strings[0]);
        float y = Float.parseFloat(strings[1]);
        float z = Float.parseFloat(strings[2]);

        player = (Player) commandSender;

        NuclearWeapon.launchNuclear(player.getWorld(), player.getLocation().toVector(), new Vector(x, y, z));

        return true;
    }
}
