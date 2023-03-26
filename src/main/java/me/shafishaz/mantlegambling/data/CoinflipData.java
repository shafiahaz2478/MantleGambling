package me.shafishaz.mantlegambling.data;

import me.shafishaz.mantlegambling.MantleGambling;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CoinflipData {
    private static final CoinflipData ourInstance = new CoinflipData();
    public static CoinflipData getInstance() {
        return ourInstance;
    }
    private CoinflipData() {
        this.coinFlipdataFile = new File(MantleGambling.getInstance().getDataFolder(), "CoinFlipdata.yml");
        if (!this.coinFlipdataFile.exists()) {
            try {
                this.coinFlipdataFile.getParentFile().mkdirs();
                this.coinFlipdataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.coinFlipData = YamlConfiguration.loadConfiguration(this.coinFlipdataFile);
    }

    private final File coinFlipdataFile;
    private final FileConfiguration coinFlipData;

    public FileConfiguration getCoinFlipdata() {

        return coinFlipData;
    }

    public void saveCoinFlipdata() {
        try {
            this.coinFlipData.save(this.coinFlipdataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
