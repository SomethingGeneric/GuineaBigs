package cloud.goober.guineabigs;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GuineaModel extends EntityModel<GuineaPigEntity> {
	private final ModelPart neck;
	private final ModelPart frleg;
	private final ModelPart flleg;
	private final ModelPart blleg;
	private final ModelPart brleg;
	private final ModelPart bb_main;
	public GuineaModel(ModelPart root) {
		this.neck = root.getChild("neck");
		this.frleg = root.getChild("frleg");
		this.flleg = root.getChild("flleg");
		this.blleg = root.getChild("blleg");
		this.brleg = root.getChild("brleg");
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData neck = modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 17).cuboid(-5.0F, -11.0F, -3.0F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, 0.0F));

		ModelPartData frleg = modelPartData.addChild("frleg", ModelPartBuilder.create().uv(18, 21).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 19.0F, 2.0F));

		ModelPartData flleg = modelPartData.addChild("flleg", ModelPartBuilder.create().uv(18, 17).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 18.0F, 2.0F));

		ModelPartData blleg = modelPartData.addChild("blleg", ModelPartBuilder.create().uv(22, 21).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 19.0F, 10.0F));

		ModelPartData brleg = modelPartData.addChild("brleg", ModelPartBuilder.create().uv(22, 17).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 19.0F, 10.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -10.0F, 0.0F, 6.0F, 5.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(GuineaPigEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// Reset the body rotation for now
		this.bb_main.yaw = 0.0F;

		// Clamp netHeadYaw to -90 to 90 degrees (convert to radians)
		float clampedYaw = Math.max(-90, Math.min(90, netHeadYaw));
		this.neck.yaw = clampedYaw * ((float) Math.PI / 180F);  // Convert clamped yaw to radians

		// Set the head pitch (clamping isn't necessary for pitch, but can be added if desired)
		this.neck.pitch = headPitch * ((float) Math.PI / 180F);  // Convert pitch to radians

		// Basic walking animation for the legs
		this.frleg.pitch = (float) (Math.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount);
		this.flleg.pitch = (float) (Math.cos(limbSwing * 1.0F + (float) Math.PI) * 1.0F * limbSwingAmount);
		this.blleg.pitch = (float) (Math.cos(limbSwing * 1.0F + (float) Math.PI) * 1.0F * limbSwingAmount);
		this.brleg.pitch = (float) (Math.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount);
	}




	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int foo) { // Hope 'foo' wasn't something important.
		neck.render(matrices, vertexConsumer, light, overlay);
		frleg.render(matrices, vertexConsumer, light, overlay);
		flleg.render(matrices, vertexConsumer, light, overlay);
		blleg.render(matrices, vertexConsumer, light, overlay);
		brleg.render(matrices, vertexConsumer, light, overlay);
		bb_main.render(matrices, vertexConsumer, light, overlay);
	}
}