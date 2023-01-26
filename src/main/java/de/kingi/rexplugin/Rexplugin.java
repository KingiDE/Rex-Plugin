package de.kingi.rexplugin;

import de.kingi.rexplugin.commands.*;
import de.kingi.rexplugin.guis.ChallengeGui;
import de.kingi.rexplugin.guis.SettingsGui;
import de.kingi.rexplugin.listeners.*;
import de.kingi.rexplugin.timer.Timer;
import de.kingi.rexplugin.utils.SettingsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class Rexplugin extends JavaPlugin {

    public static Rexplugin plugin;
    public static String prefix;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        config = plugin.getConfig(); // geht erst, wenn das Plugin gesetzt wurde

        if (config.getString("Prefix") != null) {
            prefix = config.getString("Prefix");
        } else {
            prefix = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "GHG" + ChatColor.GRAY + "] "; // normaler Prefix, wenn nichts in der config gesetzt ist
        }

        getCommand("afk").setExecutor(new AfkCommand());
        getCommand("prefix").setExecutor(new PrefixCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("spawnprotection").setExecutor(new SpawnprotectionCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("spawnmob").setExecutor(new SpawnmobCommand());
        getCommand("position").setExecutor(new PositionCommand());
        getCommand("challenge").setExecutor(new ChallengeCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("inventory").setExecutor(new InventoryCommand());

        Bukkit.getPluginManager().registerEvents(new SpawnprotectionAfkListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InvClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimerChallengeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SettingsListener(), this);

        SettingsListener.makePlayersGlow();
        SettingsListener.setPlayersHearts();

        // lädt alles vom Timer
        Timer.beginTimer(); // startet nur die Loop, aber Timer wird nicht gestartet
        Timer.sekunden = config.getInt("Timer.Sekunden");
        Timer.minuten = config.getInt("Timer.Minuten");
        Timer.stunden = config.getInt("Timer.Stunden");
        Timer.tage = config.getInt("Timer.Tage");
        Timer.wochen = config.getInt("Timer.Wochen");
        Timer.isRunning = config.getBoolean("Timer.isRunning");

        // Enabling-Nachricht:
        Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.LIGHT_PURPLE + ">--------------------------------<");
        Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.BLUE + "Rexplugin was " + ChatColor.GREEN + "sucessfully enabled");
        Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.LIGHT_PURPLE + ">--------------------------------<");

    }

    @Override
    public void onDisable() {
        config.set("Prefix", prefix); // setzt den aktuellen Prefix auch in die config
        config.set("MaxHearts", SettingsUtils.maxHeartsValue);
        config.set("NoRegeneration", SettingsUtils.noRegeneration);
        config.set("FlyList", FlyCommand.fly);
        config.set("InventoryList", InventoryCommand.invNames);
        config.set("Challengestatus", TimerChallengeListener.challengeStatus);
        config.set("Settings", SettingsGui.activeSettingsList);
        config.set("SpawnprotectionInTicks", SpawnprotectionCommand.spawnprotection);
        PositionCommand.positionConfig.set("PositionList", PositionCommand.posNames);
        config.set("ActiveChallengeList", ChallengeGui.activeChallengeList);
        config.set("ActiveGoal", ChallengeGui.activeGoal);
        config.set("Timer.Sekunden", Timer.sekunden);
        config.set("Timer.Minuten", Timer.minuten);
        config.set("Timer.Stunden", Timer.stunden);
        config.set("Timer.Tage", Timer.tage);
        config.set("Timer.Wochen", Timer.wochen);
        config.set("Timer.isRunning", Timer.isRunning);

        plugin.saveConfig();
        PositionCommand.positionConfig.save();
    }
}
