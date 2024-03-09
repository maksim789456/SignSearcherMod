package me.maksim789456.signsearcher.config

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color

object ColorAsHexStringSerializer : KSerializer<Color> {
	override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

	override fun serialize(encoder: Encoder, value: Color) {
		val string = String.format("#%02x%02x%02x", value.red, value.green, value.blue)
		encoder.encodeString(string)
	}

	override fun deserialize(decoder: Decoder): Color {
		val string = decoder.decodeString()
		return Color.decode(string)
	}
}