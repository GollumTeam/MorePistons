package com.gollum.morepistons.common.config;

import com.gollum.core.common.config.Config;
import com.gollum.core.common.config.ConfigProp;

public class ConfigMorePistons extends Config {
	
	@ConfigProp(mcRestart = true) public boolean overrideVanillaPiston = true;
	@ConfigProp(mcRestart = true) public boolean overrideVanillaStickPiston = true;
	
	@ConfigProp(minValue="0") public int numberMovableBlockWithDefaultPiston = 12;
	@ConfigProp(minValue="0") public int numberMovableBlockWithSuperPiston   = 41;
	
	@ConfigProp(minValue="1.5", maxValue="15.0") public double powerGravitationalPistons = 1.5D;
}
