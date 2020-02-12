package com.ice2670.plasmaengine.blocks;

import com.ice2670.plasmaengine.init.BlockInit;
import com.ice2670.plasmaengine.tileentities.TileEntityPlasmaEngine;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.valkyrienskies.addon.control.nodenetwork.BasicForceNodeTileEntity;
import org.valkyrienskies.mod.common.block.IBlockForceProvider;
import org.valkyrienskies.mod.common.coordinates.VectorImmutable;
import org.valkyrienskies.mod.common.entity.PhysicsWrapperEntity;
import org.valkyrienskies.mod.common.math.Vector;
import org.valkyrienskies.mod.common.physics.management.PhysicsObject;


import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Eric C on 6/26/2019.
 */
public class BlockPlasmaEngine extends BlockBase implements IBlockForceProvider, ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");


    public BlockPlasmaEngine(String name)
    {
        super(name, Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(500.0F);
        setResistance(140.0F);
        setHarvestLevel("pickaxe", 3);
        setLightLevel(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockInit.BLOCK_PLASMAENGINE);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockInit.BLOCK_PLASMAENGINE);
    }

    @Nullable
    @Override
    public Vector getBlockForceInShipSpace(World world, BlockPos blockPos, IBlockState iBlockState, PhysicsObject physicsObject, double v) {
        EnumFacing facing = iBlockState.getValue(FACING);
        Vector acting = new Vector(0.0D, 0.0D, 0.0D);
        if (!world.isBlockPowered(blockPos)) {
            return acting;
        } else {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if(tileEntity instanceof TileEntityPlasmaEngine) {
                ((TileEntityPlasmaEngine)tileEntity).setThrust(world, blockPos, iBlockState);
                ((TileEntityPlasmaEngine)tileEntity).setMaxThrust(1D);
                ((TileEntityPlasmaEngine)tileEntity).updateTicksSinceLastRecievedSignal();
                ((TileEntityPlasmaEngine)tileEntity).setThrustMultiplierGoal(1D);
                return ((TileEntityPlasmaEngine)tileEntity).getForceOutputUnoriented(v, physicsObject);
            } else {
                return acting;
            }
        }
    }

    @Override
    public boolean shouldLocalForceBeRotated(World world, BlockPos blockPos, IBlockState iBlockState, double v) {
        return true;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            IBlockState up = worldIn.getBlockState(pos.up());
            IBlockState down = worldIn.getBlockState(pos.down());
            EnumFacing face = state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
            else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
            else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
            else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
            else if (face == EnumFacing.UP && up.isFullBlock() && !down.isFullBlock()) face = EnumFacing.DOWN;
            else if (face == EnumFacing.DOWN && down.isFullBlock() && !up.isFullBlock()) face = EnumFacing.UP;
            worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        new Vector(1.0D, 0.0D, 0.0D);
        IBlockState state = this.getStateFromMeta(meta);
        return new TileEntityPlasmaEngine(new Vector(state.getValue(FACING)), true, 40000D);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityPlasmaEngine) {
            ((TileEntityPlasmaEngine)tileEntity).displayPower(playerIn);
        }
        return true;
    }


    public Vector getCustomBlockForcePosition(World world, BlockPos pos, IBlockState state, Entity shipEntity, double secondsToApply) {
        TileEntity engineTile = world.getTileEntity(pos);

        if (engineTile instanceof BasicForceNodeTileEntity) {
            VectorImmutable forceOutputNormal = ((BasicForceNodeTileEntity) engineTile).getForceOutputNormal(secondsToApply, ((PhysicsWrapperEntity) shipEntity).getPhysicsObject());
            return new Vector((double)pos.getX() + 0.5D - forceOutputNormal.getX() * 0.75D, (double)pos.getY() + 0.5D - forceOutputNormal.getY() * 0.75D, (double)pos.getZ() + 0.5D - forceOutputNormal.getZ() * 0.75D);
        } else {
            return null;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }



    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byIndex(meta);
        if(facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.QUARTZ;
    }

}
