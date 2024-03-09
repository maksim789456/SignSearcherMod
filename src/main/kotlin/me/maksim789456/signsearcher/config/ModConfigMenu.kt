package me.maksim789456.signsearcher.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.ColorControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import me.maksim789456.signsearcher.SignSearcherMod.Companion.config
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import java.awt.Color

@Environment(EnvType.CLIENT)
class ModConfigMenu : ModMenuApi {
	override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
		ModConfig.load()
		return ConfigScreenFactory { parent: Screen ->
			YetAnotherConfigLib.createBuilder()
				.title(Text.of("Tet"))
				.category(
					ConfigCategory.createBuilder()
						.name(Text.of("Generic"))
						.tooltip(Text.of("Generic category"))
						.option(Option.createBuilder<Boolean>()
							.name(Text.of("Test bool"))
							.binding(ModConfig.defaultConfig.test, { config.test }, { value: Boolean -> config.test = value })
							.controller(TickBoxControllerBuilder::create)
							.build())
						.option(Option.createBuilder<Color>()
							.name(Text.of("Test bool"))
							.binding(ModConfig.defaultConfig.color, { config.color }, { value: Color -> config.color = value })
							.controller(ColorControllerBuilder::create)
							.build())
						.build()
				)
				.save(ModConfig::save)
				.build().generateScreen(parent)
		}
	}

}