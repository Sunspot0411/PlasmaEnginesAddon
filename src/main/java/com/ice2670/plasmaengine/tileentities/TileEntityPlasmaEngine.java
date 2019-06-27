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
    public int enginepower = 1;
    public double enginepower2;
    private Vector normalVelocityUnoriented;

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public TileEntityPlasmaEngine() {
        super();
    }

    public TileEntityPlasmaEngine(Vector normalVeclocityUnoriented, boolean isForceOutputOriented, double maxThrust) {
        super(normalVeclocityUnoriented,isForceOutputOriented,maxThrust);
    }

    public void setEnginepower2(World worldIn, BlockPos pos, IBlockState state){
        this.enginepower2=enginepower;
    }




    @Override
    public double getThrustMultiplierGoal() {
        return enginepower2;
    }
}
