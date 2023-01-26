package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnprotectionCommand implements CommandExecutor, TabCompleter {

    public static int spawnprotection = Rexplugin.config.getInt("SpawnprotectionInTicks");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        if (args.length == 0) { p.sendMessage(Rexplugin.prefix + ChatColor.RED + "Du musst get/set und gegebenenfalls einen Wert in Ticks angeben!"); return false;}

        switch (args[0]) {
            case "get" -> { p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Die Spawnprotection in Ticks ist " + ChatColor.GOLD + spawnprotection); Communication.playPositiveSound(p);}
            case "set" -> {
                if (args.length == 1) {p.sendMessage(Rexplugin.prefix + ChatColor.RED + "Du musst get/set und gegebenenfalls einen Wert in Ticks angeben!"); return false;}
                spawnprotection = Integer.parseInt(args[1]);
                p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Die Spawnprotection in Ticks ist nun " + ChatColor.GOLD + args[1]);
                Communication.playPositiveSound(p);
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            list.add("get");
            list.add("set");
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1];
        for (String s : list) {
            if (s.startsWith(currentarg)) {
                completerList.add(s);
            }
        }

        return completerList;
    }
}
