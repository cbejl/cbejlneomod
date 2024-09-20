package cbejl.mods.cbejlneomod;

import cbejl.mods.cbejlneomod.block.CbejlBlocks;
import cbejl.mods.cbejlneomod.block.entity.CbejlBlockEntities;
import cbejl.mods.cbejlneomod.block.entity.EvilPCBlockRenderer;
import cbejl.mods.cbejlneomod.item.CbejlItems;
import cbejl.mods.cbejlneomod.recipe.CbejlRecipes;
import cbejl.mods.cbejlneomod.utils.CbejlCreativeTab;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CbejlNeoMod.MOD_ID)
public class CbejlNeoMod {
    public static final String MOD_ID = "cbejlneomod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CbejlNeoMod(IEventBus bus) {
        CbejlItems.register(bus);
        CbejlBlocks.register(bus);
        CbejlCreativeTab.register(bus);
        CbejlBlockEntities.register(bus);
        CbejlRecipes.register(bus);
        //bus.addListener(this::addCreative);
    }

    @Mod.EventBusSubscriber(modid = CbejlNeoMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    class CbejlEventBusEvent {
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(CbejlBlockEntities.EVIL_PC_BE.get(), EvilPCBlockRenderer::new);
        }
    }
}
