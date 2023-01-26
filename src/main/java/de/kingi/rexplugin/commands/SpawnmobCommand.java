package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnmobCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        String usage = Rexplugin.prefix + ChatColor.WHITE + "Verwende " + ChatColor.YELLOW + "/spawnmob <Mob> <Anzahl>";

        if (args.length == 0) { p.sendMessage(usage); return false;}

        String mob = args[0];
        String countString = null;

        if (args.length == 2) {countString = args[1];}

        EntityType type = null;

        try {type = EntityType.valueOf(mob.toUpperCase());}
        catch (Exception ex) {p.sendMessage(Rexplugin.prefix + ChatColor.YELLOW + "Diesen Mob gibt es nicht"); return false;}

        if (!type.isSpawnable()) {p.sendMessage(Rexplugin.prefix + ChatColor.YELLOW + "Dieser Mob ist nicht spawnbar"); return false;}

        int count = 0;

        try {
            if (countString == null) {count = 1;}
            else { count = Integer.parseInt(countString);}
        }
        catch (NumberFormatException ex) {
            p.sendMessage(Rexplugin.prefix + ChatColor.YELLOW + "Deine Anzahl ist keine Zahl");
            return false;
        }

        for (int i = 0; i < count; i++) {
            p.getLocation().getWorld().spawnEntity(p.getLocation(), type);
        }

        p.sendMessage(Rexplugin.prefix + "DIe Mobs wurden erfolgreich gespawnt");
        Communication.playPositiveSound(p);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;

        if (args.length == 1) {
            for (EntityType value : EntityType.values()) {
                if (value.isSpawnable()) {
                    list.add(value.toString().toLowerCase());
                }
            }
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)) {
                completerList.add(s);
            }
        }

        return completerList;
    }
}
