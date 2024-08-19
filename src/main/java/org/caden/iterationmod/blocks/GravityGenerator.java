package org.caden.iterationmod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.caden.iterationmod.Gravity;
import org.caden.iterationmod.entities.GravityGeneratorEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class GravityGenerator extends BlockWithEntity {

    public GravityGenerator(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GravityGeneratorEntity(pos,state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof GravityGeneratorEntity) {
                GravityGeneratorEntity.tick(world1, pos, state1, (GravityGeneratorEntity) blockEntity);
            }
        };
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        List<LivingEntity> entities = GravityGeneratorEntity.getEntitiesWithinDistance(world, pos, 25);
        for (LivingEntity entity : entities) {
            EntityAttributeInstance attributeInstance = entity.getAttributeInstance(Gravity.GRAVITY_ATTRIBUTE);
            assert attributeInstance != null;
            attributeInstance.setBaseValue(1);
        }
        return super.onBreak(world, pos, state, player);
    }
}
