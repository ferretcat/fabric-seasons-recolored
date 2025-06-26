package io.github.lucaargolo.seasons.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.resources.FoliageSeasonColors;
import io.github.lucaargolo.seasons.resources.GrassSeasonColors;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockColors.class)
public class BlockColorsMixin {
    @Inject(method = "method_1693", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/GrassColors;getDefaultColor()I"), cancellable = true)
    private static void injectGrassColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos,
            int tintIndex, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(GrassSeasonColors.getColor(FabricSeasons.getCurrentSeason(), 0.5D, 1.0D));
    }

    @Inject(method = "method_1695", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/FoliageColors;getSpruceColor()I"), cancellable = true)
    private static void injectSpruceColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos,
            int tintIndex, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(FoliageSeasonColors.getSpruceColor(FabricSeasons.getCurrentSeason()));
    }

    @Inject(method = "method_1687", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/FoliageColors;getBirchColor()I"), cancellable = true)
    private static void injectBirchColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos,
            int tintIndex, CallbackInfoReturnable<Integer> info) {
        // info.setReturnValue(FoliageSeasonColors.getBirchColor(FabricSeasons.getCurrentSeason()));

        // RegistryEntry<Biome> biomeEntry = new RegistryEntry.Direct<Biome>(biome);
        // int birchColor = FoliageColors.getBirchColor();

        // ISeasonColorProvider colorProvider = biome.is(ModTags.Biomes.TROPICAL_BIOMES)
        //         ? calendar.getTropicalSeason()
        //         : calendar.getSubSeason();
        // birchColor = colorProvider.getBirchColor();

        int newBirchColor;

        switch (FabricSeasons.getCurrentSeason()) {
            case SPRING:
                newBirchColor = 0x6EB283;
                break;

            case SUMMER:
                newBirchColor = 0x80A755;
                break;

            case FALL:
                newBirchColor = 0xE2A231;
                break;

            default:
                newBirchColor = 0xA0824D;
                break;
        }

        // if (biome.is(ModTags.Biomes.LESSER_COLOR_CHANGE_BIOMES)) {
        //     birchColor = SeasonColorUtil.mixColours(colorProvider.getBirchColor(), FoliageColor.getBirchColor(),
        //             0.75F);
        // }

        info.setReturnValue(newBirchColor);
    }

    @Inject(method = "method_1692", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/FoliageColors;getDefaultColor()I"), cancellable = true)
    private static void injectFoliageColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos,
            int tintIndex, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(FoliageSeasonColors.getDefaultColor(FabricSeasons.getCurrentSeason()));
    }
}
