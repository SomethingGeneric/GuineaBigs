package cloud.goober.guineabigs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;

public class GuineaPigEntity extends AnimalEntity {
    private int textureVariant; // New field for texture variant

    public GuineaPigEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.textureVariant = Random.create().nextInt(2); // Randomly select a texture variant (0, 1)
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == GuineaItems.TIMOTHY_HAY;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new SeekTreadmillGoal(this, 0.85D, 10, 800));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.85D)); // Slower wandering speed
        this.goalSelector.add(5, new PlaceBlockGoal(this));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createGuineaPigAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10.0D)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public GuineaPigEntity createChild(ServerWorld world, PassiveEntity entity) {
        GuineaPigEntity child = GuineaBigs.GUINEA_PIG.create(world, SpawnReason.BREEDING);
        child.setTextureVariant(Random.create().nextInt(2)); // Randomly set the child’s texture variant
        return child;
    }

    public int getTextureVariant() {
        return textureVariant;
    }

    public void setTextureVariant(int textureVariant) {
        this.textureVariant = textureVariant;
    }

    // Add a method to get the texture path based on the variant
    public Identifier getTexture() {
        return Identifier.of("guineabigs", "textures/entity/guinea_pig_" + textureVariant + ".png");
    }
}
