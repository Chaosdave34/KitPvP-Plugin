package net.gamershub.kitpvp.abilities.impl.creeper;

import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.abilities.Ability;
import net.gamershub.kitpvp.abilities.AbilityType;
import net.gamershub.kitpvp.persistentdatatypes.StringArrayPersistentDataType;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExplodeAbility extends Ability {
    public ExplodeAbility() {
        super("explode", "Explode", AbilityType.SNEAK, 25);
    }

    @Override
    public @NotNull List<Component> getDescription() {
        return createSimpleDescription("Cause an explosion.");
    }

    @Override
    public boolean onAbility(Player p) {
        p.getLocation().createExplosion(p, 3, false);
        return true;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Player p) {
            for (ItemStack armorContent : p.getInventory().getArmorContents()) {
                if (armorContent == null) continue;

                PersistentDataContainer container = armorContent.getItemMeta().getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(KitPvpPlugin.INSTANCE, "abilities");

                if (container.has(key)) {
                    String[] abilities = container.get(key, new StringArrayPersistentDataType());
                    if (abilities == null) return;

                    for (String id : abilities) {
                        if (this.id.equals(id)) {
                            e.blockList().removeIf(block -> !block.hasMetadata("placed_by_player"));
                            return;
                        }
                    }

                }
            }
        }
    }
}
