package me.quinut.radar.commands

import me.quinut.radar.tracker.LightTrackerItem
import me.quinut.radar.tracker.NormalTrackerItem
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class GetTrackerCommand(private val plugin: JavaPlugin) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.")
            return true
        }

        if (!sender.isOp) {
            sender.sendMessage("이 명령어를 사용할 권한이 없습니다.")
            return true
        }

        if (args.isEmpty()) {
            val normalTracker = NormalTrackerItem(plugin).create()
            val lightTracker = LightTrackerItem(plugin).create()

            sender.inventory.addItem(normalTracker, lightTracker)
            sender.sendMessage("모든 트래커를 받았습니다.")
            return true
        }

        when (args[0].lowercase()) {
            "normal" -> {
                val normalTracker = NormalTrackerItem(plugin).create()
                sender.inventory.addItem(normalTracker)
                sender.sendMessage("노멀 트래커를 받았습니다.")
            }
            "light" -> {
                val lightTracker = LightTrackerItem(plugin).create()
                sender.inventory.addItem(lightTracker)
                sender.sendMessage("라이트 트래커를 받았습니다.")
            }
            else -> {
                sender.sendMessage("잘못된 트래커 이름입니다. normal 또는 light를 입력해주세요.")
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 1) {
            return mutableListOf("normal", "light")
        }
        return mutableListOf()
    }
}