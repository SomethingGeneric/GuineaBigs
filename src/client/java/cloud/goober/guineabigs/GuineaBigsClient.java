package cloud.goober.guineabigs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
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
        // Register entity renderer with custom model
        EntityRendererRegistry.register(GuineaBigs.GUINEA_PIG, GuineaPigRenderer::new);

        // Register the model layer
        EntityModelLayerRegistry.registerModelLayer(GuineaBigsClient.GUINEA_MODEL_LAYER, GuineaModel::getTexturedModelData);
    }
}
