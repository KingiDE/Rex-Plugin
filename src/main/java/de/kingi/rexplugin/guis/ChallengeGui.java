package de.kingi.rexplugin.guis;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.utils.Challengeitem;
import de.kingi.rexplugin.utils.ItemBuilder;
import de.kingi.rexplugin.utils.heads.Skull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChallengeGui {

    public static List<String> activeChallengeList = Rexplugin.config.getStringList("ActiveChallengeList");
    public static String activeGoal = "";
    public static Inventory challengeInv;

    public static Challengeitem noFallDamage = new Challengeitem(Material.FEATHER, "No Fall Damage", "Du darfst keinen Fallschaden erhalten", "nofalldamage", 0);
    public static Challengeitem noBlockPlace = new Challengeitem(Material.COBBLESTONE, "Keine Blöcke platzieren", "Du darfst keine Blöcke platzieren", "noblockplace", 1);
    public static Challengeitem noBlockBreak = new Challengeitem(Material.IRON_PICKAXE, "Keine Blöcke abbauen", "Du darfst keine Blöcke abbauen", "noblockbreak", 2);
    public static Challengeitem itemAufnehmenSchaden = new Challengeitem(Material.CHEST, "Item aufnehmen = Schaden", "Wenn du ein Item aufnimmst, erhälst du Schaden", "itemAufnehmenSchaden", 3);
    public static Challengeitem springenTot = new Challengeitem(Material.OAK_STAIRS, "Springen = Tot", "Wenn du springst, stirbst du", "springenTot", 4);
    public static Challengeitem advancementSchaden = new Challengeitem(Material.SEA_LANTERN, "Advancement = Schaden", "Für jedes Advancement, das du sammelst, erhälst du Schaden", "advancementSchaden", 5);
    public static Challengeitem XPTot = new Challengeitem(Material.EXPERIENCE_BOTTLE, "XP = Tot", "Wenn du XP erhälst, stirbst du", "XPTot", 6);
    public static Challengeitem schadenFreeze = new Challengeitem(Material.BLUE_ICE, "Schaden = Freeze", "Sobald du Schaden nimmst, kannst du dich nicht mehr bewegen", "schadenFreeze", 7);
    public static Challengeitem droppenTot = new Challengeitem(Material.DROPPER, "Droppen = Tot", "Wenn du etwas droppst, stirbst du", "droppenTot", 8);
    public static Challengeitem schadenDoppelt = new Challengeitem(Material.IRON_SWORD, "Schaden x2", "Der Schaden ist doppelt", "schadenDoppelt", 9);

    public static ItemBuilder enderdragon = new ItemBuilder(Material.DRAGON_HEAD).setDisplayname(ChatColor.BLUE + "Enderdrache").setLore(ChatColor.WHITE + "Besiege den Enderdrachen").build();
    public static ItemBuilder wither = new ItemBuilder(Material.WITHER_SKELETON_SKULL).setDisplayname(ChatColor.BLUE + "Wither").setLore(ChatColor.WHITE + "Besiege den Wither").build();
    public static ItemBuilder diamond = new ItemBuilder(Material.DIAMOND).setDisplayname(ChatColor.BLUE + "Diamant").setLore(ChatColor.WHITE + "Sammle einen Diamanten").build();

    public static void createChallengeGui(Player p) {
        Inventory challengeGui = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Challenges");

        challengeGui.setItem(0, noFallDamage.getItemStack());
        challengeGui.setItem(1, noBlockPlace.getItemStack());
        challengeGui.setItem(2, noBlockBreak.getItemStack());
        challengeGui.setItem(3, itemAufnehmenSchaden.getItemStack());
        challengeGui.setItem(4, springenTot.getItemStack());
        challengeGui.setItem(5, advancementSchaden.getItemStack());
        challengeGui.setItem(6, XPTot.getItemStack());
        challengeGui.setItem(7, schadenFreeze.getItemStack());
        challengeGui.setItem(8, droppenTot.getItemStack());
        challengeGui.setItem(9, schadenDoppelt.getItemStack());

        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName("❌");
        glass.setItemMeta(glassMeta);
        for (int i = 27; i <= 35; i++) {challengeGui.setItem(i, glass);}

        challengeGui.setItem(36, enderdragon.getItemStack());
        challengeGui.setItem(37, wither.getItemStack());
        challengeGui.setItem(38, diamond.getItemStack());

        ItemStack backitem = Skull.getCustomSkull("https://textures.minecraft.net/texture/3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025");
        ItemMeta backitemMeta = backitem.getItemMeta();
        backitemMeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "❌");
        backitem.setItemMeta(backitemMeta);
        challengeGui.setItem(53, backitem);

        p.openInventory(challengeGui);
        challengeInv = challengeGui;
    }
}
