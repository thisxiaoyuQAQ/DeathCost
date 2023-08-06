package xiaoyu;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import static xiaoyu.DeathCost.economy;

public class Listener implements org.bukkit.event.Listener {
    static Plugin plugin = DeathCost.getInstance();
    ConfigurationSection min = plugin.getConfig().getConfigurationSection("min");
    ConfigurationSection permSection = plugin.getConfig().getConfigurationSection("permission");
    ConfigurationSection currentDefault = plugin.getConfig().getConfigurationSection("default");
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        //权限狗
        if (p.hasPermission("deathcost.bypass")){
            return;
        }

        boolean MinMoney = economy.getBalance(p) <= min.getDouble("money");
        boolean MinExp = p.getLevel()<= min.getInt("exp");
        double left = economy.getBalance(p);

        for (String keys : plugin.getConfig().getConfigurationSection("permission").getKeys(false)){
            ConfigurationSection currentSection = permSection.getConfigurationSection(keys);
            double exp = p.getLevel();
            double rate = 1 - currentSection.getDouble("exp")* 0.01;
            double cost = currentSection.getDouble("money")* 0.01 * economy.getBalance(p);

            if (p.hasPermission("deathcost."+keys)){
                p.sendMessage(currentSection.getString("msg").replaceAll("&","§").replaceAll("%money%", String.valueOf(left)).replaceAll("%exp%", String.valueOf(p.getLevel())));

                //穷比
                if (MinMoney){
                    p.sendMessage(min.getString("msgMoney").replaceAll("&","§").replaceAll("%money%", String.valueOf(left)).replaceAll("%exp%", String.valueOf(p.getLevel())));
                }else{
                    economy.withdrawPlayer(p,cost);
                }

                if (MinExp){
                    p.sendMessage(min.getString("msgExp").replaceAll("&","§").replaceAll("%money%", String.valueOf(left)).replaceAll("%exp%", String.valueOf(p.getLevel())));
                }else{
                    p.setLevel((int) (exp*rate));
                }
                return;
            }
        }

        //p.sendMessage(ChatColor.DARK_RED + "[debug]当前等级"+ p.getLevel());

        double cost = (currentDefault.getDouble("money")*0.01 * economy.getBalance(p));
        p.sendMessage(currentDefault.getString("msg").replaceAll("&","§").replaceAll("%money%", String.valueOf(left)).replaceAll("%exp%", String.valueOf(p.getLevel())));

        //穷比
        if (MinMoney){
            p.sendMessage(min.getString("msgMoney").replaceAll("&","§").replaceAll("%money%", String.valueOf(left)).replaceAll("%exp%", String.valueOf(p.getLevel())));
        }else{
            economy.withdrawPlayer(p,cost);
        }

        if (MinExp){
            p.sendMessage(min.getString("msgExp").replaceAll("&","§").replaceAll("%money%", String.valueOf(left)).replaceAll("%exp%", String.valueOf(p.getLevel())));
        }else{
            p.setLevel((int) (p.getLevel() * (1 - currentDefault.getDouble("exp")*0.01)));
        }


    }

}
