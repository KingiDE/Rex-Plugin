package de.kingi.rexplugin.listeners;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.guis.ChallengeGui;
import de.kingi.rexplugin.timer.Timer;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimerChallengeListener implements Listener {

    public static String challengeStatus = "default"; // "default", "failed", "completed"

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        if (ChallengeGui.activeChallengeList.contains("nofalldamage") && Timer.isRunning) {
            failChallenge();
        }
        if (ChallengeGui.activeChallengeList.contains("schadenFreeze") && Timer.isRunning && e.getEntityType().equals(EntityType.PLAYER)) {
            Player p = (Player) e.getEntity();
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 255));
        }
        if (ChallengeGui.activeChallengeList.contains("schadenDoppelt") && Timer.isRunning && e.getEntityType().equals(EntityType.PLAYER)) {
            Player p = (Player) e.getEntity();
            double damage = e.getDamage();
            p.setHealth(p.getHealth() - damage);
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        if (!Timer.isRunning && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {e.setCancelled(true);}
        if (ChallengeGui.activeChallengeList.contains("noblockplace") && Timer.isRunning && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            failChallenge();
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        if (!Timer.isRunning && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {e.setCancelled(true);}
        if (ChallengeGui.activeChallengeList.contains("noblockbreak") && Timer.isRunning && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            failChallenge();
        }
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent e) {
        if (ChallengeGui.activeGoal.equals("enderdragon") && Timer.isRunning && e.getEntityType().equals(EntityType.ENDER_DRAGON)) {
            completeChallenge();
        }
        if (ChallengeGui.activeGoal.equals("wither") && Timer.isRunning && e.getEntityType().equals(EntityType.WITHER)) {
            completeChallenge();
        }
    }

    @EventHandler
    private void onItemCollect(EntityPickupItemEvent e) {
        if (ChallengeGui.activeGoal.equals("diamond") && Timer.isRunning && e.getItem().getItemStack().getType().equals(Material.DIAMOND) && e.getEntityType().equals(EntityType.PLAYER)) {
            completeChallenge();
        }
        if (ChallengeGui.activeChallengeList.contains("itemAufnehmenSchaden") && Timer.isRunning && e.getEntityType().equals(EntityType.PLAYER)) {
            e.getEntity().setHealth(e.getEntity().getHealth() - 1);
        }
    }

    @EventHandler
    private void onJump(PlayerMoveEvent e) {
        if (ChallengeGui.activeChallengeList.contains("springenTot") && Timer.isRunning && e.getTo().getY() > e.getFrom().getY() && e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            failChallenge();
        }
    }

    @EventHandler
    private void onAdvancement(PlayerAdvancementDoneEvent e) {
        if (ChallengeGui.activeChallengeList.contains("advancementSchaden") && Timer.isRunning && e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            double health = e.getPlayer().getHealth();
            e.getPlayer().setHealth(health - 1f);
        }
    }

    @EventHandler
    private void onXP(PlayerExpChangeEvent e) {
        if (ChallengeGui.activeChallengeList.contains("XPTot") && Timer.isRunning && e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            failChallenge();
        }
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e) {
        if (ChallengeGui.activeChallengeList.contains("droppenTot") && Timer.isRunning && e.getPlayer().getGameMode().equals(GameMode.SURVIVAL) ) {
            failChallenge();
        }
    }

    // wird ausgeführt, wenn die Challenge scheitert
    private void failChallenge() {
        Communication.sendAllPlayers(Rexplugin.prefix + ChatColor.RED + "Die " + ChatColor.BOLD + "CHALLENGE " + ChatColor.RED + "wurde nicht bestanden");
        Communication.playAllNegativSound();
        Timer.isRunning = false;
        challengeStatus = "failed";
    }

    // wird ausgeführt, wenn das Ziel (Goal) erreicht wird
    private void completeChallenge() {
        Communication.sendAllPlayers(Rexplugin.prefix + ChatColor.GREEN + "Die " + ChatColor.BOLD + "CHALLENGE " + ChatColor.GREEN + "wurde bestanden");
        Communication.playAllPositiveSound();
        Timer.isRunning = false;
        challengeStatus = "completed";
    }



}
