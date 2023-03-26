package me.shafishaz.mantlegambling.commands;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.JackPotManager;
import me.shafishaz.mantlegambling.Utitlity.JackPotMessageUtility;
import me.shafishaz.mantlegambling.data.JackPotData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class JackpotCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            JackPotManager jackPotManager = MantleGambling.getInstance().jackPotManager;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("buy") || args[0].equalsIgnoreCase("bet") || args[0].equalsIgnoreCase("place")) {
                    int amt = 1;
                    if (args.length >= 2) {
                        try {
                            amt = Integer.parseInt(args[1]);
                            if (amt < 1) { throw new NumberFormatException(); }
                        } catch (NumberFormatException e) {
                            player.sendMessage("§c§l(!) §c/jackpot buy <amount>");
                            return true;
                        }
                    }
                    int total = JackPotMessageUtility.getJackpotValue("ticket-price") * amt;
                    if (MantleGambling.getEconomy().getBalance(player) >= total) {
                       jackPotManager.enterJackpot(player, amt, total);

                    } else {
                        player.sendMessage(JackPotMessageUtility.parseBuyMessage("cannot-afford", amt));
                    }
                } else if (args[0].equalsIgnoreCase("history")) {
                    FileConfiguration fileConfiguration = JackPotData.getInstance().getJackPotData();

                    List<String> jphistory = fileConfiguration.getStringList("jphistory");

                    int jphistorycount ;
                    if(jphistory.size() < 10 ){
                        jphistorycount = jphistory.size();
                    }else {
                        jphistorycount = 10;
                    }

                    player.sendMessage("");
                    player.sendMessage("§e§lServer Jackpot");

                    for (int i = jphistory.size() ; i >= jphistory.size() - 9 ; i--) {
                        if(i  < 1){
                            break;
                        }


                        player.sendMessage("§b§l(!) §a" + jphistorycount + ": " + jphistory.get(i -1));

                        jphistorycount--;
                    }


                } else {
                    player.sendMessage("§c§l(!) §c/jackpot buy <amount>");
                }
            } else {
                int amount = 0;
                double percent = 0;
                if (jackPotManager.tickets.containsKey(player.getUniqueId().toString())) {
                    amount = jackPotManager.tickets.get(player.getUniqueId().toString());
                    percent = ((double) amount / JackPotManager.total_tickets) * 100;
                }
                String tax = String.valueOf((int) (JackPotMessageUtility.getJackpotDouble("tax-percent") * 100));
                List<String> msg = JackPotMessageUtility.parseInfo(
                        MantleGambling.formatter.format(JackPotManager.money), tax, JackPotManager.total_tickets, amount, (int) percent);
                for (String line : msg) {
                    player.sendMessage(line);
                }
            }
        }

        return false;
    }
}
