package me.quinut.radar.tracker.recipe

import me.quinut.radar.armor.RollerSkateItem
import me.quinut.radar.tracker.LightTrackerItem
import me.quinut.radar.tracker.NormalTrackerItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.inventory.ShapedRecipe

class NormalTrackerRecipe(plugin: JavaPlugin) : BaseRecipe(plugin) {

    override val key: NamespacedKey = NamespacedKey(plugin, "normal_tracker_recipe")
    override val item = NormalTrackerItem(plugin)

    override fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(key, item.create())
        recipe.shape(
            "PIP",
            "ICI",
            "PIP"
        )
        recipe.setIngredient('I', Material.IRON_INGOT)
        recipe.setIngredient('C', Material.COMPASS)
        recipe.setIngredient('P', Material.COPPER_INGOT)
        return recipe
    }
}

class LightTrackerRecipe(plugin: JavaPlugin) : BaseRecipe(plugin) {

    override val key: NamespacedKey = NamespacedKey(plugin, "light_tracker_recipe")
    override val item = LightTrackerItem(plugin)

    override fun getRecipe(): ShapedRecipe {
        val recipe = ShapedRecipe(key, item.create())
        recipe.shape(
            "   ",
            "CRC",
            "   "
        )
        recipe.setIngredient('R', Material.REDSTONE)
        recipe.setIngredient('C', Material.COPPER_INGOT)
        return recipe
    }
}

class RollerSkateRecipe(private val plugin: JavaPlugin) {

    val key: NamespacedKey = NamespacedKey(plugin, "rollerskate_recipe")

    fun getRecipe(): ShapedRecipe {
        val item = RollerSkateItem(plugin)
        val recipe = ShapedRecipe(key, item.create())
        recipe.shape(
            "   ",
            " I ",
            "F F"
        )
        recipe.setIngredient('I', Material.IRON_BOOTS)
        recipe.setIngredient('F', Material.FLINT)
        return recipe
    }

    fun register() {
        plugin.server.addRecipe(getRecipe())
    }
}
