package cbejl.mods.cbejlneomod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class EvilPCBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public EvilPCBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CbejlBlockEntities.EVIL_PC_BE.get(), pPos, pBlockState);
    }

    public void setItem(ItemStack stack) {
        itemHandler.setStackInSlot(0, stack);
    }

    public void removeItem() {
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void spawnParticle(boolean allGood) {
        if (getLevel().isClientSide) return;

        if (allGood) {
            ((ServerLevel) getLevel()).sendParticles(ParticleTypes.HAPPY_VILLAGER,
                    this.getBlockPos().getCenter().x() + (Math.random() - 0.5f) / 5,
                    this.getBlockPos().getCenter().y() + 0.4f + Math.random() / 5,
                    this.getBlockPos().getCenter().z() + (Math.random() - 0.5f) / 5,
                    2,
                    0.4, 0.1, 0.4,
                    0.1);
        } else {
            ((ServerLevel) getLevel()).sendParticles(ParticleTypes.CRIT,
                    this.getBlockPos().getCenter().x() + (Math.random() - 0.5f) / 5,
                    this.getBlockPos().getCenter().y() + 0.4f + Math.random() / 5,
                    this.getBlockPos().getCenter().z() + (Math.random() - 0.5f) / 5,
                    4,
                    0.4, 0.1, 0.4,
                    0.1);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, new SimpleContainer(itemHandler.getStackInSlot(0)));
    }

    public InteractionResult interact(Player player, InteractionHand handIn) {
        if (handIn == InteractionHand.MAIN_HAND) {
            ItemStack handItem = player.getItemInHand(handIn);
            ItemStack itemStack = itemHandler.getStackInSlot(0);

            //Забираем пустой рукой
            if (!itemStack.isEmpty() && handItem.isEmpty()) {
                ItemStack it = itemStack.copy();
                removeItem();
                if (!this.level.isClientSide()) {
                    player.setItemInHand(handIn, it);
                    this.setChanged();
                }

                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
            //Кладём предмет
            else if (itemStack.isEmpty() && !handItem.isEmpty()) {
                ItemStack it = handItem.copy();
                it.setCount(1);
                this.setItem(it);
                handItem.shrink(1);

                if (!this.level.isClientSide()) {
                    this.level.playSound(null, this.worldPosition, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.10F + 0.95F);
                }

                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    public ItemStack getItemStack() {
        return itemHandler.getStackInSlot(0);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
