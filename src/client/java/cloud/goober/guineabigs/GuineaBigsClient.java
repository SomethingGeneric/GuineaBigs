package cloud.goober.guineabigs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GuineaBigsClient implements ClientModInitializer {
	@Override
    public void onInitializeClient() {

        // Transparent poops
        BlockRenderLayerMap.INSTANCE.putBlock(GuineaBlocks.GUINEA_PELLET, RenderLayer.getCutout());

        // Creature
        // TODO: He shouldn't be a reskinned pig model lol
        EntityRendererRegistry.register(GuineaBigs.GUINEA_PIG, (EntityRendererFactory.Context context) ->
                new MobEntityRenderer<GuineaPigEntity, PigEntityModel<GuineaPigEntity>>(
                        context,
                        new PigEntityModel<>(context.getPart(EntityModelLayers.PIG)),
                        0.25f // Shadow radius (halved from 0.5f)
                ) {
                    @Override
                    public Identifier getTexture(GuineaPigEntity entity) {
                        return Identifier.of(GuineaBigs.MOD_ID, "textures/entity/pig.png");
                    }

                    @Override
                    protected void scale(GuineaPigEntity entity, MatrixStack matrixStack, float f) {
                        // Scale the entity to half its size
                        matrixStack.scale(0.25f, 0.5f, 0.25f);
                    }
                }
        );
    }
}