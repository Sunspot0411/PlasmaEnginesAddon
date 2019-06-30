package com.ice2670.plasmaengine.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import valkyrienwarfare.addon.control.nodenetwork.BasicForceNodeTileEntity;

import valkyrienwarfare.math.Vector;
import valkyrienwarfare.physics.management.PhysicsObject;
import valkyrienwarfare.physics.management.PhysicsWrapperEntity;

import static com.ice2670.plasmaengine.blocks.BlockPlasmaEngine.FACING;


/**
 * Created by Eric C on 6/26/2019.
 */
public class TileEntityPlasmaEngine extends BasicForceNodeTileEntity
{
    private World entityworld;
    private BlockPos entitypos;
    private IBlockState blockstate;

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public TileEntityPlasmaEngine() {
        super();
    }

    public TileEntityPlasmaEngine(Vector normalVeclocityUnoriented, boolean isForceOutputOriented, double maxThrust) {
        super(normalVeclocityUnoriented, isForceOutputOriented, maxThrust);

    }

    public void setBlockPos(World worldIn, BlockPos pos, IBlockState state){
        this.entityworld = worldIn;
        this.entitypos = pos;
        this.blockstate = state;
    }

    @Override
    public void setMaxThrust(double maxThrust) {
        super.maxThrust = maxThrust;

    }
}
