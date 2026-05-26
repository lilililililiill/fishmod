package com.lilililililiill.fishmod;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FishMod.MODID);
    private static final Map<Supplier<? extends Item>, FishDefinition> FISH_DATA = new LinkedHashMap<>();

    public static final RegistryObject<Item> ANCHOVY = registerFish("anchovy", 6.0F, 18.0F, 26);
    public static final RegistryObject<Item> PERCH = registerFish("perch", 15.0F, 45.0F, 20);
    public static final RegistryObject<Item> CARP = registerFish("carp", 25.0F, 80.0F, 16);
    public static final RegistryObject<Item> MACKEREL = registerFish("mackerel", 20.0F, 60.0F, 10);
    public static final RegistryObject<Item> RED_SNAPPER = registerFish("red_snapper", 30.0F, 95.0F, 5);
    public static final RegistryObject<Item> TUNA = registerFish("tuna", 70.0F, 250.0F, 2);

    private static RegistryObject<Item> registerFish(String name, float minLengthCm, float maxLengthCm, int rarityWeight) {
        RegistryObject<Item> fish = ITEMS.register(name, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
        FISH_DATA.put(fish, new FishDefinition(minLengthCm, maxLengthCm, rarityWeight));
        return fish;
    }

    public static FishDefinition getFishData(Item item) {
        for (Map.Entry<Supplier<? extends Item>, FishDefinition> entry : FISH_DATA.entrySet()) {
            if (entry.getKey().get() == item) {
                return entry.getValue();
            }
        }
        return null;
    }

    private ModItems() {
    }
}
