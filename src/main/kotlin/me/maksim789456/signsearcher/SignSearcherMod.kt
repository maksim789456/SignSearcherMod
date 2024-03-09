package me.maksim789456.signsearcher

import me.maksim789456.signsearcher.config.ModConfig
import me.maksim789456.signsearcher.event.SignUpdateCallback
import me.maksim789456.signsearcher.ext.SignBlockEntityExt
import me.maksim789456.signsearcher.ext.SignTextExt
import me.maksim789456.signsearcher.screens.TestGui
import me.maksim789456.signsearcher.screens.TestScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.block.entity.SignBlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SignSearcherMod : ClientModInitializer {
	companion object {
		const val modId = "signsearcher"
		var config = ModConfig.defaultConfig
		val signs = HashMap<BlockPos, SignBlockEntity>()
		val logger: Logger = LoggerFactory.getLogger(modId)
	}

	private lateinit var openTestScreenKeyBind: KeyBinding

	override fun onInitializeClient() {
		ModConfig.load()

		SignUpdateCallback.EVENT.register(this::addSign)
		ClientBlockEntityEvents.BLOCK_ENTITY_UNLOAD.register { blockEntity, _ ->
			if (blockEntity is SignBlockEntity) {
				removeSign(blockEntity)
			}
		}

		openTestScreenKeyBind = KeyBindingHelper.registerKeyBinding(
			KeyBinding(
				"key.signsearcher.test",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_J,
				"category.signsearcher.test"
			)
		)

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client: MinecraftClient ->
			while (openTestScreenKeyBind.wasPressed()) {
				client.setScreen(TestScreen(TestGui()))
			}
		})
	}

	fun addSign(sign: SignBlockEntity) {
		signs[sign.pos] = sign
		//TODO: update this sign

		val frontText: String = ((sign as SignBlockEntityExt).getFrontText() as SignTextExt).getMessages()
			.joinToString("\n") { Text.Serializer.toJson(it) }
		val backText: String = ((sign as SignBlockEntityExt).getBackText() as SignTextExt).getMessages()
			.joinToString("\n") { Text.Serializer.toJson(it) }
//		logger.info("Sign updated at ${sign.pos.toShortString()}\nfrontText:\n$frontText\nbackText:\n$backText")
	}

	fun removeSign(sign: SignBlockEntity) {
		logger.info("Unload sign at ${sign.pos}")
		signs.remove(sign.pos)
	}
}