package winter.winter_rpg_skill_tree.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.puffish.skillsmod.api.SkillsAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import winter.winter_rpg_skill_tree.experience.GenericExperienceSource;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    private Player self = (Player) (Object) this;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "giveExperiencePoints", at = @At("TAIL"))
    public void mixinGiveExperiencePoints(int xpPoints, CallbackInfo ci) {
        if (self instanceof ServerPlayer serverPlayer) {
            SkillsAPI.updateExperienceSources(serverPlayer, GenericExperienceSource.class, source -> source.getValue(serverPlayer, xpPoints));
        }
    }
}
