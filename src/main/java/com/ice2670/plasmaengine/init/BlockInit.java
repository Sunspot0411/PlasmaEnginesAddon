package com.ice2670.plasmaengine.init;

import com.ice2670.plasmaengine.blocks.BlockPlasmaController;
import com.ice2670.plasmaengine.blocks.BlockPlasmaEngine;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric C on 6/25/2019.
 */
public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block BLOCK_PLASMAENGINE = new BlockPlasmaEngine("plasma_engine");

    public static final Block BLOCK_PLASMACONTROLLER = new BlockPlasmaController("plasma_controller", 0);

}
