package me.quinut.radar

import me.quinut.radar.commands.GetTrackerCommand
import me.quinut.radar.tracker.TrackerListener
import me.quinut.radar.tracker.recipe.LightTrackerRecipe
import me.quinut.radar.tracker.recipe.NormalTrackerRecipe
import me.quinut.radar.tracker.recipe.RollerSkateRecipe
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class Radar : JavaPlugin(), Listener {

    override fun onEnable() {
        // Plugin startup logic

        logger.info("Radar plugin has been enabled!")

        NormalTrackerRecipe(this).register()
        LightTrackerRecipe(this).register()
        RollerSkateRecipe(this).register()

        // Register the listener
        val trackerlistener = TrackerListener(this)
        server.pluginManager.registerEvents(trackerlistener, this)

        getCommand("gettracker")?.setExecutor(GetTrackerCommand(this))
        getCommand("gettracker")?.tabCompleter = GetTrackerCommand(this)


        for (player in server.onlinePlayers) {
            grantRecipes(player)
        }
    }

    override fun onDisable() {

    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        grantRecipes(event.player)
    }

    private fun grantRecipes(player: Player) {
        player.discoverRecipe(NamespacedKey(this, "normal_tracker_recipe"))
        player.discoverRecipe(NamespacedKey(this, "light_tracker_recipe"))
    }
}