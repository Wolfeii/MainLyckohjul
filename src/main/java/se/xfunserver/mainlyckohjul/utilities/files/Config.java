package se.xfunserver.mainlyckohjul.utilities.files;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import se.xfunserver.mainlyckohjul.LuckyWheel;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config extends YamlConfiguration {

    private final LuckyWheel luckyWheel;
    private final File file;
    private final String defaults;

    public Config(LuckyWheel luckyWheel, String fileName, String defaultsName) {
        this.luckyWheel = luckyWheel;
        this.file = new File(luckyWheel.getDataFolder(), fileName);
        this.defaults = defaultsName;
    }

    public boolean createIfNotExists() {
        if (file.exists()) {
            return true;
        }

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            load(file);
            if (defaults != null) {
                InputStreamReader reader = new InputStreamReader(luckyWheel.getResource(defaults));
                FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);

                setDefaults(defaultsConfig);
                options().copyDefaults(true);

                reader.close();
                save();
            }
        } catch (IOException | InvalidConfigurationException exception) {
            return false;
        }

        return true;
    }

    public boolean save() {
        try {
            options().indent(2);
            save(file);
        } catch (IOException exception) {
            return false;
        }

        return true;
    }

    public boolean reload() {
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            return false;
        }

        return true;
    }
}
