package net.gamershub.kitpvp.gui.impl;

import net.gamershub.kitpvp.ExtendedPlayer;
import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.gui.Gui;
import net.gamershub.kitpvp.gui.GuiHandler;
import net.gamershub.kitpvp.gui.InventoryClickHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CosmeticsGui extends Gui {
    public CosmeticsGui() {
        super(4, Component.text("Cosmetics"), true);
    }

    @Override
    public @NotNull Inventory build(Player p, Inventory inventory) {
        ExtendedPlayer extendedPlayer = KitPvpPlugin.INSTANCE.getExtendedPlayer(p);

        String selectedProjectileTrail = extendedPlayer.getProjectileTrailId() == null ? "None" : KitPvpPlugin.INSTANCE.getCosmeticHandler().getProjectileTrails().get(extendedPlayer.getProjectileTrailId()).getName();
        ItemStack projectileTrailButton = createItemStack(Material.ARROW, "Projectile Trails", true);
        projectileTrailButton.lore(List.of(Component.text("Selected: " + selectedProjectileTrail, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        inventory.setItem(11, projectileTrailButton);

        String selectedKillEffect = extendedPlayer.getKillEffectId() == null ? "None" : KitPvpPlugin.INSTANCE.getCosmeticHandler().getKillEffects().get(extendedPlayer.getKillEffectId()).getName();
        ItemStack killEffectButton = createItemStack(Material.IRON_SWORD, "Kill Effects", true);
        killEffectButton.lore(List.of(Component.text("Selected: " + selectedKillEffect, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)));
        inventory.setItem(15, killEffectButton);

        inventory.setItem(31, createItemStack(Material.BARRIER, "Close", true));
        return inventory;
    }

    @InventoryClickHandler(slot = 11)
    public void onProjectileTrails(InventoryClickEvent e) {
        GuiHandler.PROJECTILE_TRAILS.show((Player) e.getWhoClicked());
    }

    @InventoryClickHandler(slot = 15)
    public void onKillEffects(InventoryClickEvent e) {
        GuiHandler.KILL_EFFECTS.show((Player) e.getWhoClicked());
    }

    @InventoryClickHandler(slot = 31)
    public void onCloseButton(InventoryClickEvent e) {
        e.getInventory().close();
    }
}
