package com.ice2670.plasmaengine.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.mod.common.physics.PhysicsCalculations;
import valkyrienwarfare.api.TransformType;

public class TileEntityLargeGyroscopeStabilizer extends TileEntity
{
    public static final double MAXIMUM_TORQUE = 1500000000;
    private static final Vector3dc GRAVITY_UP = new Vector3d(0.0D, 1.0D, 0.0D);

    public TileEntityLargeGyroscopeStabilizer(){

    }

    public Vector3dc getTorqueInGlobal(PhysicsCalculations physicsCalculations, BlockPos pos) {
        Vector3d shipLevelNormal = new Vector3d(GRAVITY_UP);
        physicsCalculations.getParent().getShipTransformationManager().getCurrentPhysicsTransform().transformDirection(shipLevelNormal, TransformType.SUBSPACE_TO_GLOBAL);
        Vector3d torqueDir = GRAVITY_UP.cross(shipLevelNormal, new Vector3d());
        if (torqueDir.lengthSquared() < .00000000001) {
            // The ship is already level, don't try to divide by 0
            return new Vector3d();
        }

        double angleBetween = Math.toDegrees(GRAVITY_UP.angle(shipLevelNormal));
        torqueDir.normalize();
        double torquePowerFactor = angleBetween / 5.0D;
        torquePowerFactor = Math.max(Math.min(1.0D, torquePowerFactor), 0.0D);
        return torqueDir.mul(MAXIMUM_TORQUE * torquePowerFactor * physicsCalculations.getPhysicsTimeDeltaPerPhysTick() * -1.0D);
    }
}
