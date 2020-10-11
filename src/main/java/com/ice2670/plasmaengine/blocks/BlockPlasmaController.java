package com.ice2670.plasmaengine.blocks;

import com.ice2670.plasmaengine.init.BlockInit;
import com.ice2670.plasmaengine.tileentities.TileEntityPlasmaControl;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockPlasmaController extends BlockBase
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    int power=0;

    public BlockPlasmaController(String name, int num)
    {
        super(name, Material.IRON);
        setCreativeTab(ValkyrienSkiesMod.VS_CREATIVE_TAB);
        setSoundType(SoundType.METAL);
        setHardness(20.0F);
        setResistance(20.0F);
        setHarvestLevel("pickaxe", 3);
        this.power=num;
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
        return new TileEntityPlasmaControl();
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
        else if (enumfacing == EnumFacing.UP && side == EnumFacing.UP){
            return plasmaControl.getPower();
        }
        else if (enumfacing == EnumFacing.DOWN && side == EnumFacing.DOWN){
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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player,
                               List<String> itemInformation, ITooltipFlag advanced) {
        itemInformation.add(TextFormatting.ITALIC + "" + TextFormatting.BLUE + I18n
                .format("tooltip.plasmaengines.plasam_controller"));
    }
}
