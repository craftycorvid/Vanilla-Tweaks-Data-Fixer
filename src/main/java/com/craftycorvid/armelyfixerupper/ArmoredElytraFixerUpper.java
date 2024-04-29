package com.craftycorvid.armelyfixerupper;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmoredElytraFixerUpper implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("armored-elytra-fixer-upper");

	@Override
	public void onInitialize() {
		LOGGER.info("Loaded Armored Elytra Fixer Upper");
	}
}
