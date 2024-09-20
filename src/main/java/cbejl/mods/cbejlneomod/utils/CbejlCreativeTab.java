package cbejl.mods.cbejlneomod.utils;

import cbejl.mods.cbejlneomod.CbejlNeoMod;
import cbejl.mods.cbejlneomod.block.CbejlBlocks;
import cbejl.mods.cbejlneomod.item.CbejlItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CbejlCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CbejlNeoMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CBEJL_TAB = CREATIVE_MOD_TABS.register("cbejl_staff",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(CbejlItems.CHARON_BABY.get()))
                    .title(Component.translatable("itemGroup.cbejl_staff"))
                    .displayItems((IDParameters, output) -> {
                        addToTab(output);
                    })
                    .build());

    private static void addToTab(CreativeModeTab.Output output) {

        output.accept(CbejlItems.CHARON_BABY.get());
        output.accept(CbejlBlocks.CRAFTER_BLOCK.get());
        output.accept(CbejlBlocks.EVIL_PC.get());
    }

    public static void register(IEventBus bus) {
        CREATIVE_MOD_TABS.register(bus);
    }
}
