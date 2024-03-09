package me.maksim789456.signsearcher.ext

import net.minecraft.client.render.OutlineVertexConsumerProvider

interface BlockEntityRenderDispatcherExt {
	fun getOutlineVcp(): OutlineVertexConsumerProvider?
	fun setOutlineVcp(value: OutlineVertexConsumerProvider?)
}