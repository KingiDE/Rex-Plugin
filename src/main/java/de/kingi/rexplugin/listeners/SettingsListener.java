package de.kingi.rexplugin.listeners;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.guis.SettingsGui;
import de.kingi.rexplugin.timer.Timer;
import de.kingi.rexplugin.utils.Communication;
import de.kingi.rexplugin.utils.SettingsUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class SettingsListener implements Listener {

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        if (SettingsGui.activeSettingsList.contains("damagelog")) {
            if (!e.getEntityType().equals(EntityType.PLAYER)) {return;}
            Player p = (Player) e.getEntity();
            Communication.sendAllPlayers(Rexplugin.prefix + ChatColor.BLUE + p.getName() + ChatColor.WHITE + " hat " + ChatColor.RED + e.getDamage() + ChatColor.WHITE + " Leben Schaden durch " + ChatColor.RED + e.getCause() + ChatColor.WHITE + " erhalten");
        }
    }

    @EventHandler
    private void onLogBreak(BlockBreakEvent e) {
        if (SettingsGui.activeSettingsList.contains("timber")) {
            Material mat = e.getBlock().getType();
            if (mat.equals(Material.OAK_LOG) || mat.equals(Material.BIRCH_LOG) || mat.equals(Material.DARK_OAK_LOG) || mat.equals(Material.ACACIA_LOG) ||
                mat.equals(Material.JUNGLE_LOG) || mat.equals(Material.SPRUCE_LOG) || mat.equals(Material.MANGROVE_LOG)) {
                int x = e.getBlock().getX(); int z = e.getBlock().getZ();World world = Bukkit.getWorld("world");
                for (int y = -64; y <= 324; y++) {
                    Location location = new Location(world, x, y, z);
                    switch (Bukkit.getWorld("world").getType(x, y, z)) {
                        case OAK_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.OAK_LOG));
                        }
                        case DARK_OAK_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.DARK_OAK_LOG));
                        }
                        case JUNGLE_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.JUNGLE_LOG));
                        }
                        case ACACIA_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.ACACIA_LOG));
                        }
                        case SPRUCE_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.SPRUCE_LOG));
                        }
                        case MANGROVE_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.MANGROVE_LOG));
                        }
                        case BIRCH_LOG -> {
                            world.setType(x, y, z, Material.AIR);
                            world.dropItem(location, new ItemStack(Material.BIRCH_LOG));
                        }
                    }
                }

            }
        }
    }

    @EventHandler
    private void preTimerMove(PlayerMoveEvent e) {
        if (!SettingsGui.activeSettingsList.contains("preTimerMove") && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE) && !Timer.isRunning) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void soup(PlayerInteractEvent e) {
        if (e.getItem().equals(null)) {return;}
        if (SettingsGui.activeSettingsList.contains("soup") && Objects.requireNonNull(e.getItem()).getType().equals(Material.MUSHROOM_STEW)) {
            e.setCancelled(true);
            double health = e.getPlayer().getHealth(); Player p = e.getPlayer();
            p.setFoodLevel(p.getFoodLevel() + 6);
            if (health > 15) {p.setHealth(20); return;}
            e.getPlayer().setHealth(e.getPlayer().getHealth() + 5);
        }
    }

    @EventHandler
    private void onFoodSink(FoodLevelChangeEvent e) {
        if (SettingsGui.activeSettingsList.contains("noHunger")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onCreeperExplode(EntityExplodeEvent e) {
        if (!SettingsGui.activeSettingsList.contains("creeperBlockExplode") && e.getEntityType().equals(EntityType.CREEPER)) {
            e.blockList().clear();
        }
    }

    @EventHandler
    private void onTNTPlace(BlockPlaceEvent e) {
        if (SettingsGui.activeSettingsList.contains("tntInstantFuse") && e.getBlock().getType().equals(Material.TNT)) {
            e.getBlockPlaced().setType(Material.AIR);
            TNTPrimed tnt = (TNTPrimed) e.getBlockPlaced().getWorld().spawnEntity(e.getBlockPlaced().getLocation(), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(80);
        }
    }

    @EventHandler
    private void onAttack(EntityDamageByEntityEvent e) {
        if (!SettingsGui.activeSettingsList.contains("pvp") && e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.PLAYER)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void blockRegeneration(EntityRegainHealthEvent e) {
        if (SettingsUtils.noRegeneration && e.getEntityType().equals(EntityType.PLAYER)) {
            e.setCancelled(true);
        }
    }

    // macht Spieler Glowing
    public static void makePlayersGlow() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Rexplugin.plugin, () -> {
            if (!SettingsGui.activeSettingsList.contains("playerGlow")) {return;}
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 1));
            }
        }, 20, 20);
    }

    // setzt Spielern die maximale Herzen
    public static void setPlayersHearts() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Rexplugin.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(SettingsUtils.maxHeartsValue);
            }
        }, 20, 20);
    }



}
