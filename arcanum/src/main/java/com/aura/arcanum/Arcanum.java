package com.aura.arcanum;

import com.aura.arcanum.network.ArcanumPackets;
import com.aura.arcanum.server.AerodynamicsHandler;
import com.aura.arcanum.server.DashHandler;
import com.aura.arcanum.server.DoubleJumpHandler;
import com.aura.arcanum.server.FlowGlideHandler;
import com.aura.arcanum.server.JetServerHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arcanum implements ModInitializer {
	public static final String MOD_ID = "arcanum";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Arcanum initialized");

		PayloadTypeRegistry.serverboundPlay().register(ArcanumPackets.GlidePayload.TYPE, ArcanumPackets.GlidePayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ArcanumPackets.AerodynamicsPayload.TYPE, ArcanumPackets.AerodynamicsPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ArcanumPackets.DashPayload.TYPE, ArcanumPackets.DashPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ArcanumPackets.DoubleJumpPayload.TYPE, ArcanumPackets.DoubleJumpPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ArcanumPackets.FlowGlidePayload.TYPE, ArcanumPackets.FlowGlidePayload.CODEC);
		PayloadTypeRegistry.clientboundPlay().register(ArcanumPackets.JetBoostPayload.TYPE, ArcanumPackets.JetBoostPayload.CODEC);

		JetServerHandler.register();
		AerodynamicsHandler.register();
		DashHandler.register();
		DoubleJumpHandler.register();
		FlowGlideHandler.register();

		ServerTickEvents.END_SERVER_TICK.register(JetServerHandler::tick);
		ServerTickEvents.END_SERVER_TICK.register(FlowGlideHandler::tick);

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			JetServerHandler.clear(handler.getPlayer().getUUID());
			FlowGlideHandler.clear(handler.getPlayer().getUUID());
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
