package xiaoyu;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathCost extends JavaPlugin {
    public static Economy economy;
    private static DeathCost instance;
    public static DeathCost getInstance() {return instance;}
    @Override
    public void onEnable() {
        instance =this;
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
        Metrics metrics = new xiaoyu.Metrics(this, 19406);
        this.getCommand("deathcost").setExecutor(new Command());
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        // 检查Vault是否可用
        if (!setupEconomy()) {
            getLogger().severe("Vault未找到或未配置经济插件");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getConsoleSender().sendMessage("§b================================");
        Bukkit.getConsoleSender().sendMessage("§b[DeathCost]死亡代价插件已加载");
        Bukkit.getConsoleSender().sendMessage("§b 作者:小雨        QQ:3066156386");
        Bukkit.getConsoleSender().sendMessage("§b   强烈建议添加反馈群:426996480");
        Bukkit.getConsoleSender().sendMessage("§b爱发电 https://afdian.net/@ixiaoyu");
        Bukkit.getConsoleSender().sendMessage("§b================================");
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§b[DeathCost]死亡代价插件已卸载");
    }
    }
