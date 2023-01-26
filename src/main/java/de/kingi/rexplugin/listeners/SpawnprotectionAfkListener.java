package de.kingi.rexplugin.listeners;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.commands.SpawnprotectionCommand;
import de.kingi.rexplugin.guis.SettingsGui;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpawnprotectionAfkListener implements Listener {

    private static List<String> spawnprot = new ArrayList<>();


    @EventHandler
    private void onDamage(EntityDamageEvent e) {
            if (e.getEntityType() != EntityType.PLAYER) return;

            Player p = (Player) e.getEntity();
            if (Rexplugin.config.getStringList("Afk").contains(p.getUniqueId().toString()) || spawnprot.contains(p.getUniqueId().toString())) {
                    e.setCancelled(true);
            }

    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        if (SettingsGui.activeSettingsList.contains("spawnprotection")) {
            spawnprot.add(e.getPlayer().getUniqueId().toString());

            new BukkitRunnable() {
                @Override
                public void run() {
                    spawnprot.remove(e.getPlayer().getUniqueId().toString());
                }
            }.runTaskLater(Rexplugin.plugin, SpawnprotectionCommand.spawnprotection);
        }
    }





}
