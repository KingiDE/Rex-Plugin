package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Communication;
import de.kingi.rexplugin.utils.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PositionCommand implements CommandExecutor, TabCompleter {

    public static Config positionConfig = new Config("position.yml", Rexplugin.plugin.getDataFolder());
    public static List<String> posNames = positionConfig.getConfiguration().getStringList("PositionList");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        String usage = Rexplugin.prefix + ChatColor.RED + "Verwende /position <get/set/delete> <Name>";

        if (args.length == 0) return false;

        if (args.length == 1) { p.sendMessage(usage); return false; }
        String posName = args[1];

        if (args[0].equals("set")) {
            posNames.add(posName);

            positionConfig.set(posName + ".X", p.getLocation().getBlockX());
            positionConfig.set(posName + ".Y", p.getLocation().getBlockY());
            positionConfig.set(posName + ".Z", p.getLocation().getBlockZ());
            positionConfig.set(posName + ".Dimension", p.getWorld().getName());

            Communication.sendAllPlayers(Rexplugin.prefix + ChatColor.YELLOW + p.getName() + " " +
                    ChatColor.WHITE + "hat an den Koordinaten " + ChatColor.YELLOW + p.getLocation().getBlockX() + " " +
                    p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ() + " [" + p.getWorld().getName() + "] " + ChatColor.WHITE + "die Position " +
                    ChatColor.YELLOW + posName + " " + ChatColor.WHITE + "erstellt");
            Communication.playPositiveSound(p);
        }
        else if (args[0].equals("get")) {
            Object posX = positionConfig.get(posName + ".X");
            Object posY = positionConfig.get(posName + ".Y");
            Object posZ = positionConfig.get(posName + ".Z");
            Object posDimension = positionConfig.get(posName + ".Dimension");

            if (posX == null || posY == null || posZ == null || posDimension == null) {p.sendMessage(Rexplugin.prefix + ChatColor.RED + "Diese Position gbt es nicht"); return true;}

            p.sendMessage(Rexplugin.prefix + ChatColor.WHITE + "Die Position " + ChatColor.YELLOW + posName + " " + ChatColor.WHITE + "befindet sich bei " +
                    ChatColor.YELLOW + posX + " " + posY + " " + posZ + " [" + posDimension + "]");
            TextComponent teleport = new TextComponent(ChatColor.BLUE + "[Teleportieren]");
            teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @p " + posX + " " + posY + " " + posZ));
            p.spigot().sendMessage(teleport);
            Communication.playPositiveSound(p);
        }
        else if (args[0].equals("delete")) {

            if (positionConfig.get(posName + ".X") == null || positionConfig.get(posName + ".Y") == null || positionConfig.get(posName + ".Z") == null || positionConfig.get(posName + ".Dimension") == null)
                {p.sendMessage(Rexplugin.prefix + ChatColor.YELLOW + "Diese Position gibt es nicht"); return true;}

            posNames.remove(posName);

            positionConfig.set(posName + ".X", null);
            positionConfig.set(posName + ".Y", null);
            positionConfig.set(posName + ".Z", null);
            positionConfig.set(posName + ".Dimension", null);

            p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Die Position wurde erfolgreich gelöscht");
            Communication.playPositiveSound(p);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;

        if (args.length == 1) {
            list.add("set");
            list.add("get");
            list.add("delete");
        }

        if (args.length == 2) {
            list.addAll(posNames);
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
