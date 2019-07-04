package com.ice2670.plasmaengine.tileentities;

import com.ice2670.plasmaengine.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import valkyrienwarfare.addon.control.nodenetwork.BasicForceNodeTileEntity;
import valkyrienwarfare.mod.common.math.Vector;


import javax.annotation.Nullable;

import static com.ice2670.plasmaengine.blocks.BlockPlasmaEngine.FACING;


/**
 * Created by Eric C on 6/26/2019.
 */
public class TileEntityPlasmaEngine extends BasicForceNodeTileEntity
{
    public int power;



    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public TileEntityPlasmaEngine() {
        super();
    }

    public TileEntityPlasmaEngine(Vector normalVeclocityUnoriented, boolean isForceOutputOriented, double maxThrust) {
        super(normalVeclocityUnoriented, isForceOutputOriented, maxThrust);

    }

    public void readFromNBT(NBTTagCompound nbt){
        super.readFromNBT(nbt);

        if (nbt.hasKey("energy"))
        {
            this.power = nbt.getInteger("power");
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("power", this.power);
        return nbt;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        super.getUpdateTag();
        return this.writeToNBT(new NBTTagCompound());
    }

    public void setThrust(World worldIn, BlockPos pos, IBlockState state){

        EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

        int x = this.pos.getX();
        int y = this.pos.getY();
        int z = this.pos.getZ();


        BlockPos pos2;



        if (enumfacing == EnumFacing.NORTH) {
            int i = 1;
            while (i < 20) {
                pos2 = new BlockPos(x, y, z + i);
                if (worldIn.getBlockState(pos2).getBlock() == BlockInit.BLOCK_PLASMACONTROLLER) {
                    i++;
                } else {
                    this.power = i;
                    world.notifyBlockUpdate(pos, getState(), getState(), 3);
                    break;
                }

            }
        }

        if (enumfacing == EnumFacing.SOUTH) {
            int i = 1;
            while (i < 20) {
                pos2 = new BlockPos(x, y, z - i);
                if (worldIn.getBlockState(pos2).getBlock() == BlockInit.BLOCK_PLASMACONTROLLER) {
                    i++;
                } else {
                    this.power = i;
                    world.notifyBlockUpdate(pos, getState(), getState(), 3);
                    break;
                }

            }
        }

        if (enumfacing == EnumFacing.EAST) {
            int i = 1;
            while (i < 20) {
                pos2 = new BlockPos(x-1, y, z);
                if (worldIn.getBlockState(pos2).getBlock() == BlockInit.BLOCK_PLASMACONTROLLER) {
                    i++;
                } else {
                    this.power = i;
                    world.notifyBlockUpdate(pos, getState(), getState(), 3);
                    break;
                }
            }
        }

        if (enumfacing == EnumFacing.WEST) {
            int i = 1;
            while (i < 20) {
                pos2 = new BlockPos(x+i, y, z);
                if (worldIn.getBlockState(pos2).getBlock() == BlockInit.BLOCK_PLASMACONTROLLER) {
                    i++;
                } else {
                    this.power = i;
                    world.notifyBlockUpdate(pos, getState(), getState(), 3);
                    break;
                }
            }
        }
    }

    public void displayPower(EntityPlayer playerIn){
        String str = Double.toString(super.maxThrust);
        if(world.isRemote) {
            playerIn.sendMessage(new TextComponentString("Thrust is "+str));
        }
    }

    @Override
    public void setMaxThrust(double maxThrust) {
        super.maxThrust = 10000D*power;
    }
}
