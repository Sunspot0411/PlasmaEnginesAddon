package com.ice2670.plasmaengine.Items;

import com.ice2670.plasmaengine.init.BlockInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static com.ice2670.plasmaengine.blocks.BlockPlasmaEngine.FACING;


public class ItemPlasmaEngine extends ItemBase
{
    public ItemPlasmaEngine(String name) {
        super(name);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (worldIn.getBlockState(pos).getBlock() == Blocks.GLOWSTONE)
        {
            worldIn.setBlockState(pos, BlockInit.BLOCK_PLASMAENGINE.getDefaultState().withProperty(FACING, facing.getOpposite()));
            itemstack.shrink(1);
            player.inventory.addItemStackToInventory(new ItemStack(Items.GLOWSTONE_DUST,4));
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player,
                               List<String> itemInformation, ITooltipFlag advanced) {
        itemInformation.add(TextFormatting.ITALIC + "" + TextFormatting.BLUE + I18n
                .format("tooltip.plasmaengines.item_plasmaengine_1"));
        itemInformation.add(TextFormatting.RED + "" + TextFormatting.ITALIC + I18n
                .format("tooltip.plasmaengines.item_plasmaengine_2"));
    }
}
