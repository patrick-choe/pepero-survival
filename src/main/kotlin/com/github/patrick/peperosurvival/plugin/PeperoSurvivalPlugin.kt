/*
 * Copyright (C) 2020 PatrickKR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact me on <mailpatrickkr@gmail.com>
 */

package com.github.patrick.peperosurvival.plugin

import net.minecraft.server.v1_16_R3.Chunk
import net.minecraft.server.v1_16_R3.TileEntity
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Logger
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class PeperoSurvivalPlugin : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()

        setOf(Chunk::class.java, TileEntity::class.java).forEach { clazz ->
            val logger = LogManager.getLogger(clazz)

            if (logger is Logger) {
                logger.level = Level.ERROR
            }
        }

        val distance = config.getInt("distance").takeIf { it > 1 } ?: 4
        val air = (Material.AIR.createBlockData() as CraftBlockData).state
        val endPortalFrame = (Material.END_PORTAL_FRAME.createBlockData() as CraftBlockData).state.block.toString()

        server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onChunkLoad(event: ChunkLoadEvent) {
                if (event.isNewChunk) {
                    val chunk = event.chunk
                    val nmsChunk = (event.chunk as CraftChunk).handle

                    for (x in 0 until 16) {
                        for (z in 0 until 16) {
                            if ((x + chunk.x * 16) % distance != 0 || (z + chunk.z * 16) % distance != 0) {
                                nmsChunk.sections.filterNotNull().forEach { section ->
                                    for (y in 0 until 16) {
                                        if (section.blocks.a(x, y, z).block.toString() != endPortalFrame) {
                                            section.blocks.b(x, y, z, air)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    chunk.unload()
                }
            }

            @EventHandler
            fun onBlockFromTo(event: BlockFromToEvent) {
                if (event.block.isLiquid) {
                    event.isCancelled = true
                }
            }

            @EventHandler
            fun onBlockPlace(event: BlockPlaceEvent) {
                val block = event.block

                if ((block.x % distance != 0 || block.z % distance != 0) && event.blockReplacedState.block.type != Material.END_PORTAL_FRAME) {
                    event.isCancelled = true
                }
            }

            @EventHandler
            fun onPlayerBucketEmpty(event: PlayerBucketEmptyEvent) {
                val block = event.block

                if (block.x % distance != 0 || block.z % distance != 0) {
                    event.isCancelled = true
                }
            }
        }, this)
    }
}