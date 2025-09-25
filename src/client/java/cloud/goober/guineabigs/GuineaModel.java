package cloud.goober.guineabigs;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GuineaModel extends EntityModel<GuineaPigRenderState> {
	private final ModelPart neck;
	private final ModelPart frleg;
	private final ModelPart flleg;
	private final ModelPart blleg;
	private final ModelPart brleg;
	private final ModelPart bb_main;
	public GuineaModel(ModelPart root) {
		super(root);
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
		ModelPartData neck = modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 17).cuboid(-5.0F, -11.0F, -3.0F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData frleg = modelPartData.addChild("frleg", ModelPartBuilder.create().uv(18, 21).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 19.0F, 2.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData flleg = modelPartData.addChild("flleg", ModelPartBuilder.create().uv(18, 17).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 18.0F, 2.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData blleg = modelPartData.addChild("blleg", ModelPartBuilder.create().uv(22, 21).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 19.0F, 10.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData brleg = modelPartData.addChild("brleg", ModelPartBuilder.create().uv(22, 17).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 19.0F, 10.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -10.0F, 0.0F, 6.0F, 5.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(GuineaPigRenderState state) {
		// Reset the body rotation for now
		this.bb_main.yaw = 0.0F;

		// Basic walking animation for the legs using state properties
		this.frleg.pitch = (float) (Math.cos(state.limbAngle * 1.0F) * 1.0F * state.limbAmplitude);
		this.flleg.pitch = (float) (Math.cos(state.limbAngle * 1.0F + (float) Math.PI) * 1.0F * state.limbAmplitude);
		this.blleg.pitch = (float) (Math.cos(state.limbAngle * 1.0F + (float) Math.PI) * 1.0F * state.limbAmplitude);
		this.brleg.pitch = (float) (Math.cos(state.limbAngle * 1.0F) * 1.0F * state.limbAmplitude);
		
		// Head movement - constrain to realistic ranges for a guinea pig
		// Limit horizontal head rotation to +/- 75 degrees
		float constrainedYaw = MathHelper.clamp(state.netHeadYaw, -75.0F, 75.0F);
		// Limit vertical head rotation to -30 (down) to +45 (up) degrees  
		float constrainedPitch = MathHelper.clamp(state.headPitch, -30.0F, 45.0F);
		
		this.neck.yaw = constrainedYaw * 0.017453292F; // Convert degrees to radians
		this.neck.pitch = constrainedPitch * 0.017453292F; // Convert degrees to radians
	}




}