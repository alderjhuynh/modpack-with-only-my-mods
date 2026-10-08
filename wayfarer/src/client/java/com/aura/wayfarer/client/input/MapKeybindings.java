package com.aura.wayfarer.client.input;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.resources.Identifier;

public final class MapKeybindings {
    private MapKeybindings() {}

    public static final Category CATEGORY = Category.register(Identifier.fromNamespaceAndPath("wayfarer", "controls"));

    public static final KeyMapping OPEN_MAP = new KeyMapping("key.wayfarer.open_map", 77, CATEGORY);
    public static final KeyMapping OPEN_WAYPOINTS = new KeyMapping("key.wayfarer.open_waypoints", 66, CATEGORY);
    public static final KeyMapping ADD_WAYPOINT = new KeyMapping("key.wayfarer.add_waypoint", 78, CATEGORY);

    public static KeyMapping[] all() {
        return new KeyMapping[]{ OPEN_MAP, OPEN_WAYPOINTS, ADD_WAYPOINT };
    }
}
