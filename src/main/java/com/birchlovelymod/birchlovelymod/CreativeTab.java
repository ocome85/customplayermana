package com.birchlovelymod.birchlovelymod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeTab extends CreativeModeTab {
    public CreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        ItemStack itemstack = new ItemStack(Items.BIRCH_LOG);
        return itemstack;
    }
}

