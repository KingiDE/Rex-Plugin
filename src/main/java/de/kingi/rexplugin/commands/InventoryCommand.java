package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Base64;
import de.kingi.rexplugin.utils.Communication;
import de.kingi.rexplugin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryCommand implements CommandExecutor, TabCompleter {

    public static List<String> invNames = Rexplugin.config.getStringList("InventoryList");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;

        String usage = Rexplugin.prefix + ChatColor.YELLOW + "Verwende /inv <load/save> <Name>";

        if (args.length == 0 ) {return false;}

        if (args[0].equals("save")) {
            if (args.length == 1) {p.sendMessage(usage); return false;}
            invNames.add(args[1]);
            Rexplugin.config.set("Inventory." + args[1], Base64.itemStackArrayToBase64(p.getInventory().getContents()));
            p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Das Inventar wurde erfolgreich gespeichert");
            Communication.playPositiveSound(p);
        }
        else if (args[0].equals("load")) {
            if (args.length == 1) {p.sendMessage(usage); return false;}
            if (args[1].equals("CREATIVE")) {openinv(p); return true;}
            try {
                p.getInventory().setContents(Base64.itemStackArrayFromBase64(Rexplugin.config.getString("Inventory." + args[1])));
            } catch (IOException e) {
                // nichts machen
            }
            p.sendMessage(Rexplugin.prefix + ChatColor.GREEN + "Das Inventar wurde erfolgreich geladen");
            Communication.playPositiveSound(p);
        }
        else {p.sendMessage(usage);}


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;

        if (args.length == 1) {
            list.add("save");
            list.add("load");
        }

        if (args.length == 2) {
            list.addAll(invNames);
            list.add("CREATIVE");
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


    private void openinv(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Creative Items");

        ItemBuilder commandblock = new ItemBuilder(Material.COMMAND_BLOCK).setDisplayname(ChatColor.RED + "COMMANDBLOCK")
                .setLore(ChatColor.WHITE + "Der wohl mächtigste Block, der im Spiel existiert", ChatColor.WHITE + "Hiermit ist alles möglich")
                .build().setToInventory(0, inv);

        ItemBuilder structureblock = new ItemBuilder(Material.STRUCTURE_BLOCK).setDisplayname(ChatColor.BLUE + "STRUCTUREBLOCK")
                .setLore(ChatColor.WHITE + "Dieser Block wird genutzt, um große Gebäude zu klonen", ChatColor.WHITE + "Worldedit tut's auch")
                .build().setToInventory(1, inv);

        ItemBuilder structurevoid = new ItemBuilder(Material.STRUCTURE_VOID).setDisplayname(ChatColor.AQUA + "STRUCTUREVOID")
                .setLore(ChatColor.WHITE + "Das ist ein Item bzw. Block, mit dem du Gebiet speeren kannst", ChatColor.WHITE + "Somit kann an dieser Stelle kein weiterer Block platziert werden")
                .build().setToInventory(2, inv);

        ItemBuilder debugstick = new ItemBuilder(Material.DEBUG_STICK).setDisplayname(ChatColor.LIGHT_PURPLE + "DEBUGSTICK")
                .setLore(ChatColor.WHITE + "Hiermit kannst jede Variante jedes im Spiel existierenden Blocks herstellen", ChatColor.WHITE + "Unglaubliche Macht!")
                .build().setToInventory(3, inv);

        ItemBuilder barrier = new ItemBuilder(Material.BARRIER).setDisplayname(ChatColor.RED + "BARRIER")
                .setLore(ChatColor.WHITE + "Die stärkere Alternative zur Stucture-Void", ChatColor.WHITE + "Hier kann keine Entität hin oder ein Block platziert werden")
                .build().setToInventory(4, inv);

        ItemBuilder knowledgebook = new ItemBuilder(Material.KNOWLEDGE_BOOK).setDisplayname(ChatColor.AQUA + "KNOWLEDGEBOOK")
                .setLore(ChatColor.WHITE + "Irgendein nutzloses Item", ChatColor.WHITE + "Keine Ahnung welchen Nutzen das hat")
                .build().setToInventory(5, inv);

        ItemBuilder light = new ItemBuilder(Material.LIGHT).setDisplayname(ChatColor.GOLD + "LIGHT")
                .setLore(ChatColor.WHITE + "Light oder in der Bedrock Light-Block genannt, wird vor allem", ChatColor.WHITE + "von Map-Makern genutzt, um Plätze ohne Lichtquelle zu erleuchten")
                .build().setToInventory(6, inv);

        ItemBuilder spawner = new ItemBuilder(Material.SPAWNER).setDisplayname(ChatColor.GOLD + "SPAWNER")
                .setLore(ChatColor.WHITE + "Spawnt jedes Monster, das ein Spawnegg besitzt", ChatColor.WHITE + "Er kann einem verbuggten Mineshaft generieren")
                .build().setToInventory(7, inv);

        player.openInventory(inv);
    }
}
