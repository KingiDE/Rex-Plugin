package de.kingi.rexplugin.commands;

import de.kingi.rexplugin.Rexplugin;
import de.kingi.rexplugin.listeners.TimerChallengeListener;
import de.kingi.rexplugin.timer.Timer;
import de.kingi.rexplugin.utils.Communication;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player p)) return true;

        String usage = Rexplugin.prefix + ChatColor.RED + "Verwende /timer <start/resume/pause/stop/reset>";

        if (args.length == 0) {p.sendMessage(usage); return false;}

        switch (args[0]) {
            case "start","resume" -> {
                Timer.isRunning = true;
                TimerChallengeListener.challengeStatus = "default";
                Communication.playPositiveSound(p);
            }
            case "stop","pause" -> { Timer.isRunning = false; Communication.playPositiveSound(p); }
            case "reset" -> {
                Timer.sekunden = 0; Timer.minuten = 0; Timer.stunden = 0; Timer.tage = 0; Timer.wochen = 0; TimerChallengeListener.challengeStatus = "default";
                Communication.playPositiveSound(p);
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;

        if (args.length == 1) {
            list.add("start");
            list.add("stop");
            list.add("reset");
            list.add("pause");
            list.add("resume");
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1];
        for (String s : list) {
            if (s.startsWith(currentarg)) {
                completerList.add(s);
            }
        }

        return completerList;
    }
}
