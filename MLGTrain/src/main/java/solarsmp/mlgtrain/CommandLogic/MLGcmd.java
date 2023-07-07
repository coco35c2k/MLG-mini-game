package solarsmp.mlgtrain.CommandLogic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import solarsmp.mlgtrain.MLGTrain;

public class MLGcmd implements CommandExecutor {
    private final JavaPlugin plugin;

    public MLGcmd(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void mlg(Player player) {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!sender.hasPermission("MLG.mlg")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("Messages.no-permission")));
            return false;
        } else if (args.length != 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("mlg.usage")));
            return true;
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("messages.offline")));
                return true;
            } else {
                this.executeMLG(player, target);
                return true;
            }
        }
    }

    private void executeMLG(Player sender, Player target) {
        Location location = target.getLocation().add(0.0, 200.0, 0.0);
        String message = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("mlg.message"));
        target.sendTitle("", message, 15, 90, 30);
        target.sendMessage(message);
        target.getInventory().addItem(new ItemStack[]{new ItemStack(Material.WATER_BUCKET)});

        // Remove water bucket after 7 seconds
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (target.getInventory().contains(Material.WATER_BUCKET)) {
                target.getInventory().remove(Material.WATER_BUCKET);
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("mlg.bucket-delete")));
            }
        }, 7 * 20);

        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 1));
        target.teleport(location);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("mlg.validation-message")));
    }
}

