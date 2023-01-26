package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.guis.SettingsGui;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        SettingsGui.createSettingsGui(p);
        Communication.playPositiveSound(p);

        return true;
    }
}
