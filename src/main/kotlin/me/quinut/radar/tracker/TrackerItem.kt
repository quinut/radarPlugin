package me.quinut.radar.tracker

import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin

class NormalTrackerItem(plugin: JavaPlugin) : BaseTrackerItem(plugin, "Normal Tracker",5, 30, false) {
    override val itemKey = NamespacedKey(plugin, "NormalTrackerItem")

}

class LightTrackerItem(plugin: JavaPlugin) : BaseTrackerItem(plugin, "Light Tracker", 3, 5, true) {
    override val itemKey = NamespacedKey(plugin, "LightTrackerItem")
}

