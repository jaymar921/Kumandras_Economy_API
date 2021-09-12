package me.jaymar921.kumandraseconomy.Version;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import org.bukkit.ChatColor;

public class VersionChecker {
	
	private boolean support_1_17 = false;
	private boolean support_1_16 = false;
	
	public VersionChecker(String version) {
		update(version);
	}
	
	private void update(String version) {
		if(version.contains("1.17") || version.contains("1.17.1")) {
			support_1_17 = true;
			support_1_16 = true;
		}
		if(version.contains("1.16")) {
			support_1_16 = true;
		}
		
		
		
		if(!support_1_17)
			KumandrasEconomy.getPlugin(KumandrasEconomy.class).getLogger().info(ChatColor.GREEN+"Supports 1.17");
	}
	
	
	public boolean support_1_17() {
		return support_1_17;
	}
	public boolean support_1_16(){return support_1_16;}
	

}
