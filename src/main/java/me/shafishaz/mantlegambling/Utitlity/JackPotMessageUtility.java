package me.shafishaz.mantlegambling.Utitlity;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.JackPotManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class JackPotMessageUtility {
    public static List<String> parseResults(String prize, String winner) {
        List<String> msg = MantleGambling.getInstance().getConfig().getStringList("messages.jackpot-results");
        List<String> parsedMsg = new ArrayList<String>();
        for (String line : msg) {
            line = line.replace("{prize}", prize);
            line = line.replace("{winner}", winner);
            line = line.replace("&", "§");
            parsedMsg.add(line);
        }
        return parsedMsg;
    }

    public static List<String> parseInfo(String totalValue, String tax, long totalTickets, long playerTickets, int playerPercent) {
        List<String> msg = MantleGambling.getInstance().getConfig().getStringList("messages.jackpot-info");
        List<String> parsedMsg = new ArrayList<String>();
        for (String line : msg) {
            line = line.replace("{total-value}", totalValue);
            line = line.replace("{tax}", tax);
            line = line.replace("{total-tickets}", MantleGambling.formatter.format(totalTickets));
            line = line.replace("{player-tickets}", MantleGambling.formatter.format(playerTickets));
            line = line.replace("{player-percent}", String.valueOf(playerPercent));
            line = line.replace("{timer}", JackPotManager.timer.getTime());
            line = line.replace("&", "§");
            parsedMsg.add(line);
        }
        return parsedMsg;
    }

    public static List<String> parseWarning() {
        List<String> msg = MantleGambling.getInstance().getConfig().getStringList("messages.jackpot-warning");
        List<String> parsedMsg = new ArrayList<String>();
        String money = MantleGambling.formatter.format(JackPotManager.money);
        for (String line : msg) {
            line = line.replace("{total-value}", money);
            line = line.replace("{timer}", JackPotManager.timer.getShortTime());
            line = line.replace("&", "§");
            parsedMsg.add(line);
        }
        return parsedMsg;
    }

    public static String parseBuyMessage(String section, long amt) {
        String msg = MantleGambling.getInstance().getConfig().getString("messages." + section);
        msg = msg.replace("{amount}", String.valueOf(amt));
        msg = msg.replace("{price}", MantleGambling.formatter.format(MantleGambling.getInstance().getConfig().getInt("jackpot.ticket-price") * amt));
        msg = msg.replace("&", "§");
        return msg;
    }

    public static String parseGUIMessage(String section, long amt, long price) {
        String msg = MantleGambling.getInstance().getConfig().getString("confirm-gui." + section);
        msg = msg.replace("{amount}", MantleGambling.formatter.format(amt));
        msg = msg.replace("{price}", MantleGambling.formatter.format(price));
        msg = msg.replace("&", "§");
        return msg;
    }

    public static ItemStack parseItem(String button, String amt, String price) {
        String material = MantleGambling.getInstance().getConfig().getString("confirm-gui." + button + ".material");
        String name = MantleGambling.getInstance().getConfig().getString("confirm-gui." + button + ".name");
        List<String> lore = MantleGambling.getInstance().getConfig().getStringList("confirm-gui." + button + ".lore");

        name = name.replace("&","§" );
        name = name.replace("{amount}", amt);
        name = name.replace("{price}", price);

        List<String> parsedLore = new ArrayList<String>();
        for (String line : lore) {
            line = line.replace("{amount}", amt);
            line = line.replace("{price}", price);
            line = line.replace("&", "§");
            parsedLore.add(line);
        }
        ItemStack item = new ItemStack(Material.AIR);
        try {
            item = new ItemStack(Material.valueOf(material));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            meta.setLore(parsedLore);
            item.setItemMeta(meta);
            return item;
        } catch (IllegalArgumentException ignored) { }
        return item;
    }

    public static int getJackpotValue(String section) {
        return MantleGambling.getInstance().getConfig().getInt("jackpot." + section);
    }

    public static double getJackpotDouble(String section) {
        return MantleGambling.getInstance().getConfig().getDouble("jackpot." + section);
    }

    public static boolean getJackpotBoolean(String section) { return MantleGambling.getInstance().getConfig().getBoolean("jackpot." + section); }
}
