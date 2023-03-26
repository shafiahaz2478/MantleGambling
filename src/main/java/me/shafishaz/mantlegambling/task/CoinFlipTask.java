package me.shafishaz.mantlegambling.task;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.CoinFlipManager;
import me.shafishaz.mantlegambling.Utitlity.CoinFlipMessageUtility;
import me.shafishaz.mantlegambling.data.CoinflipData;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoinFlipTask extends BukkitRunnable {
    public CoinFlipManager coinFlipManager;
    int times = 0;

    List<ItemStack> green_glass_stains = new ArrayList<>();
    List<ItemStack> red_glass_stains = new ArrayList<>();

    Inventory inventory;

    ItemStack green_glass;
    ItemStack red_glass;

    public CoinFlipTask(CoinFlipManager coinFlipManager){
        this.coinFlipManager = coinFlipManager;

       green_glass = new ItemStack(Material.STAINED_GLASS_PANE , 1, (short) 13);
        for (int i = 0; i < 27; i++) {
            green_glass_stains.add(green_glass);
        }
        red_glass = new ItemStack(Material.STAINED_GLASS_PANE , 1, (short) 14);
        for (int i = 0; i < 27; i++) {
            red_glass_stains.add(red_glass);
        }

        this.inventory = coinFlipManager.inventory;

        inventory.setItem( 4 , coinFlipManager.betterpicked);
    }


    @Override
    public void run() {


        if(times >= 12){
            Random random = new Random();
            EconomyResponse r ;
            int randomint = random.nextInt(2);
            FileConfiguration fileConfiguration = CoinflipData.getInstance().getCoinFlipdata();
            if(randomint == 1){
                inventory.setItem(4 , coinFlipManager.betterpicked);
                coinFlipManager.better.playSound(coinFlipManager.better.getLocation() , Sound.NOTE_PLING , 1f , 1.2f);
                coinFlipManager.opponent.playSound(coinFlipManager.opponent.getLocation() , Sound.NOTE_PLING , 1f , 0.8f);
                r = MantleGambling.getEconomy().depositPlayer(coinFlipManager.better , coinFlipManager.amount);
                if(r.transactionSuccess()){
                    coinFlipManager.better.sendMessage(CoinFlipMessageUtility.parsewinnerMsg((int) coinFlipManager.amount));
                }
                r = MantleGambling.getEconomy().withdrawPlayer(coinFlipManager.opponent , coinFlipManager.amount);
                if(r.transactionSuccess()){
                    coinFlipManager.opponent.sendMessage(CoinFlipMessageUtility.parselooserMsg((int) coinFlipManager.amount));
                }
                try {
                    fileConfiguration.set( "coinflip." + coinFlipManager.better.getUniqueId().toString() + ".win" , fileConfiguration.getInt("coinflip." + coinFlipManager.better.getUniqueId().toString() + "." ) + 1);

                }catch (NullPointerException e){
                    fileConfiguration.set( "coinflip." + coinFlipManager.better.getUniqueId().toString() + ".win" , 1 );
                    fileConfiguration.set( "coinflip." + coinFlipManager.better.getUniqueId().toString() + ".lost" , 0 );
                }
                try {
                    fileConfiguration.set( "coinflip." + coinFlipManager.opponent.getUniqueId().toString() + ".lost" , fileConfiguration.getInt("coinflip." + coinFlipManager.opponent.getUniqueId().toString() + "." ) + 1);

                }catch (NullPointerException e){
                    fileConfiguration.set( "coinflip." + coinFlipManager.opponent.getUniqueId().toString() + ".win" , 0 );
                    fileConfiguration.set( "coinflip." + coinFlipManager.opponent.getUniqueId().toString() + ".lost" , 1 );
                }
            }else {
                inventory.setItem(4 , coinFlipManager.opponentpicked);
                coinFlipManager.opponent.playSound(coinFlipManager.opponent.getLocation() , Sound.NOTE_PLING , 1f , 1.2f);
                coinFlipManager.better.playSound(coinFlipManager.better.getLocation() , Sound.NOTE_PLING , 1f , 0.8f);
                r = MantleGambling.getEconomy().depositPlayer(coinFlipManager.opponent , coinFlipManager.amount);
                if(r.transactionSuccess()){
                    coinFlipManager.opponent.sendMessage(CoinFlipMessageUtility.parsewinnerMsg((int) coinFlipManager.amount));
                }
                r = MantleGambling.getEconomy().withdrawPlayer(coinFlipManager.better , coinFlipManager.amount);
                if(r.transactionSuccess()){
                    coinFlipManager.better.sendMessage(CoinFlipMessageUtility.parselooserMsg((int) coinFlipManager.amount));
                }
                try {
                    fileConfiguration.set( "coinflip." + coinFlipManager.opponent.getUniqueId().toString() + ".win" , fileConfiguration.getInt("coinflip." + coinFlipManager.opponent.getUniqueId().toString() + "." ) + 1);

                }catch (NullPointerException e){
                    fileConfiguration.set( "coinflip." + coinFlipManager.opponent.getUniqueId().toString() + ".win" , 1 );
                    fileConfiguration.set( "coinflip." + coinFlipManager.opponent.getUniqueId().toString() + ".lost" , 0 );
                }
                try {
                    fileConfiguration.set( "coinflip." + coinFlipManager.better.getUniqueId().toString() + ".lost" , fileConfiguration.getInt("coinflip." + coinFlipManager.better.getUniqueId().toString() + "." ) + 1);

                }catch (NullPointerException e){
                    fileConfiguration.set( "coinflip." + coinFlipManager.better.getUniqueId().toString() + ".win" , 0 );
                    fileConfiguration.set( "coinflip." + coinFlipManager.better.getUniqueId().toString() + ".lost" , 1 );
                }
            }

            CoinflipData.getInstance().saveCoinFlipdata();



            MantleGambling.getInstance().pendings.remove(coinFlipManager.better);
            MantleGambling.getInstance().pendings.remove(coinFlipManager.better , coinFlipManager);

            this.cancel();
            return;
        }

        if(inventory.getItem(4).equals(coinFlipManager.betterpicked)){
            inventory.setItem(4 , coinFlipManager.opponentpicked );
        }else if(inventory.getItem(4).equals(coinFlipManager.opponentpicked)){
            inventory.setItem(4 , coinFlipManager.betterpicked );
        }

        times++;
    }
}
