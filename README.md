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
</dependency>
```
----
