package com.takoy3466.datgen;

import com.takoy3466.manaitapp.Manaitapp;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = Manaitapp.MOD_ID)
public class ManaitaDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ManaitaRecipe>) packOutput -> new ManaitaRecipe(packOutput, event.getLookupProvider())
        );

        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<LootTableProvider>) packOutput -> new LootTableProvider(
                        packOutput,
                        Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(ManaitaBlockLootTable::new, LootContextParamSets.BLOCK)),
                        event.getLookupProvider())
        );
    }
}
