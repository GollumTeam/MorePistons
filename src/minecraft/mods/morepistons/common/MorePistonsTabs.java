package mods.morepistons.common;

import net.minecraft.creativetab.CreativeTabs;

public class MorePistonsTabs extends CreativeTabs {
	
	private int iconId = 1;

	public MorePistonsTabs(String label, int iconId) {
		super(label);
		this.iconId = iconId;
	}
	
	@Override
    public int getTabIconItemIndex() {
        return iconId;
    }
}