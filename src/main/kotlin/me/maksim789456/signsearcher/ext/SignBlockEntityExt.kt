package me.maksim789456.signsearcher.ext

import net.minecraft.block.entity.SignText

interface SignBlockEntityExt {
	fun getFrontText(): SignText
	fun getBackText(): SignText
}