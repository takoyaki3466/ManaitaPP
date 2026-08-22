package com.takoy3466.manaitapp;

import com.mojang.logging.LogUtils;
import com.takoy3466.datgen.ManaitaDatagen;
import com.takoy3466.manaitapp.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(Manaitapp.MOD_ID)
public class Manaitapp {

    public static final String MOD_ID = "manaitapp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Manaitapp(IEventBus bus, ModContainer modContainer) {
        ItemsInit.ITEMS.register(bus);
        BlocksInit.BLOCKS.register(bus);
        BlockEntitiesInit.BLOCK_ENTITIES.register(bus);
        TabsInit.TABS.register(bus);
        DataInit.DATA_COMPONENT.register(bus);
        SerializersInit.SERIALIZERS.register(bus);

    }
}
