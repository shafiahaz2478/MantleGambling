package me.shafishaz.mantlegambling.Object;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Utitlity.JackPotMessageUtility;
import me.shafishaz.mantlegambling.data.JackPotData;
import me.shafishaz.mantlegambling.task.JackPotTimer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class JackPotManager {

    MantleGambling plugin ;
    public LotteryManager<UUID> lottery;
    public static int money = 0;
    public static int total_tickets = 0;
    public static int ticket_price;
    public HashMap<String , Integer> tickets = new HashMap<>();
    public static JackPotTimer timer;

    public JackPotManager(MantleGambling plugin){
        this.plugin = plugin;
        this.lottery = new LotteryManager<>();
        ticket_price = plugin.getConfig().getInt("Jackpot.Ticket_Price");
        timer = new JackPotTimer(this , plugin.getConfig().getInt("jackpot.draw-time") , plugin.getConfig().getIntegerList("jackpot.warnings"));
        timer.runTaskTimerAsynchronously(plugin , 0 , 20);
    }
    public void awardWinner() {
        if (!lottery.isEmpty()) {
            int prize = money ;
            String prizeString = MantleGambling.formatter.format(prize);
            OfflinePlayer player = Bukkit.getOfflinePlayer(lottery.getRandom());
            List<String> msg = JackPotMessageUtility.parseResults(prizeString, player.getName());
            for (String line : msg) {
                Bukkit.broadcastMessage(line);
            }
            MantleGambling.getEconomy().depositPlayer(player, prize);

            FileConfiguration fileConfiguration = JackPotData.getInstance().getJackPotData();
            List<String> jphistory;
            try {
                jphistory = fileConfiguration.getStringList("jphistory");

            }catch (NullPointerException e){
                jphistory = new ArrayList<>();
            }

            String encodedString = player.getName() +": §6$" + prizeString;

            jphistory.add(encodedString);

            fileConfiguration.set("jphistory" , jphistory);

            JackPotData.getInstance().saveJackPotData();


        }
        money = 0;
        total_tickets = 0;
        tickets.clear();
        lottery = new LotteryManager<>();
    }

    public void enterJackpot(Player player, int amount, int total) {
        MantleGambling.getEconomy().withdrawPlayer(player, total);
        lottery.addEntry(player.getUniqueId(), amount);
        money += total;
        total_tickets += amount;
        if (tickets.containsKey(player.getUniqueId().toString())) {
            int oldAmount = tickets.get(player.getUniqueId().toString());
            tickets.put(player.getUniqueId().toString(), oldAmount + amount);
        } else {
            tickets.put(player.getUniqueId().toString(), amount);
        }
        player.sendMessage(JackPotMessageUtility.parseBuyMessage("buy-ticket", amount));
    }

}
