package me.abdullah.core;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;

public class Updater {

    private Core core;
    private URL versionChecker;
    private URL downloadUrl;

    private int scheduleId;
    private int broadcastScheduleId;

    public Updater(Core core, String versionChecker, String downloadUrl) throws MalformedURLException {
        this.core = core;
        this.versionChecker = new URL(versionChecker);
        this.downloadUrl = new URL(downloadUrl);
    }

    public void start(){
        // TODO use an executorservice here instead (same for the broadcast task)
        this.scheduleId = Bukkit.getScheduler().runTaskTimerAsynchronously(core, () -> {
            try {
                String currentVersion = core.getDescription().getVersion();
                String releaseVersion = readVersion(versionChecker);

                if(!currentVersion.equals(releaseVersion)){
                    BufferedInputStream in = new BufferedInputStream(downloadUrl.openStream());
                    FileOutputStream out = new FileOutputStream(
                            "plugins" + System.getProperty("file.separator") + core.getName() + ".jar",
                            false
                    );

                    if(in == null || out == null) throw new IOException();

                    byte[] data = new byte[1024];
                    int count;
                    while((count = in.read(data, 0, 1024)) != -1){
                        out.write(data, 0, count);
                    }

                    in.close();
                    out.close();

                    // TODO noclassdeffound issue
                    broadcastScheduleId = Bukkit.getScheduler().runTaskTimer(core, new Runnable() {
                        int counter = 0;

                        @Override
                        public void run() {
                            int seconds = 120 - (15*counter);
                            Bukkit.broadcastMessage("UPDATE FOUND! Server restarting in " + seconds + " seconds");

                            if(seconds <= 0){
                                Bukkit.broadcastMessage("RESTARTING!!!");
                                Bukkit.getServer().spigot().restart();
                                Bukkit.getScheduler().cancelTask(broadcastScheduleId);
                                return;
                            }

                            counter++;
                        }
                    }, 0, 20 * 15).getTaskId();

                    Bukkit.getScheduler().cancelTask(scheduleId);
                }
            }catch (Exception e){
                Bukkit.getLogger().severe("ERROR WHILE CHECKING FOR UPDATE: " + e.getMessage());
            }
        }, 0, 20 * 60 * 5).getTaskId();
    }

    private String readVersion(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        Reader reader = new InputStreamReader(connection.getInputStream());

        FileConfiguration config = YamlConfiguration.loadConfiguration(reader);

        return config.getString("version");
    }
}
