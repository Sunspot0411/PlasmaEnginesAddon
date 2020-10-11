package com.ice2670.plasmaengine.init;

import com.ice2670.plasmaengine.Items.*;
import com.ice2670.plasmaengine.Items.ItemPlasmaEngine;
import com.ice2670.plasmaengine.Items.ItemTemperedGlass;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric C on 6/25/2019.
 */
public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item ITEM_PLASMAENGINE = new ItemPlasmaEngine("item_plasmaengine");

    public static final Item ITEM_TEMPEREDGLASS = new ItemTemperedGlass("item_temperedglass");

}
