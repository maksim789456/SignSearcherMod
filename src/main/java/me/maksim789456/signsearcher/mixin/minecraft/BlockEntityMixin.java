package me.maksim789456.signsearcher.mixin.minecraft;

import me.maksim789456.signsearcher.ext.BlockEntityExt;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements BlockEntityExt {
    @Unique
    private boolean glowing = false;

    @Unique
    private int glowColor = 0xffffff;

    @Override
    public boolean isGlowing() {
        return glowing;
    }

    @Override
    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    @Override
    public int getGlowColor() {
        return glowColor;
    }

    @Override
    public void setGlowColor(int glowColor) {
        this.glowColor = glowColor;
    }
}
