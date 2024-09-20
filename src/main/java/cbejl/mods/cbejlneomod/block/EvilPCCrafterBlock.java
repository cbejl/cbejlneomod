package cbejl.mods.cbejlneomod.block;

import cbejl.mods.cbejlneomod.block.entity.CbejlBlockEntities;
import cbejl.mods.cbejlneomod.block.entity.EvilPCBlockEntity;
import cbejl.mods.cbejlneomod.recipe.EvilPCRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EvilPCCrafterBlock extends Block {
    public static final int RADIUS = 8;
    public static final int SIZE_LIMIT = 4;

    public EvilPCCrafterBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult pHit) {
        if (handIn.equals(InteractionHand.MAIN_HAND)) return craft(level, pos);
        return InteractionResult.PASS;
    }

    private InteractionResult craft(Level level, BlockPos pos) {
        if (level.isClientSide()) return InteractionResult.PASS;

        List<EvilPCBlockEntity> pcs = getEvilPCEntitiesAround(level, pos);
        if (pcs.isEmpty()) return InteractionResult.PASS;

        SimpleContainer inventory = new SimpleContainer(SIZE_LIMIT);
        for (int i = 0; i < pcs.size(); i++) {
            inventory.setItem(i, pcs.get(i).getItemStack());
        }

        Optional<EvilPCRecipe> recipe = level.getRecipeManager().getRecipeFor(EvilPCRecipe.Type.INSTANCE, inventory, level);

        pcs.forEach(x -> x.spawnParticle(recipe.isPresent()));

        if (recipe.isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            Containers.dropContents(level, pos.offset(0, 1, 0), new SimpleContainer(recipe.get().getResultItem(null)));
            pcs.forEach(EvilPCBlockEntity::removeItem);
        }

        ((ServerLevel) level).sendParticles(ParticleTypes.SOUL,
                pos.getCenter().x() + (Math.random() - 0.5) / 4,
                pos.getCenter().y() + 1,
                pos.getCenter().z() + (Math.random() - 0.5) / 4,
                10,
                0.1, 0.1, 0.1,
                0.1);

        return InteractionResult.SUCCESS;
    }


    private List<EvilPCBlockEntity> getEvilPCEntitiesAround(Level level, BlockPos pos) {
        List<EvilPCBlockEntity> result = new ArrayList<>();

        sorry:
        for (int i = -1; i <= 1; i++) {
            for (BlockPos.MutableBlockPos mutableBlockPos : BlockPos.spiralAround(pos.offset(0, i, 0), RADIUS, Direction.NORTH, Direction.EAST)) {
                level.getBlockEntity(mutableBlockPos, CbejlBlockEntities.EVIL_PC_BE.get()).ifPresent(result::add);
                if (result.size() >= SIZE_LIMIT) break sorry;
            }
        }

        return result;
    }
}
