package cbejl.mods.cbejlneomod.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class CharonBaby extends Item {
    private final SoundEvent CHARON_USE = SoundEvent.createVariableRangeEvent(
            new ResourceLocation("cbejlneomod", "charon_use"));
    private final int USE_DURATION = 100;
    private final Random random = new Random();

    public CharonBaby(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return USE_DURATION;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return CHARON_USE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        vape(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        vape(pStack, pLevel, pLivingEntity, 0);
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private void vape(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        pLevel.playSound(pLivingEntity, pLivingEntity.blockPosition().atY((int) pLivingEntity.getEyeY()),
                SoundEvents.PLAYER_BREATH, SoundSource.VOICE, 0.5f, 1f);

        Vec3 vec = pLivingEntity.getLookAngle();
        double dx = pLivingEntity.getX() + (vec.x / 4);
        double dy = pLivingEntity.getY() + pLivingEntity.getEyeHeight();
        double dz = pLivingEntity.getZ() + (vec.z / 4);

        for (int i = (USE_DURATION - pTimeCharged); i > 0; i--) {
            pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, dx, dy, dz, vec.x / 20 + random.nextDouble(-0.02, 0.02), vec.y / 20 + 0.001f + random.nextDouble(-0.01, 0.01), vec.z / 20 + random.nextDouble(-0.02, 0.02));
        }

        if (pLivingEntity instanceof Player player && !pLevel.isClientSide()) {
            EquipmentSlot equipmentslot = pStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            pStack.hurtAndBreak(1, player, (p) -> {
                p.broadcastBreakEvent(equipmentslot);
            });
        }
    }
}
