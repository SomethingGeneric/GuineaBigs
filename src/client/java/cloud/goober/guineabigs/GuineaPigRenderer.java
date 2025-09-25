package cloud.goober.guineabigs;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuineaPigRenderer extends MobEntityRenderer<GuineaPigEntity, GuineaPigRenderState, GuineaModel> {
    
    public GuineaPigRenderer(EntityRendererFactory.Context context) {
        super(context, new GuineaModel(context.getPart(GuineaBigsClient.GUINEA_MODEL_LAYER)), 0.125f);
    }

    @Override
    public GuineaPigRenderState createRenderState() {
        return new GuineaPigRenderState();
    }

    @Override
    public void updateRenderState(GuineaPigEntity entity, GuineaPigRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.textureVariant = entity.getTextureVariant();
        
        // Use simple default animation values
        state.limbAngle = entity.age * 0.6662F;
        state.limbAmplitude = (float) Math.min(entity.getVelocity().horizontalLength() * 4.0F, 1.0F);
        
        // Calculate head rotation relative to body - this is the key fix
        // The head should rotate relative to the body, not absolute rotation
        float bodyYaw = entity.bodyYaw;
        float headYaw = entity.getHeadYaw();
        state.netHeadYaw = headYaw - bodyYaw; // Head rotation relative to body
        state.headPitch = entity.getPitch(); // Up/down head movement
    }

    @Override
    public Identifier getTexture(GuineaPigRenderState state) {
        return Identifier.of(GuineaBigs.MOD_ID, "textures/entity/guinea_pig_" + state.textureVariant + ".png");
    }

    @Override
    protected void scale(GuineaPigRenderState state, MatrixStack matrixStack) {
        matrixStack.scale(0.5f, 0.5f, 0.5f); // Scale to fit
    }
}