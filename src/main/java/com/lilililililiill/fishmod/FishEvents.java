package com.lilililililiill.fishmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Locale;

public final class FishEvents {
    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        RandomSource random = event.getEntity().getRandom();
        for (ItemStack stack : event.getDrops()) {
            FishDefinition fishData = ModItems.getFishData(stack.getItem());
            if (fishData == null || FishItem.hasSizeMultiplier(stack)) {
                continue;
            }

            float sizeMultiplier = Mth.nextFloat(random, FishItem.MIN_SIZE_MULTIPLIER, FishItem.MAX_SIZE_MULTIPLIER);
            FishItem.setSizeMultiplier(stack, sizeMultiplier);
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        FishDefinition fishData = ModItems.getFishData(stack.getItem());
        if (fishData == null) {
            return;
        }

        String speciesGrade = fishData.speciesGrade();
        ChatFormatting speciesGradeColor = ("A".equals(speciesGrade) || "B".equals(speciesGrade)) ? ChatFormatting.AQUA : ChatFormatting.GRAY;
        event.getToolTip().add(Component.literal("Species Grade: " + speciesGrade)
                .withStyle(ChatFormatting.ITALIC, speciesGradeColor));

        float sizeMultiplier = FishItem.getSizeMultiplier(stack);
        FishItem.SizeGrade sizeGrade = FishItem.determineSizeGrade(sizeMultiplier);
        event.getToolTip().add(Component.literal("Size: " + sizeGrade.displayName())
                .withStyle(sizeGrade.color()));

        event.getToolTip().add(Component.literal(String.format(Locale.ROOT, "Size: %.2fx", sizeMultiplier))
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }

    private FishEvents() {
    }
}
