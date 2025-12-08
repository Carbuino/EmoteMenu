package ca.carbuino.extension.emotemenu;

import ca.carbuino.extension.emotemenu.utils.EmoteMenuUtils;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.session.GeyserSession;

import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreInitializeEvent;
import org.geysermc.geyser.api.event.bedrock.ClientEmoteEvent;
import org.geysermc.geyser.api.extension.Extension;

public class EmoteMenu implements Extension {

    @Subscribe
    public void onPreInit(GeyserPreInitializeEvent event) {
        this.logger().info("Loading Emote Menu...");
    }

    @Subscribe
    public void onPostInit(GeyserPostInitializeEvent event) {
        this.logger().info("The Emote Menu ready to roll!");
    }

    @Subscribe
    public void onEmoteEvent(ClientEmoteEvent event) {
        GeyserSession session = (GeyserSession) event.connection();

        // Build and Send the Emote Menu
        session.sendForm(EmoteMenuUtils.buildEmoteForm(session)); 

    }
}
