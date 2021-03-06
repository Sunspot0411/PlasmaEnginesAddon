package com.ice2670.plasmaengine.util.handlers;

import com.ice2670.plasmaengine.tileentities.TileEntityLargeGyroscopeDampener;
import com.ice2670.plasmaengine.tileentities.TileEntityLargeGyroscopeStabilizer;
import com.ice2670.plasmaengine.tileentities.TileEntityPlasmaControl;
import com.ice2670.plasmaengine.tileentities.TileEntityPlasmaEngine;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Eric C on 6/25/2019.
 */
public class TileEntityHandler {

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityPlasmaEngine.class, "tileentityplasmaengine");
        GameRegistry.registerTileEntity(TileEntityPlasmaControl.class, "tileentityplasmacontrol");
        GameRegistry.registerTileEntity(TileEntityLargeGyroscopeStabilizer.class, "tileentitylargegyroscopestabilizer");
        GameRegistry.registerTileEntity(TileEntityLargeGyroscopeDampener.class, "tileentitylargegyroscopedampener");
    }
}
