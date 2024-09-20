package cbejl.mods.cbejlneomod.block;

import cbejl.mods.cbejlneomod.block.entity.EvilPCBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EvilPCBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING;
    private static final VoxelShape BASE;
    private static final VoxelShape MONITOR, MONITOR_EAST, MONITOR_SOUTH, MONITOR_WEST;
    private static final VoxelShape SHAPE, SHAPE_EAST, SHAPE_SOUTH, SHAPE_WEST;

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        BASE = Block.box(0, 0, 0, 16, 4, 16);

        MONITOR = Block.box(1, 4, 6, 15, 15, 16);
        MONITOR_WEST = Block.box(6, 4, 1, 16, 15, 15);
        MONITOR_SOUTH = Block.box(1, 4, 0, 15, 15, 10);
        MONITOR_EAST = Block.box(0, 4, 1, 10, 15, 15);

        SHAPE = Shapes.or(BASE, MONITOR);

        SHAPE_EAST = Shapes.or(BASE, MONITOR_EAST);
        SHAPE_SOUTH = Shapes.or(BASE, MONITOR_SOUTH);
        SHAPE_WEST = Shapes.or(BASE, MONITOR_WEST);

    }

    public EvilPCBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EvilPCBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult pHit) {
        if (level.getBlockEntity(pos) instanceof EvilPCBlockEntity tile) {
            return tile.interact(player, handIn);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof EvilPCBlockEntity) {
                ((EvilPCBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
