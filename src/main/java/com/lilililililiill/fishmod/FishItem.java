package com.lilililililiill.fishmod;

import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FishItem extends Item {
    public static final String SIZE_MULTIPLIER_TAG = "FishSizeMultiplier";
    public static final float MIN_SIZE_MULTIPLIER = 0.3F;
    public static final float MAX_SIZE_MULTIPLIER = 3.0F;

    public FishItem(Properties properties) {
        super(properties);
    }

    public static float getSizeMultiplier(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains(SIZE_MULTIPLIER_TAG)) {
            return 1.0F;
        }
        return Mth.clamp(stack.getTag().getFloat(SIZE_MULTIPLIER_TAG), MIN_SIZE_MULTIPLIER, MAX_SIZE_MULTIPLIER);
    }

    public static boolean hasSizeMultiplier(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(SIZE_MULTIPLIER_TAG);
    }

    public static void setSizeMultiplier(ItemStack stack, float sizeMultiplier) {
        stack.getOrCreateTag().putFloat(SIZE_MULTIPLIER_TAG, Mth.clamp(sizeMultiplier, MIN_SIZE_MULTIPLIER, MAX_SIZE_MULTIPLIER));
    }

    public static SizeGrade determineSizeGrade(float sizeMultiplier) {
        float normalized = Mth.clamp((sizeMultiplier - MIN_SIZE_MULTIPLIER) / (MAX_SIZE_MULTIPLIER - MIN_SIZE_MULTIPLIER), 0.0F, 1.0F);
        if (normalized < 0.70F) {
            return SizeGrade.WHITE;
        }
        if (normalized < 0.90F) {
            return SizeGrade.YELLOW;
        }
        return SizeGrade.PURPLE;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        int bonusNutrition = determineSizeGrade(getSizeMultiplier(stack)).bonusNutrition();
        ItemStack result = super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide && bonusNutrition > 0 && livingEntity instanceof Player player) {
            player.getFoodData().eat(bonusNutrition, 0.0F);
        }
        return result;
    }

    public enum SizeGrade {
        WHITE("White", ChatFormatting.WHITE, 0),
        YELLOW("Yellow", ChatFormatting.YELLOW, 1),
        PURPLE("Purple", ChatFormatting.LIGHT_PURPLE, 2);

        private final String displayName;
        private final ChatFormatting color;
        private final int bonusNutrition;

        SizeGrade(String displayName, ChatFormatting color, int bonusNutrition) {
            this.displayName = displayName;
            this.color = color;
            this.bonusNutrition = bonusNutrition;
        }

        public String displayName() {
            return this.displayName;
        }

        public ChatFormatting color() {
            return this.color;
        }

        public int bonusNutrition() {
            return this.bonusNutrition;
        }
    }
}
