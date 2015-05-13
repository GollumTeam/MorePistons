package com.gollum.morepistons.common.item;

import static com.gollum.morepistons.ModMorePistons.log;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPiston;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.gollum.core.tools.registered.RegisteredObjects;
import com.gollum.morepistons.common.block.BlockMorePistonsRedStoneProxy;
import com.gollum.morepistons.common.block.BlockMorePistonsVanillaProxy;

public class ItemMorePistonsVanillaProxy extends ItemPiston {
	
	public Item vanillaItem;
	public int blockId;
	
	public ItemMorePistonsVanillaProxy(int id, Item vanillaItem) {
		super(id);
		this.vanillaItem = vanillaItem;
		this.blockId = id + 256;
	}
	
	private ItemStack replaceItemStack (ItemStack is) {
		if (this.itemID == is.itemID) {
			is.itemID = ((BlockMorePistonsRedStoneProxy)Block.blocksList[this.blockId]).target.blockID;
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
	public Icon getIcon(ItemStack is, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
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
	public ItemStack getContainerItemStack(ItemStack is) {
		ItemStack nIs = this.replaceItemStack(is);
		return nIs.getItem().getContainerItemStack(nIs);
	}
	
}
