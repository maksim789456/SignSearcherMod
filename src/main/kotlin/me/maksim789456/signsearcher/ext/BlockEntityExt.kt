package me.maksim789456.signsearcher.ext

interface BlockEntityExt {
	fun isGlowing(): Boolean
	fun setGlowing(glowing: Boolean)
	fun getGlowColor(): Int
	fun setGlowColor(glowColor: Int)
}