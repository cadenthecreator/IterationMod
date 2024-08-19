package org.caden.iterationmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.caden.iterationmod.Gravity;

import java.util.Objects;

public class IterationModClient implements ClientModInitializer {
    public static final Logger ClientLogger = LogManager.getLogger("Iteration(Client)");
    @Override
    public void onInitializeClient() {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // Get the attribute instance
                var attributeInstance = client.player.getAttributeInstance(Gravity.GRAVITY_ATTRIBUTE);
                if (attributeInstance != null) {
                    double value = attributeInstance.getValue();

                    if (!client.isPaused() && !client.player.getAbilities().flying && (!(client.player.isOnGround() || client.player.isSubmergedInWater()) || value<0)) {
                        client.player.addVelocity(0, 0.08 - (0.08 * value), 0);
                    }
                } else {
                    ClientLogger.warn("Gravity attribute instance is null.");
                }
            }
        });
    }
}
