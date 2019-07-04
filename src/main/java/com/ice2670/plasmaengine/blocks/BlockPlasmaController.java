package com.ice2670.plasmaengine.blocks;



import com.ice2670.plasmaengine.init.BlockInit;
import com.ice2670.plasmaengine.tileentities.TileEntityPlasmaControl;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


/**
 * Created by Eric C on 7/2/2019.
 */
public class BlockPlasmaController extends BlockBase{

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    int power=0;

    public BlockPlasmaController(String name, int num) {
        super(name, Material.CIRCUITS);
        setSoundType(SoundType.METAL);
        setHardness(101.0F);
        setResistance(140.0F);
        setHarvestLevel("pickaxe", 3);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.power=num;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityPlasmaControl();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {


        if (!worldIn.isRemote)
        {

            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing face = state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
            else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
            else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
            else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
            worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);

        }
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

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        TileEntityPlasmaControl plasmaControl = (TileEntityPlasmaControl)blockAccess.getTileEntity(pos);
        EnumFacing enumfacing = (EnumFacing) blockState.getValue(FACING);
        if (enumfacing == EnumFacing.NORTH && side == EnumFacing.NORTH){
            return plasmaControl.getPower();
        }
        else if (enumfacing == EnumFacing.SOUTH && side == EnumFacing.SOUTH){
            return plasmaControl.getPower();
        }
        else if (enumfacing == EnumFacing.EAST && side == EnumFacing.EAST){
            return plasmaControl.getPower();
        }
        else if (enumfacing == EnumFacing.WEST && side == EnumFacing.WEST){
            return plasmaControl.getPower();
        }
        else {
            return 0;
        }
    }


    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }



    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockInit.BLOCK_PLASMACONTROLLER);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(BlockInit.BLOCK_PLASMACONTROLLER);
    }






}
