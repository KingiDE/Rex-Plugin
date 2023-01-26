package de.kingi.rexplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


public class ItemBuilder {
        private ItemMeta itemMeta;
        private ItemStack itemStack;

        public ItemBuilder(Material mat){
            itemStack = new ItemStack(mat);
            itemMeta = itemStack.getItemMeta();
        }

        public ItemBuilder setDisplayname(String s){
            itemMeta.setDisplayName(s);
            return this;
        }

        public ItemBuilder setLore(String... s){
            itemMeta.setLore(Arrays.asList(s));
            return this;
        }

        public ItemBuilder build() {
            itemStack.setItemMeta(itemMeta);
            return this;
        }

        public ItemBuilder setToInventory(int slot, Inventory inv) {
            inv.setItem(slot, itemStack);
            return this;
        }

        public ItemBuilder addEnchant(Enchantment ench, int level) {
            itemMeta.addEnchant(ench, level, true);
            return this;
        }

    public ItemBuilder hideEnchants() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

        public ItemStack getItemStack() {
            return itemStack;
        }

    }
