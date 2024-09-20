package cbejl.mods.cbejlneomod.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class EvilPCBlockRenderer implements BlockEntityRenderer<EvilPCBlockEntity> {
    public EvilPCBlockRenderer(BlockEntityRendererProvider.Context context) {}

    public static final float PIXEL_SIZE = 1f/16f;

    @Override
    public void render(EvilPCBlockEntity evilPCBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = evilPCBlockEntity.getItemStack();

        poseStack.pushPose();

        switch (evilPCBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case SOUTH -> {
                poseStack.translate(0.5f, 1 - PIXEL_SIZE * 7f, 0.625f);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            }
            case EAST -> {
                poseStack.translate(0.625f, 1 - PIXEL_SIZE * 7f, 0.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(270));
            }
            case WEST -> {
                poseStack.translate(0.375f, 1 - PIXEL_SIZE * 7f, 0.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
            }
            default -> {
                poseStack.translate(0.5f, 1 - PIXEL_SIZE * 7f, 0.375f);
            }
        }

        poseStack.scale(PIXEL_SIZE * 5, PIXEL_SIZE * 5, 0.001f);

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(evilPCBlockEntity.getLevel(), evilPCBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, evilPCBlockEntity.getLevel(), 1);
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
