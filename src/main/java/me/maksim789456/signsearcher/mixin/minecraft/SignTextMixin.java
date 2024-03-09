package me.maksim789456.signsearcher.mixin.minecraft;

import me.maksim789456.signsearcher.ext.SignTextExt;
import net.minecraft.block.entity.SignText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SignText.class)
public abstract class SignTextMixin implements SignTextExt {
    @Accessor
    public abstract Text[] getMessages();
}
