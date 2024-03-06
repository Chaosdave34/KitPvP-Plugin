package net.gamershub.kitpvp.abilities.impl.provoker;

import net.gamershub.kitpvp.ExtendedPlayer;
import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.Utils;
import net.gamershub.kitpvp.abilities.Ability;
import net.gamershub.kitpvp.abilities.AbilityType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpookAbility extends Ability {
    public SpookAbility() {
        super("spook", "Spook", AbilityType.RIGHT_CLICK, 30);
    }

    @Override
    public @NotNull List<Component> getDescription() {
        return createSimpleDescription(
                "Places a pumpkin on the",
                "player you are looking at",
                "in a 20 block radius."
        );
    }

    @Override
    public boolean onAbility(Player p) {
        Player target = Utils.getTargetEntity(p, 20, Player.class, true);
        if (target != null) {

            if (KitPvpPlugin.INSTANCE.getExtendedPlayer(target).getGameState() == ExtendedPlayer.GameState.SPAWN)
                return false;

            ItemStack pumpkin = new ItemStack(Material.CARVED_PUMPKIN);
            pumpkin.addEnchantment(Enchantment.BINDING_CURSE, 1);
            target.getInventory().setHelmet(pumpkin);

            Bukkit.getScheduler().runTaskLater(KitPvpPlugin.INSTANCE, () -> {
                ExtendedPlayer extendedTargetPlayer = KitPvpPlugin.INSTANCE.getExtendedPlayer(target);
                target.getInventory().setHelmet(extendedTargetPlayer.getSelectedKit().getHeadContent());
            }, 10 * 20);

            return true;
        }
        return false;
    }
}