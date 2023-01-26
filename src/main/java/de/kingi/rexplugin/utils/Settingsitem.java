package de.kingi.rexplugin.utils;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.guis.SettingsGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Settingsitem {

    private ItemStack is;
    private ItemMeta im;
    private String name;
    private List<String> itemDescription = new ArrayList<>();
    private String configId;
    private int slot;

    public Settingsitem(Material icon, String name, String description, String configId, int slot) {
        is = new ItemStack(icon);
        im = is.getItemMeta();
        im.setDisplayName(ChatColor.BLUE + name); // Name ist immer blau
        itemDescription.add(ChatColor.WHITE + description); // Description immer weiß
        if (SettingsGui.activeSettingsList.contains(configId)) {
            itemDescription.add(ChatColor.WHITE + "Status: " + ChatColor.GREEN + ChatColor.BOLD + "✔");
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        else {
            itemDescription.add(ChatColor.WHITE + "Status: " + ChatColor.RED + "❌");
            im.removeEnchant(Enchantment.DURABILITY);
            im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        im.setLore(itemDescription);
        is.setItemMeta(im);
        this.configId = configId;
        this.name = name;
        this.slot = slot;
    }

    public void toggleSettings() {
        if (SettingsGui.activeSettingsList.contains(configId)) {
            SettingsGui.activeSettingsList.remove(configId);

            im.removeEnchant(Enchantment.DURABILITY);
            im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemDescription.set(1, ChatColor.WHITE + "Status: " + ChatColor.RED + "❌");
            im.setLore(itemDescription);
            is.setItemMeta(im);

            SettingsGui.settingsInv.setItem(slot, is);

            Communication.sendAllPlayers(Rexplugin.prefix + ChatColor.WHITE + "Die Einstellung " + ChatColor.BLUE + name + ChatColor.WHITE + " wurde " + ChatColor.RED + "deaktiviert");
        }
        else {
            SettingsGui.activeSettingsList.add(configId);

            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemDescription.set(1, ChatColor.WHITE + "Status: " + ChatColor.GREEN + ChatColor.BOLD + "✔");
            im.setLore(itemDescription);
            is.setItemMeta(im);

            SettingsGui.settingsInv.setItem(slot, is);

            Communication.sendAllPlayers(Rexplugin.prefix + ChatColor.WHITE + "Die Einstellung " + ChatColor.BLUE + name + ChatColor.WHITE + " wurde " + ChatColor.GREEN + "aktiviert");
        }
    }

    public ItemStack getItemStack() {
        return is;
    }
}
