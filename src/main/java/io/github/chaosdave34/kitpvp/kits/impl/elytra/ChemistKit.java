package io.github.chaosdave34.kitpvp.kits.impl.elytra;

import io.github.chaosdave34.kitpvp.kits.ElytraKit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class ChemistKit extends ElytraKit {
    public ChemistKit() {
        super("elytra_chemist", "Chemist", Material.POTION);
    }

    @Override
    public ItemStack[] getInventoryContent() {
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);

        return new ItemStack[]{
                bow,
        };
    }

    @Override
    public ItemStack[] getKillRewards() {
        ItemStack potion = new ItemStack(Material.SPLASH_POTION, 5);
        potion.editMeta(PotionMeta.class, potionMeta -> potionMeta.setBasePotionType(PotionType.STRONG_HARMING));

        return new ItemStack[]{
                potion,
        };
    }
}
