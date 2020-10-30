package com.ice2670.plasmaengine.blocks;

import com.ice2670.plasmaengine.init.BlockInit;
import com.ice2670.plasmaengine.tileentities.TileEntityPlasmaEngine;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.addon.control.ValkyrienSkiesControl;
import org.valkyrienskies.addon.control.nodenetwork.BasicForceNodeTileEntity;
import org.valkyrienskies.mod.common.block.IBlockForceProvider;
import org.valkyrienskies.mod.common.ships.ship_world.PhysicsObject;
import org.valkyrienskies.mod.common.util.JOML;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockPlasmaEngine extends BlockBase implements IBlockForceProvider
{

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockPlasmaEngine(String name)
    {
        super(name, Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(1000.0F);
        setResistance(500.0F);
        setHarvestLevel("pickaxe", 3);
        setLightLevel(1.0F);

    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = state.getValue(FACING).getIndex();
        return i;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        new Vector3d(1.0D, 0.0D, 0.0D);
        return new TileEntityPlasmaEngine(JOML.convertTo3d(((EnumFacing)state.getValue(FACING)).getOpposite().getDirectionVec()) , true, 40000D);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockInit.BLOCK_PLASMAENGINE);
    }

    @Nullable
    @Override
    public Vector3dc getBlockForceInShipSpace(World world, BlockPos blockPos, IBlockState iBlockState, PhysicsObject physicsObject, double v) {
        Vector3d acting = new Vector3d(0.0D, 0.0D, 0.0D);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileEntityPlasmaEngine) {
            ((TileEntityPlasmaEngine)tileEntity).displayPower(playerIn);
        }

        ItemStack heldItem = playerIn.getHeldItem(hand);


        if (heldItem == null){
            return false;}
        else{
            Item item = heldItem.getItem();
            if (item == ValkyrienSkiesControl.INSTANCE.vsWrench){
                worldIn.setBlockState(pos, BlockInit.BLOCK_PLASMAENGINE.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, playerIn).getOpposite()));
            }
        }
        return true;
    }

    @Nullable
    @Override
    public Vector3dc getCustomBlockForcePosition(World world, BlockPos pos, IBlockState state, PhysicsObject physicsObject, double secondsToApply) {
        TileEntity engineTile = world.getTileEntity(pos);

        if (engineTile instanceof BasicForceNodeTileEntity) {
            Vector3dc forceOutputNormal = ((BasicForceNodeTileEntity) engineTile).getForceOutputNormal(secondsToApply, physicsObject);
            return new Vector3d((double)pos.getX() + 0.5D - forceOutputNormal.x() * 0.75D, (double)pos.getY() + 0.5D - forceOutputNormal.y() * 0.75D, (double)pos.getZ() + 0.5D - forceOutputNormal.z() * 0.75D);
        } else {
            return null;
        }
    }

    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockInit.BLOCK_PLASMAENGINE);
    }

}
