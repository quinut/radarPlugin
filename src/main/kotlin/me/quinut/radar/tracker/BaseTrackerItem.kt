package me.quinut.radar.tracker

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

open class BaseTrackerItem(
    val plugin: JavaPlugin,
    val name: String,
    val use: Int,
    val time: Int,
    val showDistance: Boolean
) {

    open val itemKey = NamespacedKey(plugin, "BaseTrackerItem")
    open val useKey = NamespacedKey(plugin, "TrackerUse")
    open val usingKey = NamespacedKey(plugin, "TrackerUsing")
    val loreList = listOf(
        Component.text("트래커입니다. 다른 플레이어를 추적합니다."),
        Component.text("사용 횟수: ${use}회").color(NamedTextColor.GREEN),
    )
    val lore = loreList


    fun create(): ItemStack {
        val item = ItemStack(Material.COMPASS)
        val meta = item.itemMeta
        meta.displayName(Component.text(name))
        meta.lore(lore)
        meta.setMaxStackSize(1)

        meta.persistentDataContainer.set(itemKey, PersistentDataType.STRING, itemKey.key)
        meta.persistentDataContainer.set(useKey, PersistentDataType.INTEGER, use)
        meta.persistentDataContainer.set(usingKey, PersistentDataType.BOOLEAN, false)

        item.itemMeta = meta

        return item

    }


    open fun onUse(user: Player, item: ItemStack) {


        val useCount = item.itemMeta.persistentDataContainer.get(useKey, PersistentDataType.INTEGER)
        val isUsing = item.itemMeta.persistentDataContainer.get(usingKey, PersistentDataType.BOOLEAN)
        user.playSound(user.location, "block.note_block.bell", 1f, 1f)


        if (useCount!! <= 0) {
            user.sendActionBar(Component.text("더 이상 사용할 수 없습니다").color(NamedTextColor.RED))
            user.playSound(user.location, "minecraft:block.note_block.pling", 1f, 0.5f)
            return

        }

        if (isUsing == true) {
            user.sendActionBar(Component.text("이미 추적 중입니다.").color(NamedTextColor.RED))
            return
        }


        val nearestPlayer = user.world.players
            .filter { it != user && it.isOnline }
            .minByOrNull { it.location.distance(user.location) }

        if (nearestPlayer == null) {
            user.sendActionBar(Component.text("추적 가능한 플레이어가 없습니다.").color(NamedTextColor.RED))
            user.playSound(user.location, "minecraft:block.note_block.pling", 1f, 0.5f)
            return
        }

        val meta = item.itemMeta
        meta.persistentDataContainer.set(usingKey, PersistentDataType.BOOLEAN, true)
        item.itemMeta = meta

        val afterminus = useCount - 1

        object : BukkitRunnable() {
            var seconds = time + 1

            override fun run() {
                if (!user.isOnline) {
                    cancel()
                    return
                }
                seconds--
                user.sendActionBar(Component.text("추적: ${seconds}초 남음").color(NamedTextColor.GREEN))
                user.setCompassTarget(nearestPlayer.location)

                if (seconds <= 0) {
                    user.sendActionBar(Component.text("추적 종료됨").color(NamedTextColor.RED))
                    user.playSound(user.location, "minecraft:entity.ender_eye.death", 1f, 1f)
                    user.setCompassTarget(user.location)
                    val finalMeta = item.itemMeta
                    finalMeta.persistentDataContainer.set(usingKey, PersistentDataType.BOOLEAN, false)
                    finalMeta.persistentDataContainer.set(useKey, PersistentDataType.INTEGER, afterminus)

                    val updatedLoreList = listOf(
                        Component.text("트래커입니다. 다른 플레이어를 추적합니다."),
                        Component.text("사용 횟수: ${afterminus}회").color(NamedTextColor.GREEN),
                    )
                    finalMeta.lore(updatedLoreList)
                    item.itemMeta = finalMeta

                    if (afterminus <= 0) {
                        user.inventory.removeItem(item)
                        user.sendMessage("트래커가 파괴되었습니다.")
                        user.playSound(user.location, "minecraft:block.anvil.destroy", 1f, 1f)
                    }
                    user.sendMessage("남은 사용 횟수: $afterminus")
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 20L)







    }

}