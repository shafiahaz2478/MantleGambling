package me.shafishaz.mantlegambling;

import me.shafishaz.mantlegambling.Object.CoinFlipManager;
import me.shafishaz.mantlegambling.Object.JackPotManager;
import me.shafishaz.mantlegambling.commands.CoinFlipCommand;
import me.shafishaz.mantlegambling.commands.JackpotCommand;
import me.shafishaz.mantlegambling.listener.CoinFlipInventoryEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.HashMap;

public final class MantleGambling extends JavaPlugin {

    public HashMap<Player, CoinFlipManager> pendings = new HashMap<>();
    private static Economy econ = null;
    private static MantleGambling instance = null;
    public static DecimalFormat formatter;

    public JackPotManager jackPotManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        getLogger().info("Plugin has loaded");
        setupEconomy();

        getServer().getPluginCommand("CoinFlip").setExecutor(new CoinFlipCommand());
        getServer().getPluginCommand("Jackpot").setExecutor(new JackpotCommand());
        getServer().getPluginManager().registerEvents(new CoinFlipInventoryEvent() , this);

        formatter = new DecimalFormat("#,###");
        jackPotManager = new JackPotManager(this);
        instance=this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static MantleGambling getInstance() {
        return instance;
    }
}
