package com.lilililililiill.fishmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Locale;

public final class FishEvents {
    private static final ResourceLocation VANILLA_FISHING = new ResourceLocation("minecraft", "gameplay/fishing");
    private static final ResourceLocation FISHMOD_FISHING = new ResourceLocation(FishMod.MODID, "gameplay/fishing/fish");

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (!VANILLA_FISHING.equals(event.getName())) {
            return;
        }

        LootPool injectPool = LootPool.lootPool()
                .name("fishmod_fish_inject")
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootTableReference.lootTableReference(FISHMOD_FISHING).setWeight(85).setQuality(-1))
                .build();
        event.getTable().addPool(injectPool);
    }

    @SubscribeEvent
    public static void onItemFished(ItemFishedEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        RandomSource random = event.getEntity().getRandom();
        for (ItemStack stack : event.getDrops()) {
            FishDefinition fishData = ModItems.getFishData(stack.getItem());
            if (fishData == null || stack.has(ModDataComponents.FISH_LENGTH_CM.get())) {
                continue;
            }

            float lengthCm = Mth.nextFloat(random, fishData.minLengthCm(), fishData.maxLengthCm());
            String grade = determineGrade(fishData, lengthCm);

            stack.set(ModDataComponents.FISH_LENGTH_CM.get(), lengthCm);
            stack.set(ModDataComponents.FISH_GRADE.get(), grade);
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (ModItems.getFishData(stack.getItem()) == null) {
            return;
        }

        String grade = stack.get(ModDataComponents.FISH_GRADE.get());
        if (grade != null) {
            event.getToolTip().add(Component.literal("Grade: " + grade)
                    .withStyle(style -> style.withItalic(true).withColor(gradeColor(grade))));
        }

        Float lengthCm = stack.get(ModDataComponents.FISH_LENGTH_CM.get());
        if (lengthCm != null) {
            event.getToolTip().add(Component.literal(String.format(Locale.ROOT, "Length: %.1f cm", lengthCm))
                    .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }

    private static String determineGrade(FishDefinition fishData, float lengthCm) {
        float range = fishData.maxLengthCm() - fishData.minLengthCm();
        if (range <= 0.0F) {
            return "D";
        }

        float normalized = Mth.clamp((lengthCm - fishData.minLengthCm()) / range, 0.0F, 1.0F);
        if (normalized >= 0.95F) {
            return "S";
        }
        if (normalized >= 0.80F) {
            return "A";
        }
        if (normalized >= 0.60F) {
            return "B";
        }
        if (normalized >= 0.35F) {
            return "C";
        }
        return "D";
    }

    private static Integer gradeColor(String grade) {
        return switch (grade) {
            case "S" -> ChatFormatting.GOLD.getColor();
            case "A" -> ChatFormatting.LIGHT_PURPLE.getColor();
            case "B" -> ChatFormatting.AQUA.getColor();
            case "C" -> ChatFormatting.GREEN.getColor();
            default -> ChatFormatting.WHITE.getColor();
        };
    }

    private FishEvents() {
    }
}
