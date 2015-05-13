package com.gollum.morepistons.common.config;

import com.gollum.core.common.config.Config;
import com.gollum.core.common.config.ConfigProp;

public class ConfigMorePistons extends Config {

	@ConfigProp(mcRestart = true) public boolean overrideVanillaPiston = true;
	@ConfigProp(mcRestart = true) public boolean overrideVanillaStickPiston = true;
	
	@ConfigProp(minValue="0") public int numberMovableBlockWithDefaultPiston = 12;
	@ConfigProp(minValue="0") public int numberMovableBlockWithSuperPiston   = 41;
	
	@ConfigProp(minValue="1.5", maxValue="15.0") public double powerGravitationalPistons = 1.5D;
	
	@ConfigProp(mcRestart = true) public boolean compatibilityWithOldVersion = false;
	
	//////////////
	// Items Id //
	//////////////
	
	@ConfigProp(group = "Items Ids", mcRestart = true) public int itemsMagnetID = 13001;
	
	///////////////
	// Blocks Id //
	///////////////
	
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockPistonMovingID                  = 4045;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockPistonExtensionID               = 4087;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockPistonRodID                     = 4086;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockPistonMagneticExtensionID       = 4044;
	
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockPistonVanillaID                 = 4043;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockStickyPistonVanillaID           = 4042;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockDoublePistonBaseID              = 4095;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockDoubleStickyPistonBaseID        = 4094;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockTriplePistonBaseID              = 4093;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockTripleStickyPistonBaseID        = 4092;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockQuadPistonBaseID                = 4091;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockQuadStickyPistonBaseID          = 4090;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockQuintPistonBaseID               = 4083;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockQuintStickyPistonBaseID         = 4082;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSextPistonBaseID                = 4081;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSextStickyPistonBaseID          = 4080;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSeptPistonBaseID                = 4079;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSeptStickyPistonBaseID          = 4078;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockOctStickyPistonBaseID           = 4077;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockOctPistonBaseID                 = 4076;
	
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockGravitationalPistonBaseID       = 4089;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockGravitationalStickyPistonBaseID = 4088;

	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperPistonBaseID               = 4085;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperStickyPistonBaseID         = 4084;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperDoublePistonBaseID         = 4059;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperDoubleStickyPistonBaseID   = 4058;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperTriplePistonBaseID         = 4057;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperTripleStickyPistonBaseID   = 4056;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperQuadPistonBaseID           = 4055;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperQuadStickyPistonBaseID     = 4054;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperQuintPistonBaseID          = 4053;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperQuintStickyPistonBaseID    = 4052;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperSextPistonBaseID           = 4051;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperSextStickyPistonBaseID     = 4050;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperSeptPistonBaseID           = 4049;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperSeptStickyPistonBaseID     = 4048;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperOctPistonBaseID            = 4047;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockSuperOctStickyPistonBaseID      = 4046;

	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockRedStonePistonBaseId            = 4041;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockRedStoneStickyPistonBaseId      = 4040;
	
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase1ID = 4039;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase2ID = 4038;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase3ID = 4037;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase4ID = 4036;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase5ID = 4035;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase6ID = 4034;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase7ID = 4033;
	@ConfigProp(group = "Blocks Ids", mcRestart = true) public int blockMagneticPistonBase8ID = 4032;
	
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase1ID       = 4075;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase2ID       = 4074;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase3ID       = 4073;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase4ID       = 4072;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase5ID       = 4071;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase6ID       = 4070;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase7ID       = 4069;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStonePistonBase8ID       = 4068;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase1ID = 4067;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase2ID = 4066;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase3ID = 4065;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase4ID = 4064;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase5ID = 4063;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase6ID = 4062;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase7ID = 4061;
	@ConfigProp(group = "Blocks Ids compatibility", mcRestart = true) public int blockRedStoneStickyPistonBase8ID = 4060;
	
}
