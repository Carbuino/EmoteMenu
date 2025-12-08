package ca.carbuino.extension.emotemenu.utils;

import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;

import org.geysermc.geyser.api.util.PlatformType;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.text.ChatColor;
import org.geysermc.geyser.text.GeyserLocale;

public class EmoteMenuUtils {
    /**
    * Build the emote menu form for the given session
    *
    * @param session The session to build the form for
    */
    public static SimpleForm buildEmoteForm(GeyserSession session) {
        SimpleForm.Builder builder = SimpleForm.builder()
            .title("Emote Menu")
            .button("Swap Offhand", FormImage.Type.PATH, "textures/ui/refresh.png")
            .button("Toggle Advanced Tooltips", FormImage.Type.PATH, "textures/ui/icon_recipe_equipment.png")
            .button("Advancements", FormImage.Type.PATH, "textures/ui/village_hero_effect.png")
            .button("Statistics", FormImage.Type.PATH, "textures/items/iron_pickaxe.png")
            .button("Geyser Settings", FormImage.Type.PATH, "textures/ui/settings_glyph_color_2x.png");

        builder.closedResultHandler(() -> {
        }).validResultHandler((response) -> {
            switch (response.clickedButtonId()) {
                case 1:
                    // Toggle Advanced Tooltips
                    if (checkSessionPermissions(session, "geyser.command.tooltips")) {
                        session.getGeyser().commandRegistry().runCommand(session, "geyser tooltips");
                    }
                    break;

                case 2:
                    // View Advancements
                    if (checkSessionPermissions(session, "geyser.command.advancements")) {
                        session.getGeyser().commandRegistry().runCommand(session, "geyser advancements");
                    }
                    break;

                case 3:
                    // View Statistics
                    if (checkSessionPermissions(session, "geyser.command.statistics")) {
                        session.getGeyser().commandRegistry().runCommand(session, "geyser statistics");
                    }
                    break;

                case 4:
                    // View Geyser Settings
                    if (checkSessionPermissions(session, "geyser.command.settings")) {
                        session.getGeyser().commandRegistry().runCommand(session, "geyser settings");
                    }
                    break;
                    
                default:
                    // Swap offhand
                    if (checkSessionPermissions(session, "geyser.command.offhand")) {
                        session.requestOffhandSwap();
                    }
                    break;

            }
        });

        return builder.build();
    }

    /**
    * Check if a session has permission to run a given command, or if Geyser is running standalone
    *
    * @param session The session to check permissions for
    * @param permission Permissions key to check
    */
    private static boolean checkSessionPermissions(GeyserSession session, String permission) {
        if (session.getGeyser().getBootstrap().platformType() == PlatformType.STANDALONE || session.hasPermission(permission)) {
            return true;
        }
        session.sendMessage(ChatColor.RED + GeyserLocale.getPlayerLocaleString("geyser.bootstrap.command.permission_fail", session.locale()));
        return false;
    }
}
