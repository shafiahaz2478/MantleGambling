package me.shafishaz.mantlegambling.Utitlity;

import me.shafishaz.mantlegambling.MantleGambling;
import me.shafishaz.mantlegambling.Object.JackPotManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoinFlipMessageUtility {
    public static String parseInviteMsg(String playername){
        String parsedmsg = MantleGambling.getInstance().getConfig().getString("messages.coinflip-invite");
        parsedmsg = parsedmsg.replace("&" , "§");
        parsedmsg = parsedmsg.replace("{name}" , playername);
        return parsedmsg;
    }

    public static String parsewinnerMsg(int amount){
        String parsedmsg = MantleGambling.getInstance().getConfig().getString("messages.coinflip-winMsg");
        parsedmsg = parsedmsg.replace("&" , "§");
        parsedmsg = parsedmsg.replace("{prize}" , String.valueOf(amount));
        return parsedmsg;
    }
    public static String parselooserMsg(int amount){
        String parsedmsg = MantleGambling.getInstance().getConfig().getString("messages.coinflip-loseMsg");
        parsedmsg = parsedmsg.replace("&" , "§");
        parsedmsg = parsedmsg.replace("{prize}" , String.valueOf(amount));
        return parsedmsg;
    }
    public static List<String> parsestats(double win, double lost) {
        List<String> msg = MantleGambling.getInstance().getConfig().getStringList("messages.coinflip-stats");
        List<String> parsedMsg = new ArrayList<String>();
        double total = win + lost;
        double ratio = win / total;
        for (String line : msg) {
            line = line.replace("{win}", MantleGambling.formatter.format(win));
            line = line.replace("{lost}", MantleGambling.formatter.format(lost));
            line = line.replace("{total}", MantleGambling.formatter.format(total));
            line = line.replace("{ratio}", MantleGambling.formatter.format(ratio));
            line = line.replace("&", "§");
            parsedMsg.add(line);
        }
        return parsedMsg;
    }
}
