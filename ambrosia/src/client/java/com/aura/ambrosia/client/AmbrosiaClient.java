package com.aura.ambrosia.client;

import com.aura.ambrosia.client.appleskin.AppleSkin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class AmbrosiaClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(AppleSkin.INSTANCE::tick);
	}
}
