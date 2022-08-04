package com.zal1000.cobigquery;

import com.google.cloud.bigquery.*;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MyPlugin extends JavaPlugin {


    public static void query(String query) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

            TableResult results = bigquery.query(queryConfig);

            results
                    .iterateAll()
                    .forEach(row -> row.forEach(val -> System.out.printf("%s,", val.toString())));

            System.out.println("Query performed successfully.");
        } catch (BigQueryException | InterruptedException e) {
            System.out.println("Query not performed \n" + e);
        }
    }

    // This code is called after the server starts and after the /reload command
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents((Listener) this, this);
        Plugin depend = Bukkit.getPluginManager().getPlugin("CoreProtect");
        if (depend == null) {
            getPluginLoader().disablePlugin(this);
            return;
        }
        CoreProtectAPI api = ((CoreProtect) depend).getAPI();

        String projectId = "MY_PROJECT_ID";
        String datasetName = "MY_DATASET_NAME";
        String tableName = "MY_TABLE_NAME";
        String query =
                "SELECT name, SUM(number) as total_people\n"
                        + " FROM `"
                        + projectId
                        + "."
                        + datasetName
                        + "."
                        + tableName
                        + "`"
                        + " WHERE state = 'TX'"
                        + " GROUP BY name, state"
                        + " ORDER BY total_people DESC"
                        + " LIMIT 20";
        query(query);
    }

  // This code is called before the server stops and after the /reload command
  @Override
  public void onDisable() {
    getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
  }
}
