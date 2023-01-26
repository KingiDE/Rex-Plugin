package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PrefixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if (args.length == 0) { p.sendMessage(Rexplugin.prefix + ChatColor.RED + "Du musst noch einen Prefix angeben!"); return false;}

        // wenn ein Argument angegeben wurde:

        String prefix;

        // bei "RESET" wird der Prefix zurückgesetzt
        if (args[0].equals("RESET")) {
            prefix = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "GHG" + ChatColor.GRAY + "] ";
            Rexplugin.prefix = prefix;
            p.sendMessage(prefix + ChatColor.GREEN + "Der Prefix wurde erfolgreich zurückgesetzt");
            Communication.playPositiveSound(p);
            return true;
        }

        // sonst wird das Eingegebene übernommen
        prefix = args[0] + " "; // Leerstelle für Abstand
        prefix = ChatColor.translateAlternateColorCodes('#', prefix);
        Rexplugin.prefix = prefix;

        p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Du hast den Prefix auf " + prefix + ChatColor.GREEN + "geändert");
        Communication.playPositiveSound(p);

        return true;
    }

}
