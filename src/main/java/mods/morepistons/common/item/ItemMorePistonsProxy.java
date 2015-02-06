package mods.morepistons.common.item;

import static mods.gollum.autoreplace.ModGollumAutoReplace.log;
import mods.gollum.autoreplace.common.blocks.BlockAutoReplace;
import mods.gollum.autoreplace.common.blocks.BlockAutoReplace.ReplaceBlock;
import mods.gollum.core.tools.registered.RegisteredObjects;
import mods.morepistons.common.block.BlockMorePistonsVanillaProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPiston;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMorePistonsProxy extends ItemPiston {
	
	public Item vanillaItem;
	public BlockMorePistonsVanillaProxy block;
	
	public ItemMorePistonsProxy(Item vanillaItem, Block block) {
		super(block);
		this.vanillaItem = vanillaItem;
		this.block       = (BlockMorePistonsVanillaProxy) block;
	}
	
	private ItemStack replaceItemStack (ItemStack is) {
		if (Item.getItemFromBlock(this.block) == is.getItem()) {
			is.func_150996_a(Item.getItemFromBlock(this.block.target));
		}
		return is;
	}
	
	private void trace () {
		this.trace(new ItemStack(this));
	}
	private void trace (ItemStack is) {
		ItemStack nIs = this.replaceItemStack(is);
		log.message("Block transform \""+RegisteredObjects.instance().getRegisterName(this)+"\":"+is.getItemDamage()+" => \""+RegisteredObjects.instance().getRegisterName(nIs.getItem())+"\":"+nIs.getItemDamage()+"");
	}
	
	@Override
	public IIcon getIcon(ItemStack is, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		ItemStack nIs        = this.replaceItemStack(is);
		ItemStack nUsingItem = this.replaceItemStack(usingItem);
		return nIs.getItem().getIcon(is, renderPass, player, nUsingItem, useRemaining);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer e, World w, int x, int y, int z, int side, float posX, float posY, float posZ) {
		ItemStack nIs = this.replaceItemStack(is);
		this.trace(is);
		return nIs.getItem().onItemUse(is, e, w, x, y, z, side, posX, posY, posZ);
	}
	
	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int slot, boolean b) {
		
		ItemStack nIs = this.replaceItemStack(is);
		
		if (e instanceof EntityPlayer) {
			((EntityPlayer) e).inventory.setInventorySlotContents(slot, nIs);
			this.trace(is);
		}
		nIs.getItem().onUpdate(is, w, e, slot, b);
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack is) {
		ItemStack nIs = this.replaceItemStack(is);
		return nIs.getItem().getContainerItem(nIs);
	}
	
}
