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
	NamedTextColor textColour;
	String beforeLink;
	String linkText;
	String linkURL;
	String afterLink;
	FloodgateApi floodgateApi;

	@Override
	public void onEnable() {
		// Plugin startup logic
		config.options().header("textColour can be one of these: AQUA, BLACK, BLUE, DARK_AQUA, DARK_BLUE, DARK_GRAY, DARK_GREEN, DARK_PURPLE, DARK_RED, GOLD, GRAY, GREEN, LIGHT_PURPLE, RED, WHITE, YELLOW");
		config.addDefault("textColour", "AQUA");
		config.addDefault("beforeLink", "Join our");
		config.addDefault("linkText", "Discord Server");
		config.addDefault("linkURL", "https://discord.gg/AbC123d");
		config.addDefault("afterLink", "and read the rules to get access to Creative Mode!");
		config.options().copyDefaults(true);
		saveConfig();

		getServer().getPluginManager().registerEvents(this, this);
		textColour = getTextColour(config.getString("textColour"));
		beforeLink = config.getString("beforeLink");
		linkText = config.getString("linkText");
		linkURL = config.getString("linkURL");
		afterLink = config.getString("afterLink");

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
			String linkPart = linkText;
			if (floodgateApi != null && floodgateApi.isFloodgatePlayer(p.getUniqueId()))
				linkPart += " (" + linkURL + ")";

			TextComponent tc = Component.text(beforeLink + " ").color(textColour)
					.append(Component.text(linkPart, NamedTextColor.BLUE).decoration(TextDecoration.UNDERLINED, true)
							.hoverEvent(Component.text(linkURL, NamedTextColor.BLUE)))
					.clickEvent(ClickEvent.openUrl(linkURL))
					.append(Component.text(" " + afterLink, textColour));
			p.sendMessage(tc);
		}
	}

	private NamedTextColor getTextColour(String col) {
		return switch (col) {
			case "AQUA" -> NamedTextColor.AQUA;
			case "BLACK" -> NamedTextColor.BLACK;
			case "BLUE" -> NamedTextColor.BLUE;
			case "DARK_AQUA" -> NamedTextColor.DARK_AQUA;
			case "DARK_BLUE" -> NamedTextColor.DARK_BLUE;
			case "DARK_GRAY" -> NamedTextColor.DARK_GRAY;
			case "DARK_GREEN" -> NamedTextColor.DARK_GREEN;
			case "DARK_PURPLE" -> NamedTextColor.DARK_PURPLE;
			case "DARK_RED" -> NamedTextColor.DARK_RED;
			case "GOLD" -> NamedTextColor.GOLD;
			case "GRAY" -> NamedTextColor.GRAY;
			case "GREEN" -> NamedTextColor.GREEN;
			case "LIGHT_PURPLE" -> NamedTextColor.LIGHT_PURPLE;
			case "RED" -> NamedTextColor.RED;
			case "WHITE" -> NamedTextColor.WHITE;
			case "YELLOW" -> NamedTextColor.YELLOW;
			default -> null;
		};
	}
}
