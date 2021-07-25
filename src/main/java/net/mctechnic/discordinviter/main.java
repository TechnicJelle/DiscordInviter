package net.mctechnic.discordinviter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;

public final class main extends JavaPlugin implements Listener {

	FileConfiguration config = getConfig();
	String link;
	FloodgateApi floodgateApi;

	@Override
	public void onEnable() {
		// Plugin startup logic
		config.addDefault("link", "https://discord.gg/AbC123d");
		config.options().copyDefaults(true);
		saveConfig();

		getServer().getPluginManager().registerEvents(this, this);
		link = config.getString("link");

		if (Bukkit.getPluginManager().getPlugin("floodgate") != null) {
			getLogger().info("Found floodgate!");
			floodgateApi = FloodgateApi.getInstance();
		}

		getLogger().info("Discord invitation message plugin enabled!");
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		getLogger().info("Discord invitation message plugin disabled!");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (p.getGameMode() == GameMode.SPECTATOR) {
			String linkPart = "Discord channel";
			if (floodgateApi != null && floodgateApi.isFloodgatePlayer(p.getUniqueId()))
				linkPart += " (" + link + ")";

			TextComponent tc = Component.text("Join our ").color(NamedTextColor.AQUA)
					.append(Component.text(linkPart, NamedTextColor.BLUE).decoration(TextDecoration.UNDERLINED, true)
							.hoverEvent(Component.text(link, NamedTextColor.BLUE)))
					.clickEvent(ClickEvent.openUrl(link))
					.append(Component.text(" and read the rules to get access to Creative Mode!", NamedTextColor.AQUA));
			p.sendMessage(tc);
		}
	}
}
