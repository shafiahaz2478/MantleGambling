package me.shafishaz.mantlegambling.data;

import me.shafishaz.mantlegambling.MantleGambling;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class JackPotData {
    private static final JackPotData ourInstance = new JackPotData();
    public static JackPotData getInstance() {
        return ourInstance;
    }
    private JackPotData() {
        this.JackPotDataFile = new File(MantleGambling.getInstance().getDataFolder(), "JackPotData.yml");
        if (!this.JackPotDataFile.exists()) {
            try {
                this.JackPotDataFile.getParentFile().mkdirs();
                this.JackPotDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.JackPotData = YamlConfiguration.loadConfiguration(this.JackPotDataFile);
    }

    private final File JackPotDataFile;
    private final FileConfiguration JackPotData;

    public FileConfiguration getJackPotData() {

        return JackPotData;
    }

    public void saveJackPotData() {
        try {
            this.JackPotData.save(this.JackPotDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
