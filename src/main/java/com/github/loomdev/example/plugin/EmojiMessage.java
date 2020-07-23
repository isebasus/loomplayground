package com.github.loomdev.example.plugin;

import net.kyori.adventure.text.TextComponent;
import org.apache.logging.log4j.Logger;
import org.loomdev.api.entity.Entity;
import org.loomdev.api.entity.EntityType;
import org.loomdev.api.entity.decoration.ArmorStand;
import org.loomdev.api.event.entity.decoration.ArmorStandPlacedEvent;
import org.loomdev.api.entity.player.Player;
import org.loomdev.api.event.Subscribe;
import org.loomdev.api.event.player.connection.PlayerJoinedEvent;
import org.loomdev.api.event.player.PlayerMessagedEvent;
import org.loomdev.api.math.EulerAngle;
import org.loomdev.api.plugin.LoomPlugin;

import org.loomdev.api.plugin.Plugin;
import org.loomdev.api.server.Server;
import org.loomdev.api.util.ChatColor;
import com.github.loomdev.example.plugin.util.GetEmoji;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Optional;

@LoomPlugin(
        id = "loom-example-plugin",
        name = "Example Plugin",
        description = "An example plugin for Loom",
        version = "V0.1",
        authors = { "me", "you", "some other person" }
)
public class EmojiMessage implements Plugin {

    @Inject
    private Server server;

    @Inject
    private Logger logger;

    @Inject
    public EmojiMessage(Logger logger) {
        logger.info("Plugin load");
    }

    @Override
    public void onPluginEnable() {
        logger.info("Hello, enabling the plugin.");
        server.getCommandManager().register(this, new CreeperExplode());
        logger.info(this.server != null);
    }

    @Override
    public void onPluginDisable() {
        logger.info("Bye, disabling the plugin.");
    }

    @Subscribe
    public void onMessage(PlayerMessagedEvent event){
        Optional<TextComponent> message = event.getMessage();

        message.ifPresent(Player -> {
            String playerMessage = Player.content();
            if(playerMessage.contains(":")){
                String finalMessage = GetEmoji.emoji(playerMessage);
                event.setMessage(TextComponent.of(finalMessage).color(ChatColor.GOLD));
            } else {
                event.setMessage(TextComponent.of(playerMessage).color(ChatColor.GREEN));
            }
        });

    }

    @Subscribe
    public void onArmorStandSet(ArmorStandPlacedEvent event){

        final Optional<Player> playerOptional = event.getPlayer();
        playerOptional.ifPresent(player -> {

            Optional<Entity> entityOptional = player.getWorld().spawnEntity(EntityType.ARMOR_STAND, player.getLocation());
            entityOptional.ifPresent(entity -> { // Check if the entity exists (aka if it was spawned correctly)
                ArmorStand armorStand = (ArmorStand) entity; // Cast the entity to ArmorStand so you can use armor stand specific methods
                armorStand.setRightArmPose(EulerAngle.of(10, 50, 69)); // Euler angles are how armor stand positions are described
                armorStand.setLeftArmPose(EulerAngle.of(10, 50, 69));  // Euler angles are pitch, roll, and yaw (airplane shit)
                armorStand.setBasePlateHidden(true);
                armorStand.setArmsVisible(true);
                armorStand.setHeadPose(EulerAngle.of(123, 23, 90));

                armorStand.setCustomName(TextComponent.of("Sebastian is a toaster").color(ChatColor.GOLD)); // Any entity can have custom names
                armorStand.setCustomNameVisible(true); // Setting the custom name as visible makes their name tag display
            });

        });

    }

    @Subscribe
    public void onPlayerJoined(PlayerJoinedEvent event) {
        final Player player = event.getPlayer();
        event.setMessage(TextComponent.of("Hello, " + player.getName() + " your a fucking bitch").color(ChatColor.GOLD));
    }


}
