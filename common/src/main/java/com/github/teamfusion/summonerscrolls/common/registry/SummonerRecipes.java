package com.github.teamfusion.summonerscrolls.common.registry;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.recipe.AnvilScrollRecipe;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class SummonerRecipes {

    public static final CoreRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = CoreRegistry.create(BuiltInRegistries.RECIPE_SERIALIZER, SummonerScrolls.MOD_ID);
    public static final CoreRegistry<RecipeType<?>> RECIPE_TYPES = CoreRegistry.create(BuiltInRegistries.RECIPE_TYPE, SummonerScrolls.MOD_ID);

    public static final Supplier<RecipeSerializer<AnvilScrollRecipe>> ANVIL_SCROLL_SERIALIZER = RECIPE_SERIALIZERS.register(
            AnvilScrollRecipe.Serializer.ID,
            () -> AnvilScrollRecipe.Serializer.INSTANCE
    );

    public static final Supplier<RecipeType<AnvilScrollRecipe>> ANVIL_SCROLL_TYPE =
            RECIPE_TYPES.register(
                    AnvilScrollRecipe.Type.ID,
                    () -> AnvilScrollRecipe.Type.INSTANCE
            );

    public static void registerRecipes() {
        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();
    }
}
