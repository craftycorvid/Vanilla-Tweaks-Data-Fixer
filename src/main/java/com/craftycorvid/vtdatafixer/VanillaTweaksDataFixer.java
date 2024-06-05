package com.craftycorvid.vtdatafixer;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaTweaksDataFixer implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("vanilla-tweaks-data-fixer");

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded Vanilla Tweaks Data Fixer");
	}
}
