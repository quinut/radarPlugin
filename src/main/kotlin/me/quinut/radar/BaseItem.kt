package me.quinut.radar

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

open class BaseItem(
    val plugin: JavaPlugin,
    open val name: String,
) {
    open val itemKey = NamespacedKey(plugin, "BastArmorItem")

    open val loreList = listOf(
        Component.text("아이템입니다."),
        Component.text("특수 효과:").color(NamedTextColor.GREEN),
    )
    val lore = loreList

    open fun create(): ItemStack {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.displayName(Component.text(name))
        meta.lore(lore)

        meta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, itemKey.key)


        item.itemMeta = meta

        return item

    }


    }