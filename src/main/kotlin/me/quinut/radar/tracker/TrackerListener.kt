package me.quinut.radar.tracker

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class TrackerListener(val plugin: JavaPlugin) : Listener {

    val normalTrackerKey = NormalTrackerItem(plugin).itemKey
    val lightTrackerKey = LightTrackerItem(plugin).itemKey

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val user = event.player
        val isNormalTracker = user.inventory.itemInMainHand.persistentDataContainer.has(normalTrackerKey,
            PersistentDataType.STRING)
        val isLightTracker = user.inventory.itemInMainHand.persistentDataContainer.has(lightTrackerKey,
            PersistentDataType.STRING)
        val normalTracker = NormalTrackerItem(plugin)
        val lightTracker = LightTrackerItem(plugin)

        if (isNormalTracker) {
            normalTracker.onUse(user, user.inventory.itemInMainHand)
        }
        if (isLightTracker) {
            lightTracker.onUse(user, user.inventory.itemInMainHand)
        }





    }
}