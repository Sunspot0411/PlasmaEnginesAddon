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
    public int enginepower2;
    private Vector normalVelocityUnoriented;

    private IBlockState getState() {
        return world.getBlockState(pos);
    }

    public TileEntityPlasmaEngine() {
        super();
    }

    public TileEntityPlasmaEngine(Vector normalVeclocityUnoriented, boolean isForceOutputOriented) {
        this();
        this.normalVelocityUnoriented = normalVeclocityUnoriented;
    }


    public void getEnginePower(World worldIn, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

        enginepower2 = enginepower;
    }


    @Override
    public Vector getForceOutputUnoriented(double secondsToApply, PhysicsObject physicsObject) {

        return this.normalVelocityUnoriented.getProduct(4000D*enginepower2 * secondsToApply);
    }
}
