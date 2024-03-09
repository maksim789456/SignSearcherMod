package me.maksim789456.signsearcher.mixin.sodium;

import me.jellysquid.mods.sodium.client.render.SodiumWorldRenderer;
import me.maksim789456.signsearcher.ext.BlockEntityExt;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SodiumWorldRenderer.class)
public class SodiumWorldRendererMixin {
    @Redirect(
            method = "renderBlockEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/BlockEntityRenderDispatcher;render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V")
    )
    private static void renderWithOutline(BlockEntityRenderDispatcher dispatcher, BlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vcp, MatrixStack _matrices, BufferBuilderStorage bufferBuilders) {
        if (((BlockEntityExt) blockEntity).isGlowing()) {
            OutlineVertexConsumerProvider outlineVcp = bufferBuilders.getOutlineVertexConsumers();
            int color = ((BlockEntityExt) blockEntity).getGlowColor();
            outlineVcp.setColor(
                    ColorHelper.Argb.getRed(color),
                    ColorHelper.Argb.getGreen(color),
                    ColorHelper.Argb.getBlue(color),
                    0xff
            );
            vcp = outlineVcp;
        }
        dispatcher.render(blockEntity, tickDelta, matrices, vcp);
    }
}
