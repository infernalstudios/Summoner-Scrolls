package com.github.teamfusion.summonerscrolls.common.recipe;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class AnvilScrollRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    final Ingredient scroll;
    final Ingredient upgrade;
    final ItemStack result;
    final int cost;

    public AnvilScrollRecipe(ResourceLocation id, Ingredient scroll, Ingredient upgrade, ItemStack result, int cost) {
        this.id = id;
        this.scroll = scroll;
        this.upgrade = upgrade;
        this.result = result;
        this.cost = cost;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.scroll.test(container.getItem(0)) && this.upgrade.test(container.getItem(1));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack itemStack = this.result.copy();
        CompoundTag compoundTag = container.getItem(1).getTag();
        if (compoundTag != null) {
            itemStack.setTag(compoundTag.copy());
        }

        return itemStack;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i >= 2 && j >= 1;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getCost() {
        return this.cost;
    }

    public static class Type implements RecipeType<AnvilScrollRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "anvil_scroll";
    }

    public static class Serializer implements RecipeSerializer<AnvilScrollRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "anvil_scroll";

        public AnvilScrollRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {

            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "scroll"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, "upgrade"));
            ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            int cost = GsonHelper.getAsInt(jsonObject, "levelcost");
            return new AnvilScrollRecipe(resourceLocation, ingredient, ingredient2, itemStack, cost);
        }

        public AnvilScrollRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient ingredient = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient ingredient2 = Ingredient.fromNetwork(friendlyByteBuf);
            ItemStack itemStack = friendlyByteBuf.readItem();
            int cost = friendlyByteBuf.readInt();
            return new AnvilScrollRecipe(resourceLocation, ingredient, ingredient2, itemStack, cost);
        }

        public void toNetwork(FriendlyByteBuf friendlyByteBuf, AnvilScrollRecipe anvilScrollRecipe) {

            anvilScrollRecipe.scroll.toNetwork(friendlyByteBuf);
            anvilScrollRecipe.upgrade.toNetwork(friendlyByteBuf);
            friendlyByteBuf.writeItem(anvilScrollRecipe.result);
            friendlyByteBuf.writeInt(anvilScrollRecipe.cost);
        }
    }
}
