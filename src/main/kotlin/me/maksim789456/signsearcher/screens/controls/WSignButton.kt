package me.maksim789456.signsearcher.screens.controls

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import io.github.cottonmc.cotton.gui.widget.data.InputResult
import me.maksim789456.signsearcher.ext.BlockEntityExt
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.AbstractSignBlock
import net.minecraft.block.WoodType
import net.minecraft.block.entity.SignBlockEntity
import net.minecraft.block.entity.SignText
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer.SignModel
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import java.util.*

class WSignButton() : WWidget() {
	private lateinit var text: SignText
	private var messages: Array<String> = Array(4) { String() }
	private lateinit var sign: SignBlockEntity
	private lateinit var signType: WoodType
	private lateinit var model: SignModel
	private var side: Boolean = true
	private val textRenderer: TextRenderer = MinecraftClient.getInstance().textRenderer

	private var enabled = true
	protected var alignment = HorizontalAlignment.CENTER

	private var onClick: Runnable? = null

	constructor(sign: SignBlockEntity) : this() {
		this.sign = sign
		signType = AbstractSignBlock.getWoodType(this.sign.cachedState.block)
		model = SignBlockEntityRenderer.createSignModel(MinecraftClient.getInstance().entityModelLoader, signType)
		updateText()
	}

	fun changeSide() {
		side = !side
		updateText()
	}

	private fun updateText() {
		text = sign.getText(side)
		for (line in 0..3) {
			messages[line] = this.text.getMessage(
				line,
				false
			).string
		}
	}

	override fun canResize(): Boolean = true

	override fun canFocus(): Boolean = true

	override fun addTooltip(tooltip: TooltipBuilder?) {
		tooltip?.add(Text.literal(sign.pos.toString()))
	}

	@Environment(EnvType.CLIENT)
	override fun paint(context: DrawContext, x: Int, y: Int, mouseX: Int, mouseY: Int) {
		context.matrices.push()
		context.matrices.translate(47.0f + x.toFloat(), 23.5f + y.toFloat(), 50.0f)
		context.matrices.push()
		renderSignBackground(context)
		context.matrices.pop()
		renderSignText(context)
		context.matrices.pop()
		renderDebugFullColor(context)

		if (this.parent == null)
			throw NullPointerException()
	}

	private fun renderSignBackground(context: DrawContext) {
		context.matrices.translate(0.0f, 31.0f, 0.0f)
		context.matrices.scale(62.500004f, 62.500004f, -62.500004f)
		val spriteIdentifier = TexturedRenderLayers.getSignTextureId(this.signType)
		val vertexConsumer = spriteIdentifier.getVertexConsumer(
			context.vertexConsumers
		) { texture: Identifier? ->
			this.model.getLayer(texture)
		}
		this.model.stick.visible = false
		this.model.root.render(context.matrices, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV)
	}

	private fun renderDebugFullColor(context: DrawContext) {
		context.matrices.translate(0.0f, 0.0f, 0.0f)
		context.fill(0, 0, width, height, 0xE0E0E0);
	}

	private fun renderSignText(context: DrawContext) {
		var o: Int
		var string: String
		context.matrices.translate(0.0f, 0.0f, 4.0f)
		val textScale = Vector3f(0.9765628f, 0.9765628f, 0.9765628f);
		context.matrices.scale(textScale.x(), textScale.y(), textScale.z())
		val signColor: Int = this.text.color.signColor
		val l: Int = 4 * this.sign.textLineHeight / 2
		var n = 0
		while (n < this.messages.size) {
			string = this.messages[n]
			if (string == null) {
				++n
				continue
			}
			if (this.textRenderer.isRightToLeft) {
				string = this.textRenderer.mirror(string)
			}
			o = -this.textRenderer.getWidth(string) / 2
			context.drawText(this.textRenderer, string, o, n * this.sign.textLineHeight - l, signColor, false)
			++n
		}
	}

	override fun setSize(x: Int, y: Int) {
		super.setSize(95, 48)
	}

	@Environment(EnvType.CLIENT)
	override fun onClick(x: Int, y: Int, button: Int): InputResult {
		super.onClick(x, y, button)
		if (enabled && isWithinBounds(x, y)) {
			MinecraftClient.getInstance().soundManager.play(
				PositionedSoundInstance.master(
					SoundEvents.UI_BUTTON_CLICK,
					1.0f
				)
			)

			if ((sign as BlockEntityExt).isGlowing()) {
				(sign as BlockEntityExt).setGlowing(false);
			} else {
				(sign as BlockEntityExt).setGlowing(true);
			}

			if (onClick != null) onClick!!.run()
			return InputResult.PROCESSED
		}
		return InputResult.IGNORED
	}

	fun getOnClick(): Runnable? {
		return onClick
	}

	fun setOnClick(onClick: Runnable?): WSignButton {
		this.onClick = onClick
		return this
	}

	fun isEnabled(): Boolean {
		return enabled
	}

	fun setEnabled(enabled: Boolean): WSignButton {
		this.enabled = enabled
		return this
	}
}