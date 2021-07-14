package net.mctechnic.discordinviter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandVerify implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (label.equalsIgnoreCase("verify")) {
			if (sender instanceof Player) {   //console
				if (sender.isOp()) {
					sender.sendMessage(Component.text("Please run this command from the console").color(NamedTextColor.RED));
				} else {
					sender.sendMessage(Component.text("You do not have permission to run this command. Please ask a staff member to be verified.").color(NamedTextColor.RED));
				}
			} else {
				if (args.length > 0) {
					for (String a : args) {
						Player p = Bukkit.getPlayer(a);
						if (p != null) {
							//TODO: check is p already is verified
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getUniqueId() + " promote verification");
							sender.sendMessage("Verified " + a + " (" + p.getUniqueId() + ")");
						} else {
							sender.sendMessage(Component.text("Player " + a + " not found").color(NamedTextColor.RED));
						}
					}
				}
			}
			return true;
		}
		return false;
	}
}
