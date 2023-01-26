package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand implements CommandExecutor {

    public static List<String> fly = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            p.sendMessage(Rexplugin.prefix + ChatColor.RED + "Du befindest dich bereits im Kreativmmodus und kannst schon fliegen.");
            Communication.playNegativSound(p);
            return true;
        }

        if (!(fly.contains(p.getUniqueId().toString()))) {
            fly.add(p.getUniqueId().toString());
            p.setAllowFlight(true);
            p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Du kannst jetzt fliegen");
            Communication.playPositiveSound(p);
        }
        else {
            fly.remove(p.getUniqueId().toString());
            p.setAllowFlight(false);
            p.sendMessage(Rexplugin.prefix + ChatColor.YELLOW + "Du kannst jetzt nicht mehr fliegen");
            Communication.playPositiveSound(p);
        }


        return true;
    }
}
