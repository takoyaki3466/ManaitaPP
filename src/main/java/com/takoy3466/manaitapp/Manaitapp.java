package com.takoy3466.manaitapp;

import com.mojang.logging.LogUtils;
import com.takoy3466.manaitapp.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
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
        AttachmentsInit.ATTACHMENTS.register(bus);
    }
}
