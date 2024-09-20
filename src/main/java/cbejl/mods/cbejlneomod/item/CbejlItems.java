package cbejl.mods.cbejlneomod.item;

import cbejl.mods.cbejlneomod.CbejlNeoMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CbejlItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CbejlNeoMod.MOD_ID);

    public static final RegistryObject<Item> CHARON_BABY = ITEMS.register(
            "charon_baby",
            () -> new CharonBaby(new Item.Properties().durability(64)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
