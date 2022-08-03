package com.zal1000.cobigquery;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MyPlugin extends JavaPlugin {


  // This code is called after the server starts and after the /reload command
  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents((Listener) this, this);
    Plugin depend = Bukkit.getPluginManager().getPlugin("CoreProtect");
    // if (depend == null) {
    //     getPluginLoader().disablePlugin(this);
    //     return;
    // }
    CoreProtectAPI api = ((CoreProtect) depend).getAPI();
  }

  // This code is called before the server stops and after the /reload command
  @Override
  public void onDisable() {
    getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
  }
}
