package de.kingi.rexplugin.listeners;

import de.kingi.rexplugin.Rexplugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(Rexplugin.prefix + ChatColor.GREEN + ">> " + ChatColor.WHITE + e.getPlayer().getName());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(Rexplugin.prefix + ChatColor.RED + "<< " + ChatColor.WHITE + e.getPlayer().getName());
    }
}
