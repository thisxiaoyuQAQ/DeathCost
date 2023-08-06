package xiaoyu;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        Plugin plugin = DeathCost.getPlugin(DeathCost.class);
        CommandSender p = commandSender;
        if (strings.length == 0){
            p.sendMessage("=====DeathCost=====");
            p.sendMessage("/DeathCost reload 重载插件");
            //p.sendMessage("/tax top 交税榜");
        }
        if (commandSender != null && commandSender.hasPermission("DeathCost.reload"))
            if (strings.length == 1 && strings[0].equals("reload")) {
                plugin.reloadConfig();
                commandSender.sendMessage("§a[DeathCost]重载成功");
            }
        return false;
    }
}