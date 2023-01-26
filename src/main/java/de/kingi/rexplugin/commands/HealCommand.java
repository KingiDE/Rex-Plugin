package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import de.kingi.rexplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        p.setHealth(20);
        p.setFoodLevel(20);
        p.setSaturation(20);
        p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Du wurdest erfolgreich geheilt");
        Communication.playPositiveSound(p);

        return true;
    }
}
