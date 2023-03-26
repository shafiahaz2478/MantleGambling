package me.shafishaz.mantlegambling.task;

import me.shafishaz.mantlegambling.Object.JackPotManager;
import me.shafishaz.mantlegambling.Utitlity.JackPotMessageUtility;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class JackPotTimer extends BukkitRunnable {

    private final JackPotManager jackPotManager;
    private final int originalCountdown;
    private int countdown;
    private final List<Integer> warnings;

    public JackPotTimer(JackPotManager jackPotManager , int countdown , List<Integer> warnings){
        this.jackPotManager = jackPotManager;
        this.originalCountdown = countdown;
        this.countdown = countdown;
        this.warnings = warnings;
    }

    @Override
    public void run() {
        if (warnings.contains(countdown)) {
            List<String> msg = JackPotMessageUtility.parseWarning();
            for (String line : msg) {
                Bukkit.broadcastMessage(line);
            }
        }
        if (countdown == 0) {
            jackPotManager.awardWinner();
            countdown = originalCountdown;
        }
        countdown--;
    }

    public String getTime() {
        return formatMilliSecondsToTime((long) countdown * 1000);
    }

    public String getShortTime() {
        long milliseconds = (long) countdown * 1000;
        int seconds = (int) (milliseconds / 1000);
        if (seconds >= 3600) {
            int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            if (minutes == 0) {
                return hours + "h";
            }
            return hours + "h, " + minutes + "m";
        } else if (seconds > 60) {
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            int sec = (int) (milliseconds / 1000) % 60;
            if (sec == 0) {
                return minutes + "m";
            }
            return minutes + "m, " + sec + "s";
        }
        return seconds + "s";
    }

    private String formatMilliSecondsToTime(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return hours + "h, " + minutes + "m, " + seconds + "s";
    }
}
