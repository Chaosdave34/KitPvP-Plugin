package net.gamershub.kitpvp.fakeplayer.impl;

import net.gamershub.kitpvp.fakeplayer.FakePlayer;
import net.gamershub.kitpvp.gui.GuiHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CosmeticsFakePlayer extends FakePlayer {
    public CosmeticsFakePlayer() {
        super("Cosmetics", new Location(Bukkit.getWorld("world"), 0.5, 100, 10.5), -180, 0, true);
    }

    @Override
    public void onAttack(Player p) {
        GuiHandler.COSMETICS.show(p);
    }
}