package ca.carbuino.extension.emotemenu;

import ca.carbuino.extension.emotemenu.utils.EmoteMenuUtils;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.configuration.EmoteOffhandWorkaroundOption;
import org.geysermc.geyser.session.GeyserSession;

import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreInitializeEvent;
import org.geysermc.geyser.api.event.bedrock.ClientEmoteEvent;
import org.geysermc.geyser.api.event.bedrock.SessionJoinEvent;
import org.geysermc.geyser.api.extension.Extension;

public class EmoteMenu implements Extension {

    boolean gotWorkaroundOption = false;

    @Subscribe
    public void onPreInit(GeyserPreInitializeEvent event) {
        this.logger().info("Loading Emote Menu...");
    }

    @Subscribe
    public void onPostInit(GeyserPostInitializeEvent event) {
        this.logger().info("The Emote Menu ready to roll!");
    }

    @Subscribe
    public void onJoin(SessionJoinEvent event) {
        if (gotWorkaroundOption == false) {
            GeyserSession session = (GeyserSession) event.connection();

            EmoteOffhandWorkaroundOption option = session.getGeyser().getConfig().getEmoteOffhandWorkaround();
            if (option == EmoteOffhandWorkaroundOption.NO_EMOTES) {
                this.logger().warning("Your off-hand workaround is set to 'no-emotes' in the Geyser config, this must be changed to either 'disabled' or 'emotes-and-offhand' if you want to use the Emote Menu.");
            }
        }
        gotWorkaroundOption = true;
    };

    @Subscribe
    public void onEmoteEvent(ClientEmoteEvent event) {
        GeyserSession session = (GeyserSession) event.connection();

        // Build and Send the Emote Menu
        session.sendForm(EmoteMenuUtils.buildEmoteForm(session)); 

    }
}
