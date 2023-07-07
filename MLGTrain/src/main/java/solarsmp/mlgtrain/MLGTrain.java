package solarsmp.mlgtrain;

import org.bukkit.plugin.java.JavaPlugin;
import solarsmp.mlgtrain.CommandLogic.DropperCommand;
import solarsmp.mlgtrain.CommandLogic.FlatCommand;
import solarsmp.mlgtrain.CommandLogic.MLGcmd;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;



public class MLGTrain extends JavaPlugin implements Listener {
    private FileConfiguration config;
    private static MLGTrain instance;

    public MLGTrain() {
    }

    public void onEnable() {
        System.out.println("[MLG]:enable");
        this.getCommand("mlg").setExecutor(new MLGcmd(this));
        this.getCommand("dropper").setExecutor(new DropperCommand(this));
        this.getCommand("flat").setExecutor(new FlatCommand());
        this.saveDefaultConfig();
        this.config = this.getConfig();
        instance = this;
    }

    public void onDisable() {
        System.out.println("[MLG]:disabled");
    }

    public static MLGTrain getInstance() {
        return instance;
    }
}