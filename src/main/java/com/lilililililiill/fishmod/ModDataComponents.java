package com.lilililililiill.fishmod;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FishMod.MODID);

    public static final RegistryObject<DataComponentType<Float>> FISH_LENGTH_CM = DATA_COMPONENT_TYPES.register("fish_length_cm",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.floatRange(0.0F, Float.MAX_VALUE))
                    .networkSynchronized(ByteBufCodecs.FLOAT)
                    .build());

    public static final RegistryObject<DataComponentType<String>> FISH_GRADE = DATA_COMPONENT_TYPES.register("fish_grade",
            () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    private ModDataComponents() {
    }
}
