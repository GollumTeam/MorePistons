package mods.morePistons.common;

import net.minecraft.creativetab.CreativeTabs;

public class MorePistonsTabs extends CreativeTabs {
	
	private int iconID = 1;

	public MorePistonsTabs(String label) {
		super(label);
	}
	
	public void setIconItem(int id) {
		iconID = id;
	}
	
	@Override
    public int getTabIconItemIndex() {
        return iconID;
    }
}
