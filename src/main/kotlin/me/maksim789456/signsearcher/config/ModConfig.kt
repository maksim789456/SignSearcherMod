package me.maksim789456.signsearcher.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.maksim789456.signsearcher.SignSearcherMod
import net.fabricmc.loader.api.FabricLoader
import java.awt.Color
import java.lang.RuntimeException
import java.nio.file.Files
import java.nio.file.Path

@Serializable
data class ModConfig(@Serializable var test: Boolean,
					 @Serializable(with = ColorAsHexStringSerializer::class) var color: Color)
{
	companion object {
		val defaultConfig = ModConfig(false, Color.pink)

		fun save() {
			val filePath = getFilePath();
			try {
				val jsonStr = Json.encodeToString(SignSearcherMod.config);
				Files.writeString(filePath, jsonStr)
			} catch (e: Exception) {
				//TODO: process exceptions
				throw RuntimeException(e)
			}
		}

		fun load() {
			val filePath = getFilePath();
			try {
				if (Files.exists(filePath)) {
					val jsonStr = Files.readString(filePath)
					if (jsonStr.isNotEmpty()) {
						SignSearcherMod.config = Json.decodeFromString(jsonStr)
					}
				}
			} catch (e: Exception) {
				//TODO: process exceptions
				throw RuntimeException(e)
			}

			SignSearcherMod.config = defaultConfig
		}

		private fun getFilePath(): Path {
			return FabricLoader.getInstance()
				.configDir
				.normalize()
				.toAbsolutePath()
				.resolveSibling("config/" + SignSearcherMod.modId + ".json")
		}
	}
}
