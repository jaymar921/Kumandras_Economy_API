package me.jaymar921.kumandraseconomy.datahandlers.Configurations;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataConfigUpdater {
    List<String> data = new LinkedList<String>();
    public void getBackup(String plugin_name, String file_name) {
        try {
            File file = new File("plugins/"+plugin_name+"/"+file_name);
            FileWriter wr = new FileWriter("plugins/"+plugin_name+"/old_"+file_name);

            if(file.exists()) {
                Scanner Reader = new Scanner(file);
                while (Reader.hasNextLine()) {
                    data.add(Reader.nextLine());
                }
                Reader.close();
            }

            StringBuffer sb = new StringBuffer();
            for(String content : data)
                sb.append(content).append("\n");
            wr.write(sb.toString());
            wr.close();
        }catch(Exception e) {
            KumandrasEconomy.getPlugin(KumandrasEconomy.class).getLogger().info("File not found :/");
        }

    }
    public void loadConfig(String plugin_name, String file_name) {
        try {
            File config_yml = new File("plugins/"+plugin_name+"/"+file_name);
            File config_old_yml = new File("plugins/"+plugin_name+"/old_"+file_name);

            List<String> data_2 = new ArrayList<>();
            //Copy the data from the old_config
            if(config_old_yml.exists()) {
                Scanner Reader = new Scanner(config_old_yml);
                while (Reader.hasNextLine()) {
                    data.add(Reader.nextLine());
                }
                Reader.close();
            }
            //Copy the data from the new config
            if(config_yml.exists()) {
                Scanner Reader = new Scanner(config_yml);
                while (Reader.hasNextLine()) {
                    data_2.add(Reader.nextLine());
                }
                Reader.close();
            }

            StringBuffer sb = new StringBuffer();
            List<String> c_data = new ArrayList<>();
            for(String newData : data_2){
                String[] content_data = newData.split(":");
                String copied ="";

                if(!c_data.contains(content_data[0]))
                    copied = newData;

                for(String oldData : data) {

                    String[] old_content_data = oldData.split(":");
                    if(newData.contains("Version"))
                        copied = newData;
                    if (old_content_data[0].equals(content_data[0])) {
                        if(oldData.equals(newData))
                            copied = newData;
                        else
                            copied = oldData;
                        //sb.append(oldData).append("\n");

                    }


                }
                c_data.add(copied);
            }
            for(String copied :c_data)
                sb.append(copied).append("\n");


            FileWriter wr = new FileWriter("plugins/"+plugin_name+"/"+file_name);
            wr.write(sb.toString());
            wr.close();
        }catch(Exception e) {
            KumandrasEconomy.getPlugin(KumandrasEconomy.class).getLogger().info("File not found :/");
        }

    }
}
