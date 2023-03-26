package me.shafishaz.mantlegambling.task;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.CoinFlipManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CoinFlipInitialTimer extends BukkitRunnable {

    CoinFlipManager coinFlipManager;
    Inventory inventory;

    int timer = 4;

    public CoinFlipInitialTimer(CoinFlipManager coinFlipManager){
        this.coinFlipManager = coinFlipManager;
        this.inventory = coinFlipManager.inventory;
    }

    @Override
    public void run() {
        if(timer == 1){
            inventory.setItem(4 , new ItemStack(Material.STAINED_GLASS_PANE , 1 , (short) 14));
            new CoinFlipTask(coinFlipManager).runTaskTimerAsynchronously(MantleGambling.getInstance() , 0 , 10);
            this.cancel();
            return;
        } else if (timer == 2 || timer == 3) {
            inventory.setItem(4 , new ItemStack(Material.STAINED_GLASS_PANE , timer, (short) 4));
        } else if (timer == 4) {
            inventory.setItem(4 , new ItemStack(Material.STAINED_GLASS_PANE , timer , (short) 13));
        }
        coinFlipManager.better.playSound(coinFlipManager.better.getLocation() , Sound.NOTE_PLING , 1f , 1f);
        coinFlipManager.opponent.playSound(coinFlipManager.opponent.getLocation() , Sound.NOTE_PLING , 1f , 1f);
        timer --;
    }
}
