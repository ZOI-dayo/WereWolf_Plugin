package net.zoizoi.plugin.werewolf.Command.SubCommand.SubCommands;

import net.zoizoi.plugin.werewolf.Game.GameManager;
import net.zoizoi.plugin.werewolf.Main;
import net.zoizoi.plugin.werewolf.System.PluginConfig;
import net.zoizoi.plugin.werewolf.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class joinSubCommand {
    public boolean OnCommand(Player player, Command command, String label, String[] args, Main plugin, GameManager gameManager, int GameID) {
        if (!gameManager.getGame(GameID).isReady) {
            if (gameManager.getGame(GameID).AddPlayer(player)) {
                for (Player p : player.getWorld().getPlayers()) {
                    p.sendMessage(ChatColor.YELLOW + "ゲームに" + player.getName() + "さんが参加しました");
                }
                for (PotionEffect PE :
                        player.getActivePotionEffects()) {
                    PotionEffectType PET = PE.getType();
                    player.removePotionEffect(PET);
                }
                player.setPlayerListName("");

                player.getInventory().clear();
                player.sendMessage("人狼ゲームに参加しました");
                TextUtils.sendHoverText(player, ChatColor.RED + "＞＞＞このメッセージを押してキャンセル＜＜＜", "人狼ゲームから離脱する", "/wolf cancel");
                player.sendMessage("ゲームの開始を待っています");
                player.setPlayerListName(ChatColor.GREEN + player.getName());
                player.sendTitle("ゲームに参加しました", "ゲームの開始を待っています", 10, 50, 10);
                player.setGameMode(GameMode.ADVENTURE);
                Location Lobby = new Location(player.getWorld(),
                        PluginConfig.config.getDouble("Location.Lobby.x"),
                        PluginConfig.config.getDouble("Location.Lobby.y"),
                        PluginConfig.config.getDouble("Location.Lobby.z"));
                player.teleport(Lobby);
            } else {
                player.sendMessage("既に参加しています");
            }
        } else {
            player.sendMessage("ゲームが開始されているので参加できません");
        }
        return true;
    }
}
