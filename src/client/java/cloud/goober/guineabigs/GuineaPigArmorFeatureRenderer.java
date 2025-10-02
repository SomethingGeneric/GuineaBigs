package cloud.goober.guineabigs;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuineaPigArmorFeatureRenderer extends FeatureRenderer<GuineaPigRenderState, GuineaModel> {
    private static final Identifier ARMOR_TEXTURE = Identifier.of(GuineaBigs.MOD_ID, "textures/entity/guinea_pig_armor.png");
    
    public GuineaPigArmorFeatureRenderer(FeatureRendererContext<GuineaPigRenderState, GuineaModel> context) {
        super(context);
    }
    
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GuineaPigRenderState state, float limbAngle, float limbDistance) {
        if (state.hasArmor) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(ARMOR_TEXTURE));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
