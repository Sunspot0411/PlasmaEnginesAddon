package com.ice2670.plasmaengine.blocks;

import com.ice2670.plasmaengine.tileentities.TileEntityLargeGyroscopeStabilizer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.joml.Vector3dc;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;
import org.valkyrienskies.mod.common.block.IBlockTorqueProvider;
import org.valkyrienskies.mod.common.physics.PhysicsCalculations;
import org.valkyrienskies.mod.common.util.ValkyrienUtils;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLargeGyroscopeStabilizer extends BlockBase implements ITileEntityProvider, IBlockTorqueProvider
{
    public BlockLargeGyroscopeStabilizer(String name)
    {
        super(name, Material.IRON);
        setCreativeTab(ValkyrienSkiesMod.VS_CREATIVE_TAB);
        setSoundType(SoundType.METAL);
        setHardness(20.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 1);
    }

    /*public void addInformation(ItemStack stack, @Nullable World player, List<String> itemInformation, ITooltipFlag advanced) {
        itemInformation.add(TextFormatting.BLUE + I18n.format("tooltip.plasmaengines.gyroscope_stabilizer", new Object[0]));
    }*/

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLargeGyroscopeStabilizer();
    }

    public Vector3dc getTorqueInGlobal(PhysicsCalculations physicsCalculations, BlockPos pos) {
        TileEntity thisTile = ValkyrienUtils.getTileEntitySafe(physicsCalculations.getParent().getWorld(), pos);
        if (thisTile instanceof TileEntityLargeGyroscopeStabilizer) {
            TileEntityLargeGyroscopeStabilizer tileGyroscope = (TileEntityLargeGyroscopeStabilizer)thisTile;
            return tileGyroscope.getTorqueInGlobal(physicsCalculations, pos);
        } else {
            return null;
        }
    }
}

