package com.takoy3466.manaitapp.item;

import com.takoy3466.manaitapp.dataComponent.CrushedManaitaData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.world.item.Item;

public class CrushedManaita extends Item {
    public static int MULTIPLE = 64;

    public CrushedManaita() {
        super(new Item.Properties().component(DataInit.CRUSHED_DATA, new CrushedManaitaData(MULTIPLE)));
    }
}
