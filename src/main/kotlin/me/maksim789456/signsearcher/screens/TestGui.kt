package me.maksim789456.signsearcher.screens

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Insets
import me.maksim789456.signsearcher.SignSearcherMod
import me.maksim789456.signsearcher.screens.controls.WSignButton
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

class TestGui : LightweightGuiDescription() {
	private val signButtons: ArrayList<WSignButton> = ArrayList()
	private var frontSide: Boolean = true

	init {
		val root = WPlainPanel()
		root.insets = Insets(7, 7, 7, 7)
		setRootPanel(root)
		root.setSize(330, 200)
//		root.insets = Insets.ROOT_PANEL

		val label = WLabel(Text.literal("Signs around"))
		root.add(label, 0, 0, 2, 1)

		val changeSide = WButton(Text.literal("Back"))
		changeSide.setOnClick {
			frontSide = !frontSide
			changeSide.label = if (frontSide) Text.literal("Back") else Text.literal("Front")

			for (sign in signButtons) {
				sign.changeSide()
			}
		}
		root.add(changeSide, root.width - 100 - 3, 0, 50, 20)

		val cancel = WButton(Text.literal("Close"))
		cancel.setOnClick { MinecraftClient.getInstance().setScreen(null) }
		root.add(cancel, root.width - 50, 0, 50, 20)

		val grid = WGridPanel(20)
		grid.setGaps(80, 30)
		val scrollPanel = WScrollPanel(grid)
		root.add(scrollPanel, 0, 25, 330, 185)
		scrollPanel.addPainters();

		var indexX = 0
		var indexY = 0
		for (sign in SignSearcherMod.signs) {
			val button = WSignButton(sign.value)
			button.setSize(0, 0)
			grid.add(button, indexX, indexY)
			signButtons.add(button)
			indexX++
			if (indexX >= 3) {
				indexX = 0;
				indexY++
			}
		}

		root.validate(this)
	}
}