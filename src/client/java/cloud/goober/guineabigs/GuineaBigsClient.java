package cloud.goober.guineabigs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuineaBigsClient implements ClientModInitializer {
    public static final EntityModelLayer GUINEA_MODEL_LAYER = new EntityModelLayer(Identifier.of(GuineaBigs.MOD_ID, "guinea_pig"), "main");

    @Override
    public void onInitializeClient() {
        // Transparent poops
        BlockRenderLayerMap.INSTANCE.putBlock(GuineaBlocks.GUINEA_PELLET, RenderLayer.getCutout());

        // Register entity renderer with custom model
        EntityRendererRegistry.register(GuineaBigs.GUINEA_PIG, (EntityRendererFactory.Context context) ->
                new MobEntityRenderer<GuineaPigEntity, GuineaModel>(
                        context,
                        new GuineaModel(context.getPart(GUINEA_MODEL_LAYER)),  // Use your custom model
                        0.25f // Shadow radius (halved from 0.5f)
                ) {
                    @Override
                    public Identifier getTexture(GuineaPigEntity entity) {
                        return Identifier.of(GuineaBigs.MOD_ID, "textures/entity/guinea_pig.png"); // Update texture
                    }

                    @Override
                    protected void scale(GuineaPigEntity entity, MatrixStack matrixStack, float f) {
                        matrixStack.scale(0.5f, 0.5f, 0.5f); // Scale to fit your custom model
                    }
                }
        );

        EntityModelLayerRegistry.registerModelLayer(GuineaBigsClient.GUINEA_MODEL_LAYER, GuineaModel::getTexturedModelData);

    }
}
