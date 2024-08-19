package org.caden.iterationmod.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.caden.iterationmod.Gravity;
import org.caden.iterationmod.IterationMod;

import java.util.List;

public class GravityGeneratorEntity extends BlockEntity {
    public GravityGeneratorEntity(BlockPos pos, BlockState state) {
        super(IterationMod.GRAVITY_GENERATOR_BLOCK_ENTITY,pos, state);
    }

    public static List<LivingEntity> getEntitiesWithinDistance(World world, BlockPos pos, double distance) {
        Box box = new Box(
                pos.getX() - distance, pos.getY() - distance, pos.getZ() - distance,
                pos.getX() + distance, pos.getY() + distance, pos.getZ() + distance
        );

        return world.getEntitiesByClass(LivingEntity.class, box, entity -> true);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GravityGeneratorEntity be) {
        if (!world.isClient()) {
            List<LivingEntity> entities = getEntitiesWithinDistance(world, pos, 25);
            for (LivingEntity entity : entities) {
                IterationMod.ServerLogger.info(entity.getName());
                EntityAttributeInstance attributeInstance = entity.getAttributeInstance(Gravity.GRAVITY_ATTRIBUTE);
                IterationMod.ServerLogger.info(attributeInstance);
                assert attributeInstance != null;
                attributeInstance.setBaseValue(Math.max(0,Math.min(1, entity.getPos().distanceTo(pos.toCenterPos())/12.5-1)));
            }
        }
    }
}
