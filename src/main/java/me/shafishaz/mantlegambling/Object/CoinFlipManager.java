package me.shafishaz.mantlegambling.Object;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.task.CoinFlipInitialTimer;
import me.shafishaz.mantlegambling.task.CoinFlipTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoinFlipManager {
    public Player better;
    public Player opponent;
    public double amount;
    public Inventory inventory;
    public ItemStack    betterpicked;
    public ItemStack opponentpicked;

    public static List<ItemStack> wools = new ArrayList<>();

    public CoinFlipManager(Player better , double amount){
        this.better = better;
        this.amount = amount;

        better.openInventory(getbetterchooseInv());

    }

    public Inventory getbetterchooseInv(){
        Inventory inventory = Bukkit.createInventory(better , 9 , "Pick a colour");

        ItemStack white_wool = new ItemStack(Material.WOOL , 1 , (short) 0 );
        ItemStack orange_wool = new ItemStack(Material.WOOL , 1 , (short) 1);
        ItemStack magenta_wool = new ItemStack(Material.WOOL , 1 , (short) 2);
        ItemStack yellow_wool = new ItemStack(Material.WOOL , 1 , (short) 4);
        ItemStack lime_wool = new ItemStack(Material.WOOL , 1 , (short) 5);
        ItemStack purple_wool = new ItemStack(Material.WOOL , 1 , (short) 10);
        ItemStack blue_wool = new ItemStack(Material.WOOL , 1 , (short) 11);
        ItemStack green_wool = new ItemStack(Material.WOOL , 1 , (short) 13);
        ItemStack red_wool = new ItemStack(Material.WOOL , 1 , (short) 14);

        ItemMeta woolmeta = white_wool.getItemMeta();
        woolmeta.setDisplayName("§f§l§nWhite");
        white_wool.setItemMeta(woolmeta);
        woolmeta = orange_wool.getItemMeta();
        woolmeta.setDisplayName("§6§l§nOrange");
        orange_wool.setItemMeta(woolmeta);
        woolmeta = magenta_wool.getItemMeta();
        woolmeta.setDisplayName("§5§l§nMagenta");
        magenta_wool.setItemMeta(woolmeta);
        woolmeta = yellow_wool.getItemMeta();
        woolmeta.setDisplayName("§e§l§nYellow");
        yellow_wool.setItemMeta(woolmeta);
        woolmeta = lime_wool.getItemMeta();
        woolmeta.setDisplayName("§a§l§nLime");
        lime_wool.setItemMeta(woolmeta);
        woolmeta = purple_wool.getItemMeta();
        woolmeta.setDisplayName("§d§l§nPurple");
        purple_wool.setItemMeta(woolmeta);
        woolmeta = blue_wool.getItemMeta();
        woolmeta.setDisplayName("§9§l§nBlue");
        blue_wool.setItemMeta(woolmeta);
        woolmeta = green_wool.getItemMeta();
        woolmeta.setDisplayName("§2§l§nGreen");
        green_wool.setItemMeta(woolmeta);
        woolmeta = red_wool.getItemMeta();
        woolmeta.setDisplayName("§c§l§nRed");
        red_wool.setItemMeta(woolmeta);


        wools.add(white_wool);
        wools.add(orange_wool);
        wools.add(magenta_wool);
        wools.add(yellow_wool);
        wools.add(lime_wool);
        wools.add(purple_wool);
        wools.add(blue_wool);
        wools.add(green_wool);
        wools.add(red_wool);


        inventory.setItem(0 , white_wool);
        inventory.setItem(1 , orange_wool);
        inventory.setItem(2 , magenta_wool);
        inventory.setItem(3 , lime_wool);
        inventory.setItem(6, yellow_wool);
        inventory.setItem(7, purple_wool);
        inventory.setItem(8, blue_wool);
        inventory.setItem(5, red_wool);
        inventory.setItem(4 , green_wool);

        return inventory;
    }

    public Inventory getopponetchooseInv(){
        Inventory inventory = Bukkit.createInventory(opponent , 9 , "Pick a colour -" + better.getName());

        ItemStack white_wool = new ItemStack(Material.WOOL , 1 , (short) 0 );
        ItemStack orange_wool = new ItemStack(Material.WOOL , 1 , (short) 1);
        ItemStack magenta_wool = new ItemStack(Material.WOOL , 1 , (short) 2);
        ItemStack yellow_wool = new ItemStack(Material.WOOL , 1 , (short) 4);
        ItemStack lime_wool = new ItemStack(Material.WOOL , 1 , (short) 5);
        ItemStack purple_wool = new ItemStack(Material.WOOL , 1 , (short) 10);
        ItemStack blue_wool = new ItemStack(Material.WOOL , 1 , (short) 11);
        ItemStack green_wool = new ItemStack(Material.WOOL , 1 , (short) 13);
        ItemStack red_wool = new ItemStack(Material.WOOL , 1 , (short) 14);

        ItemMeta woolmeta = white_wool.getItemMeta();
        woolmeta.setDisplayName("§f§l§nWhite");
        white_wool.setItemMeta(woolmeta);
        woolmeta = orange_wool.getItemMeta();
        woolmeta.setDisplayName("§6§l§nOrange");
        orange_wool.setItemMeta(woolmeta);
        woolmeta = magenta_wool.getItemMeta();
        woolmeta.setDisplayName("§5§l§nMagenta");
        magenta_wool.setItemMeta(woolmeta);
        woolmeta = yellow_wool.getItemMeta();
        woolmeta.setDisplayName("§e§l§nYellow");
        yellow_wool.setItemMeta(woolmeta);
        woolmeta = lime_wool.getItemMeta();
        woolmeta.setDisplayName("§a§l§nLime");
        lime_wool.setItemMeta(woolmeta);
        woolmeta = purple_wool.getItemMeta();
        woolmeta.setDisplayName("§d§l§nPurple");
        purple_wool.setItemMeta(woolmeta);
        woolmeta = blue_wool.getItemMeta();
        woolmeta.setDisplayName("§9§l§nBlue");
        blue_wool.setItemMeta(woolmeta);
        woolmeta = green_wool.getItemMeta();
        woolmeta.setDisplayName("§2§l§nGreen");
        green_wool.setItemMeta(woolmeta);
        woolmeta = red_wool.getItemMeta();
        woolmeta.setDisplayName("§c§l§nRed");
        red_wool.setItemMeta(woolmeta);




        inventory.setItem(0 , white_wool);
        inventory.setItem(1 , orange_wool);
        inventory.setItem(2 , magenta_wool);
        inventory.setItem(3 , lime_wool);
        inventory.setItem(6, yellow_wool);
        inventory.setItem(7, purple_wool);
        inventory.setItem(8, blue_wool);
        inventory.setItem(5, red_wool);
        inventory.setItem(4 , green_wool);

        int i = inventory.first(betterpicked);

        ItemStack itemStack = new ItemStack(Material.BARRIER );
        woolmeta = itemStack.getItemMeta();
        woolmeta.setDisplayName("§l§m§7Picked");

        inventory.setItem(i , itemStack);

        return inventory;
    }



    public Player getBetter() {
        return better;
    }

    public Player getOpponent() {
        return opponent;
    }

    public double getAmount() {
        return amount;
    }


    public void flipCoin(){

        new CoinFlipInitialTimer(this).runTaskTimer(MantleGambling.getInstance() , 0 , 20);

    }
}
