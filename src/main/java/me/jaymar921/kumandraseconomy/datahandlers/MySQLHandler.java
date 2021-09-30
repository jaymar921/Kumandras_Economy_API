package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import net.md_5.bungee.api.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MySQLHandler {
    static Connection connection;
    static boolean connected;
    public static boolean loadData(KumandrasEconomy main){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            main.getLogger().info(ChatColor.YELLOW+"Connecting to database...");

            String url = main.getMySQLConfig().getConfig().getString("URL");
            String database = main.getMySQLConfig().getConfig().getString("Database");
            String user = main.getMySQLConfig().getConfig().getString("User");
            String password = main.getMySQLConfig().getConfig().getString("Password");

            if(url==null)
                throw new Exception("URL should not be null");

            connection = DriverManager.getConnection(url,user,password);
            connected = true;
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            boolean found = false;
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                if(resultSet.getString(1).equalsIgnoreCase(database))
                    found = true;

            }

            if(!found)
                createDB(main, url, database, user, password);
            else
                loadDB(main,url,database,user,password);
            connection.close();
            return true;
        }catch (Exception error){
            main.getLogger().info(ChatColor.RED+"Failed to connect to Database...");
            main.getLogger().info(ChatColor.YELLOW+"Implementing local Database ["+ChatColor.DARK_GREEN+"PlayerData.yml"+ChatColor.YELLOW+"]");
            main.getLogger().info(ChatColor.AQUA+"Here is the Issue");
            main.getLogger().info(ChatColor.DARK_AQUA+error.getMessage());
            connected = false;
        }
        return false;
    }

    private static void createDB(KumandrasEconomy main, String url, String database, String user, String password){
        try{
            connection = DriverManager.getConnection(url,user,password);
            String query = "create database "+database;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch (Exception error){
            main.getLogger().info(ChatColor.RED+"Failed to create database ["+ChatColor.YELLOW+database+ChatColor.RED+"]");
            main.getLogger().info(ChatColor.YELLOW+"Implementing local database ["+ChatColor.DARK_GREEN+"PlayerData.yml"+ChatColor.YELLOW+"]");
            connected = false;
            return;
        }
        try{
            connection = DriverManager.getConnection(url+database,user,password);
            String query = "create table if not exists player_data(uuid varchar(100) primary key, balance double not null, jobs varchar(100) not null) engine = innodb";
            Statement statement = connection.createStatement();
            statement.execute(query);
        }catch (Exception error){
            main.getLogger().info(ChatColor.RED+"Failed to create table ["+ChatColor.YELLOW+"player_data"+ChatColor.RED+"]");
            main.getLogger().info(ChatColor.YELLOW+"Implementing local Database ["+ChatColor.DARK_GREEN+"PlayerData.yml"+ChatColor.YELLOW+"]");
            connected = false;
        }
        main.getLogger().info(ChatColor.YELLOW+"Created and implementing Database ["+ChatColor.DARK_GREEN+database+ChatColor.YELLOW+"]");
    }

    private static void loadDB(KumandrasEconomy main, String url, String database, String user, String password){
        try{
            connection = DriverManager.getConnection(url+database,user,password);
            String query = "select * from player_data";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Map<String, PlayerStatus> information = new HashMap<>();
            while(resultSet.next()){
                String uuid = resultSet.getString(1);
                double balance = resultSet.getDouble(2);
                String[] jobs = resultSet.getString(3).split(",");
                PlayerStatus status = new PlayerStatus(uuid, balance);
                for(String job : jobs)
                    if(job.length()>3)
                        status.getJobs().add(job);
                information.put(uuid,status);
            }
            main.getDataHandler().setStatusHolder(information);
        }catch (Exception error){
            main.getLogger().info(ChatColor.RED+"Failed to load Database ["+ChatColor.YELLOW+database+ChatColor.RED+"]");
            main.getLogger().info(ChatColor.YELLOW+"Implementing local Database ["+ChatColor.DARK_GREEN+"PlayerData.yml"+ChatColor.YELLOW+"]");
            connected =false;
            return;
        }

        main.getLogger().info(ChatColor.YELLOW+"Loaded Database ["+ChatColor.DARK_GREEN+database+ChatColor.YELLOW+"]");
    }

    public static boolean saveData(KumandrasEconomy main){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            main.getLogger().info(ChatColor.YELLOW+"Connecting to database...");

            String url = main.getMySQLConfig().getConfig().getString("URL");
            String database = main.getMySQLConfig().getConfig().getString("Database");
            String user = main.getMySQLConfig().getConfig().getString("User");
            String password = main.getMySQLConfig().getConfig().getString("Password");

            if(url==null)
                throw new Exception();

            connection = DriverManager.getConnection(url,user,password);
            connected = true;
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            boolean found = false;
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                if(resultSet.getString(1).equalsIgnoreCase(database))
                    found = true;

            }

            if(!found)
                createDB(main, url, database, user, password);
            else
                saveDB(main,url,database,user,password);
            connection.close();
            return true;
        }catch (Exception error){
            main.getLogger().info(ChatColor.RED+"Failed to connect to Database...");
            main.getLogger().info(ChatColor.YELLOW+"Implementing local Database ["+ChatColor.DARK_GREEN+"PlayerData.yml"+ChatColor.YELLOW+"]");
            connected = false;
        }
        return false;
    }
    private static void saveDB(KumandrasEconomy main, String url, String database, String user, String password){
        try{
            connection = DriverManager.getConnection(url+database,user,password);
            String query = "";
            Statement statement = connection.createStatement();

            Map<String,PlayerStatus> information = main.getDataHandler().getStatusHolder();

            for(String uuid : information.keySet()){
                if(information.get(uuid)==null)
                    continue;
                PlayerStatus status = information.get(uuid);
                double balance = status.getBalance();
                StringBuilder stringBuilder = new StringBuilder();
                for(String jobs : status.getJobs())
                    stringBuilder.append(jobs).append(",");
                String jobs = " ";
                if(stringBuilder.toString().length()>2)
                jobs = stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);


                if(statement.executeQuery("select * from player_data where uuid = '"+uuid+"';").next()){
                    query = "update player_data set balance="+balance+",jobs='"+jobs+"' where uuid='"+uuid+"'";
                    statement.execute(query);
                }
                else {
                    query = "insert into player_data (uuid,balance,jobs) values ('" + uuid + "', " + balance + ",'" + jobs + "')";
                    statement.executeUpdate(query);
                }

            }
        }catch (Exception error){
            main.getLogger().info(ChatColor.RED+"Failed to save Database ["+ChatColor.YELLOW+database+ChatColor.RED+"]");
            main.getLogger().info(ChatColor.YELLOW+"Implementing local Database ["+ChatColor.DARK_GREEN+"PlayerData.yml"+ChatColor.YELLOW+"]");
            connected =false;
            return;
        }

        main.getLogger().info(ChatColor.YELLOW+"Saved Database ["+ChatColor.DARK_GREEN+database+ChatColor.YELLOW+"]");
    }
}
