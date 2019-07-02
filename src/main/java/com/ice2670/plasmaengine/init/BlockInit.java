package com.ice2670.plasmaengine.init;

import com.ice2670.plasmaengine.blocks.BlockBase;
import com.ice2670.plasmaengine.blocks.BlockPlasmaEngine;
import net.minecraft.block.Block;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import valkyrienwarfare.addon.control.block.engine.BlockNormalEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric C on 6/25/2019.
 */
public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block BLOCK_PLASMAENGINE = new BlockPlasmaEngine("plasma_engine");

}
