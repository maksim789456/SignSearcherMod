package me.maksim789456.signsearcher.mixin.minecraft;

import me.maksim789456.signsearcher.ext.BlockEntityRenderDispatcherExt;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin implements BlockEntityRenderDispatcherExt {
    @Unique
    public OutlineVertexConsumerProvider outlineVcp;

    @Override
    public OutlineVertexConsumerProvider getOutlineVcp() {
        return outlineVcp;
    }

    @Override
    public void setOutlineVcp(OutlineVertexConsumerProvider value) {
        outlineVcp = value;
    }
}
