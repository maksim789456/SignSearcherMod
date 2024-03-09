package me.maksim789456.signsearcher.event

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.block.entity.SignBlockEntity

fun interface SignUpdateCallback {
	companion object {
		@JvmField
		val EVENT =
			EventFactory.createArrayBacked(SignUpdateCallback::class.java) { listeners: Array<SignUpdateCallback> ->
				SignUpdateCallback { blockEntity ->
					for (listener in listeners) {
						listener.update(blockEntity)
					}
				}
			}
	}

	fun update(blockEntity: SignBlockEntity)
}