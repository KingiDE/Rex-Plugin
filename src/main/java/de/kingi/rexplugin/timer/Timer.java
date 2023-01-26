package de.kingi.rexplugin.timer;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.guis.SettingsGui;
import de.kingi.rexplugin.listeners.TimerChallengeListener;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Timer {

    public static int sekunden;
    public static int minuten;
    public static int stunden;
    public static int tage;
    public static int wochen;
    public static boolean isRunning;

    public static void beginTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Rexplugin.plugin, () -> {
            sendActionbar();
            if (isRunning) {sekunden ++;};

            while (sekunden >= 60) {sekunden = sekunden - 60; minuten ++;}
            while (minuten >= 60) {minuten = minuten - 60; stunden ++;}
            while (stunden >= 60) {stunden = stunden - 60; tage ++;}
            while (tage >= 60) {tage = tage - 60; wochen ++;}
            sendActionbar();

        }, 20, 20);

    }

    private static void sendActionbar() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {

            if (!SettingsGui.activeSettingsList.contains("timer")) {return;}

            // wenn gestoppt und alles 0
            if (!isRunning && sekunden == 0 && minuten == 0 && stunden == 0 && tage == 0 && wochen == 0) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "Der Timer ist pausiert")); continue;
            }

            String fixedTime = "";

            if (wochen != 0) {fixedTime = fixedTime + wochen + "w ";}
            if (tage != 0) {fixedTime = fixedTime + tage + "d ";}
            if (stunden != 0) {fixedTime = fixedTime + stunden + "h ";}
            if (minuten != 0) {fixedTime = fixedTime + minuten + "m ";}
            if (sekunden != 0) {fixedTime = fixedTime + sekunden + "s";}


            // Challenge failed (und pausiert)
            if (!isRunning && TimerChallengeListener.challengeStatus.equals("failed")) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + ">> " + ChatColor.RED.toString()
                        + ChatColor.BOLD + fixedTime + ChatColor.RED + " (pausiert)" + ChatColor.GRAY + " <<")); continue;
            }
            // Challenge completed (und pausiert)
            if (!isRunning && TimerChallengeListener.challengeStatus.equals("completed")) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + ">> " + ChatColor.GREEN.toString()
                        + ChatColor.BOLD + fixedTime + ChatColor.GREEN + " (pausiert)" + ChatColor.GRAY + " <<")); continue;
            }
            // wenn gestoppt und NICHT 0
            if (!isRunning) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + ">> " + ChatColor.GOLD.toString()
                        + ChatColor.BOLD + fixedTime + ChatColor.GOLD + " (pausiert)" + ChatColor.GRAY + " <<")); continue;
            }

            // Standard
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + ">> " + ChatColor.GOLD.toString()
                    + ChatColor.BOLD + fixedTime + ChatColor.GRAY + " <<"));

        }
    }

}
