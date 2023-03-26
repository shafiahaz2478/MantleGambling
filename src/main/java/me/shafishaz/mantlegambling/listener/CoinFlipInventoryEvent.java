package me.shafishaz.mantlegambling.listener;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.CoinFlipManager;
import me.shafishaz.mantlegambling.Utitlity.CoinFlipMessageUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class CoinFlipInventoryEvent implements Listener {

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory().getTitle().equalsIgnoreCase("Picking")){
            event.setCancelled(true);
        }

        if (MantleGambling.getInstance().pendings.containsKey(player)) {event.setCancelled(true);}

        if (event.getClickedInventory().getTitle().startsWith("Pick a colour")) {
            event.setCancelled(true);
            if (MantleGambling.getInstance().pendings.containsKey(player)) {
                if (CoinFlipManager.wools.contains(event.getCurrentItem())) {
                    MantleGambling.getInstance().pendings.get(player).betterpicked = event.getCurrentItem();
                    Inventory inventory = Bukkit.createInventory(player, 9, "Waiting for the opponent to accept");

                    ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta meta = (SkullMeta) playerhead.getItemMeta();
                    meta.setOwner(player.getName());
                    playerhead.setItemMeta(meta);
                    ItemStack opponenthead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

                    ItemStack grey_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                    ItemMeta grey_glass_meta = grey_glass.getItemMeta();
                    grey_glass_meta.setDisplayName("§7 Waiting");
                    grey_glass.setItemMeta(grey_glass_meta);

                    inventory.setItem(0, playerhead);
                    inventory.setItem(1, event.getCurrentItem());
                    inventory.setItem(4, grey_glass);
                    inventory.setItem(7, new ItemStack(Material.BARRIER));
                    inventory.setItem(8, opponenthead);


                    MantleGambling.getInstance().pendings.get(player).inventory = inventory;
                    player.openInventory(inventory);

                }
            } else {
                String title = event.getClickedInventory().getTitle();

                String bettername = title.split("-")[1];
                Player better = Bukkit.getPlayer(bettername);



                MantleGambling.getInstance().pendings.get(better).opponentpicked = event.getCurrentItem();

                Inventory inventory = Bukkit.createInventory(better, 9, "Picking");
                ItemStack green_glass = new ItemStack(Material.STAINED_GLASS_PANE, 5, (short) 13);

                MantleGambling.getInstance().pendings.get(better).inventory = inventory;

                ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta meta = (SkullMeta) playerhead.getItemMeta();
                meta.setOwner(better.getName());
                playerhead.setItemMeta(meta);
                ItemStack opponenthead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                meta = (SkullMeta) opponenthead.getItemMeta();
                meta.setOwner(player.getName());
                opponenthead.setItemMeta(meta);

                inventory.setItem(0, playerhead);
                inventory.setItem(1, MantleGambling.getInstance().pendings.get(better).betterpicked);
                inventory.setItem(4, green_glass);
                inventory.setItem(7, event.getCurrentItem());
                inventory.setItem(8, opponenthead);
                player.openInventory(inventory);
                better.openInventory(inventory);
                MantleGambling.getInstance().pendings.get(better).flipCoin();



            }

        } else if (event.getClickedInventory().getTitle().equalsIgnoreCase("Coin Flip")) {
            ItemStack clickeditem = event.getCurrentItem();
            if (clickeditem.getType().equals(Material.SKULL_ITEM)) {
                SkullMeta skullMeta = (SkullMeta) clickeditem.getItemMeta();
                Player better = Bukkit.getPlayer(skullMeta.getOwner());
                MantleGambling.getInstance().pendings.get(better).opponent = player;

                player.openInventory(MantleGambling.getInstance().pendings.get(better).getopponetchooseInv());
//

            }
            event.setCancelled(true);
        }


    }


}
