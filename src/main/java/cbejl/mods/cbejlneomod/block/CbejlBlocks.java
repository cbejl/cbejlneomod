package cbejl.mods.cbejlneomod.block;

import cbejl.mods.cbejlneomod.CbejlNeoMod;
import cbejl.mods.cbejlneomod.item.CbejlItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CbejlBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CbejlNeoMod.MOD_ID);

    public static final RegistryObject<Block> EVIL_PC = registerBlock(
            "evil_computer",
            () -> new EvilPCBlock(BlockBehaviour.Properties
                    .copy(Blocks.RAW_IRON_BLOCK)
                    .sound(SoundType.COPPER)
                    .noOcclusion()));

    public static final RegistryObject<Block> CRAFTER_BLOCK = registerBlock(
            "evil_computer_crafter_block",
            () -> new EvilPCCrafterBlock(BlockBehaviour.Properties.
                    copy(Blocks.RAW_IRON_BLOCK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> result = BLOCKS.register(name, block);
        CbejlItems.ITEMS.register(name, () -> new BlockItem(result.get(), new Item.Properties()));
        return result;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
