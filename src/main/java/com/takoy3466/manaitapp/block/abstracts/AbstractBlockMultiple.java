package com.takoy3466.manaitapp.block.abstracts;

import com.takoy3466.manaitapp.core.interfaces.IMultiple;
import com.takoy3466.manaitapp.core.ManaitaTier;
import net.minecraft.world.level.block.Block;

public class AbstractBlockMultiple extends Block implements IMultiple {
    private final ManaitaTier manaitaTier;
    public AbstractBlockMultiple(Properties properties, ManaitaTier manaitaTier) {
        super(properties);
        this.manaitaTier = manaitaTier;
    }

    @Override
    public int getMultiple() {
        return manaitaTier.getMultiple();
    }

    @Override
    public ManaitaTier getManaitaTier() {
        return manaitaTier;
    }

}
