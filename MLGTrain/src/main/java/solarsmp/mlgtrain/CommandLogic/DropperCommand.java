package solarsmp.mlgtrain.CommandLogic;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import solarsmp.mlgtrain.MLGTrain;

public class DropperCommand implements CommandExecutor, Listener {
    private final JavaPlugin plugin;

    public DropperCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("MLG.dropper")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("Messages.no-permission")));
            return false;
        } else if (args.length != 1) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("dropper.usage")));
            return true;
        } else {
            Player target = this.plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("messages.offline")));
                return true;
            } else {
                this.executeDropper(target);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MLGTrain.getInstance().getConfig().getString("dropper.executed")));
                return true;
            }
        }
    }

    private void executeDropper(Player player) {
        String message = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("dropper.message"));
        player.sendMessage(message);
        player.sendTitle("", message, 15, 90, 30);
        Location spawnLocation = player.getLocation().add(0.0, 7.0, 0.0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 160, 1));

        for (int x = -1; x <= 1; ++x) {
            for (int y = 0; y <= 1; ++y) {
                for (int z = -1; z <= 1; ++z) {
                    Location blockLocation = spawnLocation.clone().add((double) x, (double) y, (double) z);
                    blockLocation.getBlock().setType(Material.COBBLESTONE);
                    player.getWorld().spawnParticle(Particle.BLOCK_CRACK, blockLocation.add(0.5, 0.5, 0.5), 50, 0.2, 0.2, 0.2, Material.COBBLESTONE.createBlockData());
                }
            }
        }

        Location waterLocation = spawnLocation.clone().add(0.0, 1.0, 0.0);
        waterLocation.getBlock().setType(Material.WATER);
        Location tpLocation = waterLocation.clone().add(1.0, 110.0, 1.0);
        player.teleport(tpLocation);
        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            for (int x = -1; x <= 1; ++x) {
                for (int y = 0; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        Location blockLocation = spawnLocation.clone().add((double) x, (double) y, (double) z);
                        blockLocation.getBlock().setType(Material.AIR);
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, blockLocation.add(0.5, 0.5, 0.5), 50, 0.2, 0.2, 0.2, Material.COBBLESTONE.createBlockData());
                    }
                }
            }

            waterLocation.getBlock().setType(Material.AIR);
            player.getWorld().spawnParticle(Particle.WATER_SPLASH, waterLocation.add(0.5, 0.5, 0.5), 100, 0.5, 0.5, 0.5);
        }, 120L);
    }
}


