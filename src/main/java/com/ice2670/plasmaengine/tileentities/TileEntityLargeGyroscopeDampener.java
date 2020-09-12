package com.ice2670.plasmaengine.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.mod.common.physics.PhysicsCalculations;
import valkyrienwarfare.api.TransformType;

public class TileEntityLargeGyroscopeDampener extends TileEntity
{
    private static final Vector3dc GRAVITY_UP = new Vector3d(0.0D, 1.0D, 0.0D);
    private double maximumTorque = 500000.0D;

    public TileEntityLargeGyroscopeDampener() {
    }

    public Vector3dc getTorqueInGlobal(PhysicsCalculations physicsCalculations, BlockPos pos) {
        Vector3d shipLevelNormal = new Vector3d(GRAVITY_UP);
        physicsCalculations.getParent().getShipTransformationManager().getCurrentPhysicsTransform().transformDirection(shipLevelNormal, TransformType.SUBSPACE_TO_GLOBAL);
        shipLevelNormal.dot(new Vector3d(physicsCalculations.getAngularVelocity()));
        Vector3d angularChangeAllowed = shipLevelNormal.mul(shipLevelNormal.dot(new Vector3d(physicsCalculations.getAngularVelocity())), new Vector3d());
        Vector3d angularVelocityToDamp = (new Vector3d(physicsCalculations.getAngularVelocity())).sub(angularChangeAllowed);
        Vector3d dampingTorque = angularVelocityToDamp.mul(physicsCalculations.getPhysicsTimeDeltaPerPhysTick());
        Vector3d dampingTorqueWithRespectToInertia = physicsCalculations.getPhysMOITensor().transform(dampingTorque);
        double dampingTorqueRespectMagnitude = dampingTorqueWithRespectToInertia.length();
        if (dampingTorqueRespectMagnitude > this.maximumTorque) {
            dampingTorqueWithRespectToInertia.mul(this.maximumTorque / dampingTorqueRespectMagnitude);
        }

        return dampingTorqueWithRespectToInertia.mul(-1.0D);
    }

}
