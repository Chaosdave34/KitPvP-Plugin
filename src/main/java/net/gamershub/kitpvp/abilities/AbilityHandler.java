package net.gamershub.kitpvp.abilities;

import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.StringArrayPersistentDataType;
import net.gamershub.kitpvp.Utils;
import net.gamershub.kitpvp.abilities.impl.FireballAbility;
import net.gamershub.kitpvp.abilities.impl.LightningAbility;
import net.gamershub.kitpvp.abilities.impl.ThunderstormAbility;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AbilityHandler implements Listener {
    public HashMap<String, Ability> abilities = new HashMap<>();

    public static Ability FIREBALL;
    public static Ability LIGHTNING;
    public static Ability THUNDERSTORM;

    public AbilityHandler() {
        FIREBALL = registerAbility(new FireballAbility());
        LIGHTNING = registerAbility(new LightningAbility());
        THUNDERSTORM = registerAbility(new ThunderstormAbility());
    }

    public Ability registerAbility(Ability ability) {
        Utils.registerEvents(ability);

        if (!abilities.containsKey(ability.getId())) {
            abilities.put(ability.getId(), ability);
        } else {
            throw new IllegalArgumentException("Ability ID already in use");
        }

        return ability;
    }

    public List<Ability> getItemAbilities(ItemStack itemStack) {
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(KitPvpPlugin.INSTANCE, "abilities");

        if (container.has(key)) {
            String[] abilities = container.get(key, new StringArrayPersistentDataType());
            if (abilities == null) return Collections.emptyList();

            List<Ability> abilityList = new ArrayList<>();

            for (String id : abilities) {
                abilityList.add(this.abilities.get(id));
            }

            return abilityList;

        }

        return Collections.emptyList();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return;

        PersistentDataContainer container = e.getItem().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(KitPvpPlugin.INSTANCE, "abilities");

        if (container.has(key)) {
            String[] abilities = container.get(key, new StringArrayPersistentDataType());
            if (abilities == null) return;
            for (String id : abilities) {
                if (this.abilities.containsKey(id)) {
                    Ability ability = this.abilities.get(id);
                    Player p = e.getPlayer();

                    switch (ability.getType()) {
                        case RIGHT_CLICK -> {
                            if (e.getAction().isRightClick()) ability.handleAbility(e);
                        }
                        case LEFT_CLICK -> {
                            if (e.getAction().isLeftClick()) ability.handleAbility(e);
                        }
                        case SNEAK_RIGHT_CLICK -> {
                            if (e.getAction().isRightClick() && p.isSneaking()) ability.handleAbility(e);
                        }
                        case SNEAK_LEFT_CLICK -> {
                            if (e.getAction().isLeftClick() && p.isSneaking()) ability.handleAbility(e);
                        }
                    }
                }
            }
        }
    }
}
