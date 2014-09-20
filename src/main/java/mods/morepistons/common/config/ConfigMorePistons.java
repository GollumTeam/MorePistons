package mods.morepistons.common.config;

import mods.gollum.core.common.config.Config;
import mods.gollum.core.common.config.ConfigProp;

public class ConfigMorePistons extends Config {

	@ConfigProp public int[]     test1 = new int[]    { 1, 2, 3 };
	@ConfigProp public long[]    test2 = new long[]   { 11, 22, 33 };
	@ConfigProp public Integer[] test3 = new Integer[]{ 111, 222, 333 };
	@ConfigProp public Long[]    test4 = new Long[]   { 1111L, 2222L, 3333L };
	
}
