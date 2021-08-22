## Kumandra's Economy Plugin

### by JayMar921

> Plugin Version: 1.0

### Description

This plugin adds an economy system in game, it has deposit/withdraw/pay system which is common in economy plugins. The plugin does not just limit on adding currency in game, it also has its own trading systems and delivery system which enhances the item transportation with player interaction, This plugin soft depend on EssentialsX Economy means that it can run with or without this Essentials plugin.

### Kumandra's Economy API

For developers who want to use this plugin as an API, you can download the latest version of the plugin at spigot and import the Jar file into your project, or you can use maven or gradle dependency.

----
### Gradle
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
    repositories{
        ...
        maven{ url 'https://jitpack.io' }
    }
} 
```
Add the dependency
```gradle
dependencies {
    implementation 'com.github.jaymar921:Kumandras_Economy_Plugin:1.0' 	
}
```
----
### Maven
Repository
```maven
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the dependency
```maven
<dependency>
    <groupId>com.github.jaymar921</groupId>
    <artifactId>Kumandras_Economy_Plugin</artifactId>
    <version>1.0</version>
    <scope>provided</scope>
</dependency>
```
----

### How to implement the API

You need to get the Main class instance and then get the API instance from the plugin main

```java
//Get the instance of the main class

KumandrasEconomy kumandrasEconomy = Bukkit.getServer().getPluginManager().getPlugin("KumandrasEconomy");

//Get the API handler

KumandrasAPI kumandrasAPI = (KumandrasAPI) kumandrasEconomy.getAPI();

```

### How to use the API

Once you have successfully instantiated the KumandrasAPI, these are the methods useful for plugin interaction

```java
//Register your plugin to the API
kumandrasAPI.RegisterPlugin(@NotNull String pluginName); //Void
//e.g. kumandrasAPI.RegisterPlugin("CustomEnchantments"); 

//Get player balance
double balance = kumandrasAPI.getBalance(@NotNull player); //Double, returns null if player has no data

//Deposit money to player
kumandrasAPI.deposit(@NotNull Player player, double amount);//Boolean, returns true if amount is added to player data

//Withdraw money from player
kumandrasAPI.withdraw(@NotNull Player player, double amount);//Boolean, returns false if amount>balance or player has no data
```

