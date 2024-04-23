package ca.carbuino.extension.emotemenu.utils;

import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.CustomForm;
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
            .button("Execute Command", FormImage.Type.PATH, "textures/blocks/command_block.png")
            .button("Geyser Settings", FormImage.Type.PATH, "textures/ui/settings_glyph_color_2x.png");

        builder.closedResultHandler(() -> {
            return;
        }).validResultHandler((response) -> {
            switch (response.clickedButtonId()) {
                case 1:
                    // Toggle Advanced Tooltips
                    if (checkSessionPermissions(session, "geyser.command.tooltips") == true) {
                        session.getGeyser().commandManager().runCommand(session, "geyser tooltips");
                    }
                    break;

                case 2:
                    // View Advancements
                    if (checkSessionPermissions(session, "geyser.command.advancements") == true) {
                        session.getGeyser().commandManager().runCommand(session, "geyser advancements");
                    }
                    break;

                case 3:
                    // View Statistics
                    if (checkSessionPermissions(session, "geyser.command.statistics") == true) {
                        session.getGeyser().commandManager().runCommand(session, "geyser statistics");
                    }
                    break;

                case 4:
                    // Execute Command Menu
                    session.sendForm(EmoteMenuUtils.buildCommandForm(session));
                    break;

                    
                case 5:
                    // View Geyser Settings
                    if (checkSessionPermissions(session, "geyser.command.settings") == true) {
                        session.getGeyser().commandManager().runCommand(session, "geyser settings");
                    }
                    break;
                    
                default:
                    // Swap offhand
                    if (checkSessionPermissions(session, "geyser.command.offhand") == true) {
                        session.getGeyser().commandManager().runCommand(session, "geyser offhand");
                    }
                    break;

            };
        });

        return builder.build();
    };
    
    /**
    * Check if a session has permission to run a given command, or if Geyser is running standalone
    *
    * @param session The session to check permissions for
    * @param permission Permissions key to check
    */
    private static boolean checkSessionPermissions(GeyserSession session, String permission) {
        if (session.getGeyser().getPlatformType() == PlatformType.STANDALONE || session.hasPermission(permission)) {
            return true;
        };
        session.sendMessage(ChatColor.RED + GeyserLocale.getPlayerLocaleString("geyser.bootstrap.command.permission_fail", session.locale()));
        return false;
    };

    /**
    * Build the form for command menu option for the given session
    *
    * @param session The session to build the form for
    */
    private static CustomForm buildCommandForm(GeyserSession session) {
        CustomForm.Builder builder = CustomForm.builder()
            .title("Emote Menu - Execute Command")
            .input("Command", "Enter a command");

        builder.closedResultHandler(() -> {
            session.sendForm(EmoteMenuUtils.buildEmoteForm(session));
            
        }).validResultHandler((response) -> {
            session.sendCommand(response.asInput(0));
            
        });

        return builder.build();
    };
};
