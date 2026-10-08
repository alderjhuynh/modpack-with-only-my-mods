package com.aura.armory;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public final class ModItems {
	private ModItems() {
	}

	public static final ResourceKey<Item> IRON_KATANA_KEY =
			ResourceKey.create(Registries.ITEM, Armory.id("iron_katana"));

	public static final Item IRON_KATANA = Registry.register(
			BuiltInRegistries.ITEM,
			IRON_KATANA_KEY,
			new Item(new Item.Properties().sword(ToolMaterial.IRON, 3.0F, -2.4F).setId(IRON_KATANA_KEY)));

	public static final ResourceKey<CreativeModeTab> COMBAT_TAB =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("combat"));

	public static void register() {
		CreativeModeTabEvents.modifyOutputEvent(COMBAT_TAB).register((output) -> output.accept(IRON_KATANA));
		Armory.LOGGER.info("Registered armory items");
	}
}
