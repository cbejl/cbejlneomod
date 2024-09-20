package cbejl.mods.cbejlneomod.recipe;

import cbejl.mods.cbejlneomod.CbejlNeoMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CbejlRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CbejlNeoMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<EvilPCRecipe>> EVIL_PC_SERIALIZER =
            SERIALIZERS.register("evil_pctration", () -> EvilPCRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}