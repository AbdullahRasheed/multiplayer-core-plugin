package me.abdullah.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;

public class Updater {

    private Core core;
    private URL versionChecker;
    private URL downloadUrl;

    public Updater(Core core, String versionChecker, String downloadUrl) throws MalformedURLException {
        this.core = core;
        this.versionChecker = new URL(versionChecker);
        this.downloadUrl = new URL(downloadUrl);
    }

    public void start(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(core, () -> {
            try {
                String currentVersion = core.getDescription().getVersion();
                String releaseVersion = readText(versionChecker);

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

                    // TODO restart server
                }
            }catch (Exception e){
                Bukkit.getLogger().severe("ERROR WHILE CHECKING FOR UPDATE: " + e.getMessage());
            }
        }, 0, 20 * 60);
    }

    private String readText(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String s;
        while((s = reader.readLine()) != null){
            sb.append(s);
        }

        reader.close();
        return sb.toString();
    }
}
