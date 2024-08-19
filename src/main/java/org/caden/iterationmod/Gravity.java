package org.caden.iterationmod;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.caden.iterationmod.blocks.GravityGenerator;

public class Gravity {

    public static final EntityAttribute GRAVITY_ATTRIBUTE = new ClampedEntityAttribute(
            "attribute.iteration.generic.gravity",
            1.0,
            -1024.0,
            1024.0
    ).setTracked(true);
    public static void registerAttributes() {
            Registry.register(Registries.ATTRIBUTE, new Identifier("iteration", "gravity"), GRAVITY_ATTRIBUTE);
    }
}
