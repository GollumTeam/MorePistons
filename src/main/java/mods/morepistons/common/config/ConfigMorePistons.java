package mods.morepistons.common.config;

import mods.gollum.core.common.config.Config;
import mods.gollum.core.common.config.ConfigProp;

public class ConfigMorePistons extends Config {
	
	@ConfigProp(minValue="0") public int numberMovableBlockWithDefaultPiston = 12;
	@ConfigProp(minValue="0") public int numberMovableBlockWithSuperPiston   = 41;
	
	@ConfigProp(minValue="0.1") public double powerGravitationalPistons = 1.5D;
}
