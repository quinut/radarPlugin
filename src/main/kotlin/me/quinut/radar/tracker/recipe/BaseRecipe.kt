package me.quinut.radar.tracker.recipe

import me.quinut.radar.tracker.BaseTrackerItem
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

abstract class BaseRecipe(val plugin: JavaPlugin){
    open val key = NamespacedKey(plugin, "base_recipe")
    abstract val item: BaseTrackerItem

    open fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(key, item.create())


        return recipe
    }

    fun register() {
        plugin.server.addRecipe(getRecipe())
    }





}