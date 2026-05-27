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

    public static final RegistryObject<Item> ATLANTIC_COD = registerFish("atlantic_cod", "B");
    public static final RegistryObject<Item> BLACKFISH = registerFish("blackfish", "D");
    public static final RegistryObject<Item> PACIFIC_HALIBUT = registerFish("pacific_halibut", "B");
    public static final RegistryObject<Item> ATLANTIC_HALIBUT = registerFish("atlantic_halibut", "A");
    public static final RegistryObject<Item> ATLANTIC_HERRING = registerFish("atlantic_herring", "E");
    public static final RegistryObject<Item> PINK_SALMON = registerFish("pink_salmon", "C");
    public static final RegistryObject<Item> POLLOCK = registerFish("pollock", "C");
    public static final RegistryObject<Item> RAINBOW_TROUT = registerFish("rainbow_trout", "C");
    public static final RegistryObject<Item> BAYAD = registerFish("bayad", "C");
    public static final RegistryObject<Item> BOULTI = registerFish("boulti", "D");
    public static final RegistryObject<Item> CAPITAINE = registerFish("capitaine", "A");
    public static final RegistryObject<Item> SYNODONTIS = registerFish("synodontis", "E");
    public static final RegistryObject<Item> SMALLMOUTH_BASS = registerFish("smallmouth_bass", "D");
    public static final RegistryObject<Item> LARGEMOUTH_BASS = registerFish("largemouth_bass", "C");
    public static final RegistryObject<Item> BLUEGILL = registerFish("bluegill", "E");
    public static final RegistryObject<Item> BROWN_TROUT = registerFish("brown_trout", "D");
    public static final RegistryObject<Item> CARP = registerFish("carp", "D");
    public static final RegistryObject<Item> CATFISH = registerFish("catfish", "A");
    public static final RegistryObject<Item> GAR = registerFish("gar", "C");
    public static final RegistryObject<Item> MINNOW = registerFish("minnow", "E");
    public static final RegistryObject<Item> MUSKELLUNGE = registerFish("muskellunge", "B");
    public static final RegistryObject<Item> PERCH = registerFish("perch", "E");
    public static final RegistryObject<Item> ARAPAIMA = registerFish("arapaima", "A");
    public static final RegistryObject<Item> PIRANHA = registerFish("piranha", "E");
    public static final RegistryObject<Item> TAMBAQUI = registerFish("tambaqui", "C");
    public static final RegistryObject<Item> BROWN_SHROOMA = registerFish("brown_shrooma", "E");
    public static final RegistryObject<Item> RED_SHROOMA = registerFish("red_shrooma", "E");
    public static final RegistryObject<Item> JELLYFISH = registerFish("jellyfish", "D");
    public static final RegistryObject<Item> RED_GROUPER = registerFish("red_grouper", "C");
    public static final RegistryObject<Item> TUNA = registerFish("tuna", "A");
    public static final RegistryObject<Item> LEECH = registerFish("leech", "D");
    public static final RegistryObject<Item> BOX_TURTLE = registerFish("box_turtle", "A");
    public static final RegistryObject<Item> ARRAU_TURTLE = registerFish("arrau_turtle", "A");
    public static final RegistryObject<Item> STARSHELL_TURTLE = registerFish("starshell_turtle", "A");
    public static final RegistryObject<Item> GOLDFISH = registerFish("goldfish", "D");

    private static RegistryObject<Item> registerFish(String name, String speciesGrade) {
        RegistryObject<Item> fish = ITEMS.register(name,
                () -> new FishItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));
        FISH_DATA.put(fish, new FishDefinition(speciesGrade, weightForSpeciesGrade(speciesGrade)));
        return fish;
    }

    private static int weightForSpeciesGrade(String speciesGrade) {
        return switch (speciesGrade) {
            case "E" -> 60;
            case "D" -> 30;
            case "C" -> 20;
            case "B" -> 12;
            case "A" -> 6;
            default -> throw new IllegalArgumentException("Unknown species grade: " + speciesGrade);
        };
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
