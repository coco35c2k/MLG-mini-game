package solarsmp.mlgtrain.CommandLogic;

import solarsmp.mlgtrain.MLGTrain;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solarsmp.mlgtrain.MLGTrain;

public class FlatCommand implements CommandExecutor {
    public FlatCommand() {
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MLGTrain.getInstance().getConfig().getString("Messages.console-error"));
            return true;
        } else if (!sender.hasPermission("MLG.flat")) {
            sender.sendMessage(MLGTrain.getInstance().getConfig().getString("Messages.no-permission").replace("&", "§"));
            return false;
        } else {
            Player player = (Player)sender;
            World world = player.getWorld();
            if (args.length < 3) {
                player.sendMessage(MLGTrain.getInstance().getConfig().getString("flat.usage").replace("&", "§"));
                return true;
            } else {
                int length;
                int width;
                try {
                    length = Integer.parseInt(args[0]);
                    width = Integer.parseInt(args[1]);
                } catch (NumberFormatException var15) {
                    player.sendMessage(MLGTrain.getInstance().getConfig().getString("flat.error1").replace("&", "§"));
                    return true;
                }

                if (length <= 100 && width <= 100) {
                    Material blockType = Material.matchMaterial(args[2]);
                    if (blockType == null) {
                        player.sendMessage(MLGTrain.getInstance().getConfig().getString("flat.error2").replace("&", "§"));
                        return true;
                    } else {
                        int startX = player.getLocation().getBlockX();
                        int startZ = player.getLocation().getBlockZ();
                        int startY = player.getLocation().getBlockY() - 1;

                        for(int x = startX; x < startX + length; ++x) {
                            for(int z = startZ; z < startZ + width; ++z) {
                                world.getBlockAt(x, startY, z).setType(blockType);
                            }
                        }

                        player.sendMessage(MLGTrain.getInstance().getConfig().getString("flat.executed").replace("&", "§"));
                        return true;
                    }
                } else {
                    player.sendMessage(MLGTrain.getInstance().getConfig().getString("flat.error3").replace("&", "§"));
                    return true;
                }
            }
        }
    }
}
