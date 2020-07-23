package com.github.loomdev.example.plugin;

import net.kyori.adventure.text.TextComponent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.loomdev.api.command.Command;
import org.loomdev.api.command.CommandSource;
import org.loomdev.api.entity.Entity;
import org.loomdev.api.entity.EntityType;
import org.loomdev.api.entity.decoration.ArmorStand;
import org.loomdev.api.entity.mob.Creeper;
import org.loomdev.api.math.EulerAngle;
import org.loomdev.api.entity.player.Player;
import org.loomdev.api.util.ChatColor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CreeperExplode extends Command{

    public CreeperExplode() {
        super("creeper");
    }

    @Override
    public void execute(@NonNull CommandSource commandSource, String[] strings) {
        Player player = (Player) commandSource;

        setDescription("Call this command to blow someone up on the server");
        commandSource.sendMessage(TextComponent.of("Wishes are wishes").color(ChatColor.RED));

        Optional<Entity> entityOptional = player.getWorld().spawnEntity(EntityType.CREEPER, player.getLocation());
        entityOptional.ifPresent(entity -> { // Check if the entity exists (aka if it was spawned correctly)
            Creeper creeper = (Creeper) entity; // Cast the entity to ArmorStand so you can use armor stand specific methods
            creeper.setCharged(true);

            creeper.setCustomName(TextComponent.of("Suicide God").color(ChatColor.GOLD)); // Any entity can have custom names
            creeper.setCustomNameVisible(true);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            creeper.explode();
        });
    }



}
