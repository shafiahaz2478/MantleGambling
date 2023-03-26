package me.shafishaz.mantlegambling.commands;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.CoinFlipManager;
import me.shafishaz.mantlegambling.Utitlity.CoinFlipMessageUtility;
import me.shafishaz.mantlegambling.data.CoinflipData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoinFlipCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player ) {
            Player player = (Player) sender;
            Inventory coinFlipinv = Bukkit.createInventory(null, 9, "Coin Flip");
            if (strings.length == 2) {
                if(strings[0].equalsIgnoreCase("create")) {
                    double amount = Integer.parseInt(strings[1]);
                    double playereconomy = MantleGambling.getEconomy().getBalance(player);
                    if (amount >= playereconomy) {
                        player.sendMessage("§c§l(!) §cYou dont have enough money");
                    }else {
                        CoinFlipManager coinFlipManager = new CoinFlipManager(player,  amount);
                        MantleGambling.getInstance().pendings.put(player, coinFlipManager);


                    }
                }

            } else if (strings.length == 1) {
                if(strings[0].equalsIgnoreCase("stats")){

                    FileConfiguration fileConfiguration = CoinflipData.getInstance().getCoinFlipdata();

                    int win = 0;
                    int lost = 0;

                    try{
                        win = fileConfiguration.getInt("coinflip." + player.getUniqueId().toString() + ".win");
                    }catch (NullPointerException ignored){}
                    try{
                        lost = fileConfiguration.getInt("coinflip." + player.getUniqueId().toString() + ".lost");
                    }catch (NullPointerException ignored){}


                    List<String> msg = CoinFlipMessageUtility.parsestats(win , lost);

                    for (String line : msg) {
                        player.sendMessage(line);
                    }

                }
            }else if (strings.length == 0){
                List<ItemStack> playerheads = new ArrayList<>();
                for(Map.Entry<Player , CoinFlipManager> entry : MantleGambling.getInstance().pendings.entrySet()){
                    if(playerheads.size() >=9){
                        break;
                    }
                    if(entry.getKey() != player){
                        List<String> lore = new ArrayList<>();
                        ItemStack playerhead = new ItemStack(Material.SKULL_ITEM , 1 , (short) 3);
                        SkullMeta meta = (SkullMeta) playerhead.getItemMeta();
                        meta.setOwner(entry.getKey().getName());
                        lore.add("§6$" + MantleGambling.formatter.format(entry.getValue().amount));
                        meta.setLore(lore);
                        playerhead.setItemMeta(meta);
                        playerheads.add(playerhead);
                    }
                }
                coinFlipinv.setContents(playerheads.toArray(new ItemStack[0]));
                player.openInventory(coinFlipinv);

            }
        }


        return false;
    }
}
