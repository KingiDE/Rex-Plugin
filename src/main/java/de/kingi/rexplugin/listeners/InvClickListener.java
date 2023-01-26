package de.kingi.rexplugin.listeners;

import de.kingi.rexplugin.guis.ChallengeGui;
import de.kingi.rexplugin.guis.SettingsGui;
import de.kingi.rexplugin.utils.Communication;
import de.kingi.rexplugin.utils.SettingsUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClickListener implements Listener {

    @EventHandler
    private void onInvClick(InventoryClickEvent e) {
        String invName = e.getView().getTitle();
        int clickedSlot = e.getSlot();
        Player clickPlayer = (Player) e.getWhoClicked();

        if (invName.equals(ChatColor.BLUE + "Settings")) {
            e.setCancelled(true);

            switch (clickedSlot) {
                case 0 -> SettingsGui.damagelog.toggleSettings();
                case 1 -> {
                    if (e.isShiftClick() && SettingsUtils.maxHeartsValue > 1) {
                        SettingsUtils.maxHeartsValue --;
                    }
                    else {
                        SettingsUtils.maxHeartsValue ++;
                    }
                    SettingsUtils.setMaxHeartsItem(SettingsGui.settingsInv, 1);
                    Communication.playNegativSound(clickPlayer);
                }
                case 2 -> SettingsGui.timber.toggleSettings();
                case 3 -> SettingsGui.pvp.toggleSettings();
                case 4 -> {SettingsUtils.toggleKeepInv(); SettingsUtils.setKeepInvItem(SettingsGui.settingsInv, 4);}
                case 5 -> SettingsGui.preTimerMove.toggleSettings();
                case 6 -> SettingsGui.playerGlow.toggleSettings();
                case 7 -> {SettingsUtils.toggleInstantRespawn(); SettingsUtils.setInstantRespawnItem(SettingsGui.settingsInv, 7);}
                case 8 -> {SettingsUtils.switchRegenerationType(); SettingsUtils.setRegenerationItem(SettingsGui.settingsInv, 8);}
                case 9 -> {SettingsUtils.switchDifficulty(); SettingsUtils.setDifficultyItem(SettingsGui.settingsInv, 9);}
                case 10 -> SettingsGui.soup.toggleSettings();
                case 11 -> SettingsGui.noHunger.toggleSettings();
                case 12 -> SettingsGui.TNTInstantFuse.toggleSettings();
                case 13 -> SettingsGui.creeperBlockExplode.toggleSettings();
                case 14 -> SettingsGui.spawnprotection.toggleSettings();
                case 15 -> SettingsGui.timer.toggleSettings();
                case 16 -> {
                    int wert = Bukkit.getWorld("world").getGameRuleValue(GameRule.RANDOM_TICK_SPEED);
                    if (e.isShiftClick()) {
                        Bukkit.getWorld("world").setGameRule(GameRule.RANDOM_TICK_SPEED, wert - 1);
                    }
                    else {
                        Bukkit.getWorld("world").setGameRule(GameRule.RANDOM_TICK_SPEED, wert + 1);
                    }
                    SettingsGui.tickspeed.setLore(org.bukkit.ChatColor.WHITE + "Die Tickgeschwindigkeit kann umgestellt werden", org.bukkit.ChatColor.DARK_PURPLE + org.bukkit.ChatColor.BOLD.toString() + Bukkit.getWorld("world").getGameRuleValue(GameRule.RANDOM_TICK_SPEED)).build();
                    SettingsGui.settingsInv.setItem(16, SettingsGui.tickspeed.getItemStack());
                }
                case 53 -> clickPlayer.closeInventory();
                default -> Communication.playNegativSound(clickPlayer);
            }
        }

        if (invName.equals(ChatColor.BLUE + "Challenges")) {
            e.setCancelled(true);

            switch (clickedSlot) {
                case 0 -> ChallengeGui.noFallDamage.changeChallenge();
                case 1 -> ChallengeGui.noBlockPlace.changeChallenge();
                case 2 -> ChallengeGui.noBlockBreak.changeChallenge();
                case 3 -> ChallengeGui.itemAufnehmenSchaden.changeChallenge();
                case 4 -> ChallengeGui.springenTot.changeChallenge();
                case 5 -> ChallengeGui.advancementSchaden.changeChallenge();
                case 6 -> ChallengeGui.XPTot.changeChallenge();
                case 7 -> ChallengeGui.schadenFreeze.changeChallenge();
                case 8 -> ChallengeGui.droppenTot.changeChallenge();
                case 9 -> ChallengeGui.schadenDoppelt.changeChallenge();
                case 36 -> ChallengeGui.challengeInv.setItem(44, ChallengeGui.enderdragon.getItemStack());
                case 37 -> ChallengeGui.challengeInv.setItem(44, ChallengeGui.wither.getItemStack());
                case 38 -> ChallengeGui.challengeInv.setItem(44, ChallengeGui.diamond.getItemStack());
                case 53 -> clickPlayer.closeInventory();
                default -> Communication.playNegativSound(clickPlayer);
            }
        }

    }
}
