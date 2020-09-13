package com.ice2670.plasmaengine.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

/**
 * Created by Eric C on 7/6/2019.
 */
public class BlockTemperedGlass extends BlockBase {
    public BlockTemperedGlass(String name)
    {
        super(name, Material.GLASS);
        setCreativeTab(ValkyrienSkiesMod.VS_CREATIVE_TAB);
        setSoundType(SoundType.GLASS);
        setHardness(402.0F);
        setResistance(500.0F);
        setHarvestLevel("pickaxe", 3);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }


    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
