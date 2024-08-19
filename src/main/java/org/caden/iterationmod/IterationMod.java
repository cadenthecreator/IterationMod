package org.caden.iterationmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.caden.iterationmod.blocks.GravityGenerator;
import org.caden.iterationmod.entities.GravityGeneratorEntity;

public class IterationMod implements ModInitializer {
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of("iteration", name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static final Logger ServerLogger = LogManager.getLogger("Iteration(Server)");
    private static final GravityGenerator block = new GravityGenerator(FabricBlockSettings.create().solid().hardness(1));

    public static final Block GRAVITY_GENERATOR_BLOCK = register(block,"gravity_generator",true);

    public static final BlockEntityType<GravityGeneratorEntity> GRAVITY_GENERATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier("iteration_mod", "gravity_generator"),                                           
            BlockEntityType.Builder.create(GravityGeneratorEntity::new, GRAVITY_GENERATOR_BLOCK).build()
    );

    @Override
    public void onInitialize() {
        Gravity.registerAttributes();
        ServerLogger.info("Registration Complete");
    }
}
