package me.quinut.radar.armor

import me.quinut.radar.BaseItem
import java.util.UUID
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.attribute.AttributeModifier.Operation
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

class RollerSkateItem(plugin: JavaPlugin) : BaseItem(plugin, "Roller Skate") {
    override val itemKey = NamespacedKey(plugin, "RollerSkateItem")

    override val loreList = listOf(
        Component.text("롤러 스케이트입니다."),
        Component.text("특수 효과:").color(NamedTextColor.GREEN),
        Component.text("이동 속도가 증가합니다.").color(NamedTextColor.YELLOW)
    )

    override fun create(): ItemStack {
        val item = ItemStack(Material.IRON_BOOTS)
        val meta = item.itemMeta
        meta.displayName(Component.text(name))
        meta.lore(lore)

        val speedModifier = AttributeModifier(UUID.randomUUID(), "MovementSpeedModifier", 0.4, Operation.ADD_SCALAR, EquipmentSlot.FEET)

        meta.addAttributeModifier(Attribute.MOVEMENT_SPEED, speedModifier)

        meta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, itemKey.key)


        item.itemMeta = meta

        return item
    }

    init {
        // Additional initialization if needed
    }
}