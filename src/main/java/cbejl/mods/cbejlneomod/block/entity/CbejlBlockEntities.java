package cbejl.mods.cbejlneomod.block.entity;

import cbejl.mods.cbejlneomod.CbejlNeoMod;
import cbejl.mods.cbejlneomod.block.CbejlBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CbejlBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CbejlNeoMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<EvilPCBlockEntity>> EVIL_PC_BE =
            BLOCK_ENTITIES.register(
                    "evil_computer_be",
                    () -> BlockEntityType.Builder.of(EvilPCBlockEntity::new,
                            CbejlBlocks.EVIL_PC.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
