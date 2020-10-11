package com.ice2670.plasmaengine.Items;

import com.ice2670.plasmaengine.Main;
import com.ice2670.plasmaengine.init.ItemInit;
import com.ice2670.plasmaengine.util.IHasModel;
import net.minecraft.item.Item;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

public class ItemBase extends Item implements IHasModel
{
    public ItemBase(String name)
    {
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ValkyrienSkiesMod.VS_CREATIVE_TAB);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
