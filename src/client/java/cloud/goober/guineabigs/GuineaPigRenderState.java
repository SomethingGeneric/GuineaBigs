package cloud.goober.guineabigs;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class GuineaPigRenderState extends LivingEntityRenderState {
    public int textureVariant;
    public float limbAngle;
    public float limbAmplitude;
    // Add head rotation fields that work with the body rotation
    public float netHeadYaw;  // Head yaw relative to body
    public float headPitch;   // Head pitch (up/down looking)
}