package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.guis.ChallengeGui;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChallengeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        ChallengeGui.createChallengeGui(p);
        Communication.playPositiveSound(p);

        return true;
    }
}
