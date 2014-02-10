package morePistons;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

public class MorePistonsRecipes
{
    public MorePistonsRecipes()
    {
        ModLoader.addRecipe(new ItemStack(MorePistons.doublePistonBase, 1), new Object[] {"XXX", " Y ", " Z ", 'X', Block.planks, 'Y', Item.ingotIron, 'Z', Block.pistonBase});
        ModLoader.addRecipe(new ItemStack(MorePistons.doubleStickyPistonBase, 1), new Object[] {"Y", "X", 'X', MorePistons.doublePistonBase, 'Y', Item.slimeBall});
        ModLoader.addRecipe(new ItemStack(MorePistons.doubleStickyPistonBase, 1), new Object[] {"XXX", " Y ", " Z ", 'X', Block.planks, 'Y', Item.ingotIron, 'Z', Block.pistonStickyBase});
        ModLoader.addRecipe(new ItemStack(MorePistons.triplePistonBase, 1), new Object[] {"XXX", " Y ", " Z ", 'X', Block.planks, 'Y', Item.ingotIron, 'Z', MorePistons.doublePistonBase});
        ModLoader.addRecipe(new ItemStack(MorePistons.tripleStickyPistonBase, 1), new Object[] {"Y", "X", 'X', MorePistons.triplePistonBase, 'Y', Item.slimeBall});
        ModLoader.addRecipe(new ItemStack(MorePistons.tripleStickyPistonBase, 1), new Object[] {"XXX", " Y ", " Z ", 'X', Block.planks, 'Y', Item.ingotIron, 'Z', MorePistons.doubleStickyPistonBase});
        ModLoader.addRecipe(new ItemStack(MorePistons.quadruplePistonBase, 1), new Object[] {"XXX", " Y ", " Z ", 'X', Block.planks, 'Y', Item.ingotIron, 'Z', MorePistons.triplePistonBase});
        ModLoader.addRecipe(new ItemStack(MorePistons.quadrupleStickyPistonBase, 1), new Object[] {"Y", "X", 'X', MorePistons.quadruplePistonBase, 'Y', Item.slimeBall});
        ModLoader.addRecipe(new ItemStack(MorePistons.quadrupleStickyPistonBase, 1), new Object[] {"XXX", " Y ", " Z ", 'X', Block.planks, 'Y', Item.ingotIron, 'Z', MorePistons.tripleStickyPistonBase});
        ModLoader.addRecipe(new ItemStack(MorePistons.gravitationalPistonBase, 1), new Object[] {"Y", "X", 'X', Block.pistonBase, 'Y', Block.tnt});
        ModLoader.addRecipe(new ItemStack(MorePistons.superPistonBase, 1), new Object[] {"WWW", "CIC", "ORO", 'W', Block.planks, 'C', Block.cobblestone, 'I', Item.ingotIron, 'O', Block.obsidian, 'R', Item.redstone});
        ModLoader.addRecipe(new ItemStack(MorePistons.superStickyPistonBase, 1), new Object[] {"S", "P", 'S', Item.slimeBall, 'P', MorePistons.superPistonBase});
    }
}
