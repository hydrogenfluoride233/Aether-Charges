package com.hydrogen.crystalcharges.client;

import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.hydrogen.crystalcharges.entity.CrystalBoltEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/**
 * Reuses the Aether's own crystal model and projectile textures, so a shot in flight
 * looks exactly like the boss's.
 */
public class CrystalBoltRenderer extends EntityRenderer<CrystalBoltEntity> {
    private static final ResourceLocation ICE = ResourceLocation.fromNamespaceAndPath("aether", "textures/entity/projectile/crystals/ice_ball.png");
    private static final ResourceLocation FIRE = ResourceLocation.fromNamespaceAndPath("aether", "textures/entity/projectile/crystals/fire_ball.png");
    private static final ResourceLocation ELECTRIC = ResourceLocation.fromNamespaceAndPath("aether", "textures/entity/projectile/crystals/electric_ball.png");

    private final CrystalModel<CrystalBoltEntity> model;

    public CrystalBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CrystalModel<>(CrystalModel.createBodyLayer().bakeRoot());
        this.shadowRadius = 0.15F;
    }

    @Override
    public void render(CrystalBoltEntity entity, float entityYaw, float partialTick, PoseStack pose,
                       MultiBufferSource buffer, int packedLight) {
        pose.pushPose();
        pose.translate(0.0D, 0.4D, 0.0D);
        var consumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        float time = entity.tickCount + partialTick;
        pose.mulPose(Axis.XP.rotationDegrees(time * 36.0F));
        this.model.crystal1.render(pose, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        pose.mulPose(Axis.YP.rotationDegrees(time * 36.0F));
        this.model.crystal2.render(pose, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        pose.mulPose(Axis.ZP.rotationDegrees(time * 36.0F));
        this.model.crystal3.render(pose, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        pose.popPose();
        super.render(entity, entityYaw, partialTick, pose, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CrystalBoltEntity entity) {
        return switch (entity.element()) {
            case FIRE -> FIRE;
            case THUNDER -> ELECTRIC;
            default -> ICE;
        };
    }
}
