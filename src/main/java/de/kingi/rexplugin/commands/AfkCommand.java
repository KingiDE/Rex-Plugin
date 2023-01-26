package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AfkCommand implements CommandExecutor {

    private List<String> afkList = Rexplugin.config.getStringList("Afk");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if (afkList.contains(p.getUniqueId().toString())) {
            afkList.remove(p.getUniqueId().toString());
            Rexplugin.config.set("Afk", afkList);
            p.sendMessage(Rexplugin.prefix + ChatColor. GREEN + "Du bist nun nicht mehr Afk");
            Communication.playPositiveSound(p);
        }
        else {
            afkList.add(p.getUniqueId().toString());
            Rexplugin.config.set("Afk", afkList);
            p.sendMessage(Rexplugin.prefix + ChatColor. GREEN + "Du bist nun Afk");
            Communication.playPositiveSound(p);
        }

        return true;
    }
}
