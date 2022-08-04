package me.abdullah.core.item;

import me.abdullah.core.Core;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.concurrent.Callable;

public enum GameItemRecipe {

    BUNDLE(() -> {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Core.getInstance(), "game_item_bundle"), new ItemStack(Material.BUNDLE));
        recipe.shape("SLS", "L L", "LLL");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('L', Material.LEATHER);

        return recipe;
    });

    private Callable<Recipe> recipe;
    GameItemRecipe(Callable<Recipe> recipe){
        this.recipe = recipe;
    }

    public Callable<Recipe> getRecipe() {
        return recipe;
    }
}
