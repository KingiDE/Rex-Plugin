package de.kingi.rexplugin.guis;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.ItemBuilder;
import de.kingi.rexplugin.utils.SettingsUtils;
import de.kingi.rexplugin.utils.Settingsitem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SettingsGui {

    public static List<String> activeSettingsList = Rexplugin.config.getStringList("Settings");
    public static Inventory settingsInv;

    public static Settingsitem damagelog = new Settingsitem(Material.CACTUS, "Damagelog", "Zeigt dir den Schaden im Chat an", "damagelog", 0);
    //public static Settingsitem maxhearts;
    public static Settingsitem timber = new Settingsitem(Material.DARK_OAK_LOG, "Timber", "Baut einen Baum direkt ab", "timber", 2);
    public static Settingsitem pvp = new Settingsitem(Material.IRON_SWORD, "PVP", "Lässt PVP aktivieren", "pvp", 3);
    //public static Settingsitem keepInv;
    public static Settingsitem preTimerMove = new Settingsitem(Material.PHANTOM_MEMBRANE, "Pre Timer Movement", "Gibt an, ob du dich bei pausiertem Timer bewegen kannst", "preTimerMove", 5);
    public static Settingsitem playerGlow = new Settingsitem(Material.SPECTRAL_ARROW, "Player Glow", "Macht slle Spiele durch Wände hindurch sichtbar","playerGlow", 6);
    //public static ItemBuilder instantRespawn;
    //public static Settingsitem difficulty;
    public static Settingsitem soup = new Settingsitem(Material.MUSHROOM_STEW, "Soup", "Stelle ein, ob man soupen kann", "soup", 10);
    public static Settingsitem noHunger = new Settingsitem(Material.COOKED_BEEF, "No Hunger","Verhindert, dass Spieler Hunger bekommen", "noHunger", 11);
    public static Settingsitem TNTInstantFuse = new Settingsitem(Material.TNT, "TNT Instant Fuse", "TNT zündet sofort beim Platzieren", "tntInstantFuse", 12);
    public static Settingsitem creeperBlockExplode = new Settingsitem(Material.CREEPER_SPAWN_EGG, "Creeper Block Explode", "Creeper können Blöcke zerstören", "creeperBlockExplode", 13);
    public static Settingsitem spawnprotection = new Settingsitem(Material.GOLDEN_APPLE, "Spawnprotection", "Aktiviere die Spawnprotection", "spawnprotection",14);
    public static Settingsitem timer = new Settingsitem(Material.CLOCK, "Timer", "Aktiviere den Timer", "timer",15);
    public static ItemBuilder tickspeed = new ItemBuilder(Material.WHEAT_SEEDS).setDisplayname(ChatColor.BLUE + "Tickspeed").setLore(ChatColor.WHITE + "Die Tickgeschwindigkeit kann umgestellt werden", ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + Bukkit.getWorld("world").getGameRuleValue(GameRule.RANDOM_TICK_SPEED)).build();
    //public static Settingsitem regeneration;

    public static void createSettingsGui(Player p) {
        Inventory settingsGui = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Settings");

        settingsGui.setItem(0, damagelog.getItemStack());
        SettingsUtils.setMaxHeartsItem(settingsGui, 1);
        settingsGui.setItem(2, timber.getItemStack());
        settingsGui.setItem(3, pvp.getItemStack());
        SettingsUtils.setKeepInvItem(settingsGui, 4);
        settingsGui.setItem(5, preTimerMove.getItemStack());
        settingsGui.setItem(6, playerGlow.getItemStack());
        SettingsUtils.setInstantRespawnItem(settingsGui, 7);
        SettingsUtils.setDifficultyItem(settingsGui, 9);
        settingsGui.setItem(10, soup.getItemStack());
        settingsGui.setItem(11, noHunger.getItemStack());
        settingsGui.setItem(12, TNTInstantFuse.getItemStack());
        settingsGui.setItem(13, creeperBlockExplode.getItemStack());
        settingsGui.setItem(14, spawnprotection.getItemStack());
        settingsGui.setItem(15, timer.getItemStack());
        settingsGui.setItem(16, tickspeed.getItemStack());
        SettingsUtils.setRegenerationItem(settingsGui, 8);


        p.openInventory(settingsGui);
        settingsInv = settingsGui;
    }
}
