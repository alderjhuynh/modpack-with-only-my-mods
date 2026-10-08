package com.aura.folio;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aura.folio.component.ModDataComponents;
import com.aura.folio.entity.ModEntityTypes;
import com.aura.folio.item.ModItems;
import com.aura.folio.spell.Spells;

public class Folio implements ModInitializer {
	public static final String MOD_ID = "folio";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("folio registered");
		ModDataComponents.initialize();
		ModEntityTypes.initialize();
		Spells.initialize();
		ModItems.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
