package de.kingi.rexplugin.utils;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.heads.Skull;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsUtils {

    private static final ItemBuilder keepInvTRUE = new ItemBuilder(Material.SHULKER_SHELL).setDisplayname(ChatColor.BLUE + "Keep Inventory").setLore(ChatColor.WHITE + "Behalte dein Inventar nach dem Tod", ChatColor.WHITE + "Status: " + ChatColor.GREEN + "✔").addEnchant(Enchantment.DURABILITY, 1).hideEnchants().build();
    private static final ItemBuilder keepInvFALSE = new ItemBuilder(Material.SHULKER_SHELL).setDisplayname(ChatColor.BLUE + "Keep Inventory").setLore(ChatColor.WHITE + "Behalte dein Inventar nach dem Tod", ChatColor.WHITE + "Status: " + ChatColor.RED + "❌").build();

    private static final ItemBuilder instantRespawnTRUE = new ItemBuilder(Material.TOTEM_OF_UNDYING).setDisplayname(ChatColor.BLUE + "Instant Respawn").setLore(ChatColor.WHITE + "Ermöglicht sofortigen Respawn", ChatColor.WHITE + "Status: " + ChatColor.GREEN + "✔").addEnchant(Enchantment.DURABILITY, 1).hideEnchants().build();
    private static final ItemBuilder instantRespawnFALSE = new ItemBuilder(Material.TOTEM_OF_UNDYING).setDisplayname(ChatColor.BLUE + "Instant Respawn").setLore(ChatColor.WHITE + "Ermöglicht sofortigen Respawn", ChatColor.WHITE + "Status: " + ChatColor.RED + "❌").build();

    private static final ItemStack maxHearts = Skull.getCustomSkull("https://textures.minecraft.net/texture/2869bdd9a8f77eeff75d8f67ed0322bd9c16dd494972314ed707dd10a3139a58");
    public static int maxHeartsValue = Rexplugin.config.getInt("MaxHearts");

    private static final ItemBuilder difficultyPEACEFUL = new ItemBuilder(Material.GREEN_DYE).setDisplayname(ChatColor.BLUE + "Schwierigkeit").setLore(ChatColor.WHITE + "Die Schwierigkeit ist " + ChatColor.DARK_GREEN + "FRIEDLICH").build();
    private static final ItemBuilder difficultyEASY = new ItemBuilder(Material.YELLOW_DYE).setDisplayname(ChatColor.BLUE + "Schwierigkeit").setLore(ChatColor.WHITE + "Die Schwierigkeit ist " + ChatColor.YELLOW + "EINFACH").build();
    private static final ItemBuilder difficultyNORMAL = new ItemBuilder(Material.ORANGE_DYE).setDisplayname(ChatColor.BLUE + "Schwierigkeit").setLore(ChatColor.WHITE + "Die Schwierigkeit ist " + ChatColor.GOLD + "NORMAL").build();
    private static final ItemBuilder difficultyHARD = new ItemBuilder(Material.RED_DYE).setDisplayname(ChatColor.BLUE + "Schwierigkeit").setLore(ChatColor.WHITE + "Die Schwierigkeit ist " + ChatColor.RED + "SCHWER").build();

    private static final ItemBuilder regenerationDefault = new ItemBuilder(Material.GOLDEN_CARROT).setDisplayname(ChatColor.BLUE + "Regenerationsart").setLore(ChatColor.WHITE + "Die Regenerationsart ist " + ChatColor.GREEN + "standardmäßig").build();
    private static final ItemBuilder regenerationUHC = new ItemBuilder(Material.GOLDEN_CARROT).setDisplayname(ChatColor.BLUE + "Regenerationsart").setLore(ChatColor.WHITE + "Die Regenerationsart ist " + ChatColor.YELLOW + "UHC").build();
    private static final ItemBuilder regenerationUUHC = new ItemBuilder(Material.GOLDEN_CARROT).setDisplayname(ChatColor.BLUE + "Regenerationsart").setLore(ChatColor.WHITE + "Die Regenerationsart ist " + ChatColor.RED + "UUHC").build();
    public static boolean noRegeneration = Rexplugin.config.getBoolean("NoRegeneration");

    public static void toggleKeepInv() {
        if(Bukkit.getWorld("world").getGameRuleValue(GameRule.KEEP_INVENTORY)) {
            Bukkit.getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, false);
        }
        else {
            Bukkit.getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, true);
        }
    }

    public static void setKeepInvItem(Inventory inv, int slot) {
        if(Bukkit.getWorld("world").getGameRuleValue(GameRule.KEEP_INVENTORY)) {
            inv.setItem(slot, keepInvTRUE.getItemStack());
        }
        else {
            inv.setItem(slot, keepInvFALSE.getItemStack());
        }
    }

    public static void toggleInstantRespawn() {
        if(Bukkit.getWorld("world").getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN)) {
            Bukkit.getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, false);
        }
        else {
            Bukkit.getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        }
    }

    public static void setInstantRespawnItem(Inventory inv, int slot) {
        if(Bukkit.getWorld("world").getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN)) {
            inv.setItem(slot, instantRespawnTRUE.getItemStack());
        }
        else {
            inv.setItem(slot, instantRespawnFALSE.getItemStack());
        }
    }

    public static void setMaxHeartsItem(Inventory inv, int slot) {
        ItemMeta maxHeartsMeta = maxHearts.getItemMeta();
        maxHeartsMeta.setDisplayName(ChatColor.BLUE + "Max Hearts");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.WHITE + "Die maximalen Leben, die ein Spieler haben kann");
        lore.add(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + String.valueOf(maxHeartsValue));
        maxHeartsMeta.setLore(lore);
        maxHearts.setItemMeta(maxHeartsMeta);

        inv.setItem(slot, maxHearts);
    }

    public static void setDifficultyItem(Inventory inv, int slot) {
        String difficulty = Bukkit.getWorld("world").getDifficulty().toString();
        if (difficulty.equals("PEACEFUL")) {inv.setItem(slot, difficultyPEACEFUL.getItemStack());}
        if (difficulty.equals("EASY")) {inv.setItem(slot, difficultyEASY.getItemStack());}
        if (difficulty.equals("NORMAL")) {inv.setItem(slot, difficultyNORMAL.getItemStack());}
        if (difficulty.equals("HARD")) {inv.setItem(slot, difficultyHARD.getItemStack());}
    }

    public static void switchDifficulty() {
        String difficulty = Bukkit.getWorld("world").getDifficulty().toString();
        if (difficulty.equals("PEACEFUL")) {Bukkit.getWorld("world").setDifficulty(Difficulty.EASY);}
        if (difficulty.equals("EASY")) {Bukkit.getWorld("world").setDifficulty(Difficulty.NORMAL);}
        if (difficulty.equals("NORMAL")) {Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);}
        if (difficulty.equals("HARD")) {Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);}
    }

    public static void setRegenerationItem(Inventory inv, int slot) {
        World world = Bukkit.getWorld("world");
        if (world.getGameRuleValue(GameRule.NATURAL_REGENERATION) && !noRegeneration) {
            inv.setItem(slot, regenerationDefault.getItemStack());
        }
        else if (!world.getGameRuleValue(GameRule.NATURAL_REGENERATION) && !noRegeneration) {
            inv.setItem(slot, regenerationUHC.getItemStack());
        }
        else if (noRegeneration) {
            inv.setItem(slot, regenerationUUHC.getItemStack());
        }

        /*
        Standard: natural: true && anyRegenBlock: false
        UHC: natural: false && anyRegenBlock: false
        UUHC: anyRegenBlock: true
        */
    }

    public static void switchRegenerationType() {
        World world = Bukkit.getWorld("world");
        if (world.getGameRuleValue(GameRule.NATURAL_REGENERATION) && !noRegeneration) {
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        }
        else if (!world.getGameRuleValue(GameRule.NATURAL_REGENERATION) && !noRegeneration) {
            noRegeneration = true;
        }
        else if (noRegeneration) {
            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
            noRegeneration = false;
        }
    }

}
