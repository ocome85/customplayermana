package com.birchlovelymod.birchlovelymod;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Test_Sword1<T extends LivingEntity> extends SwordItem {
    private static final Predicate<Monster> PREDICATE_MOB = p_29453_ -> p_29453_ != null;

    private static final int MANA_COST= 50;
    protected TargetingConditions targetConditions;
    protected final Class<? extends LivingEntity> lookAtType;

    public Test_Sword1(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        lookAtType = null;
        this.targetConditions = null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41290_, Player p_41291_, InteractionHand p_41292_) {
        ItemStack itemstack = p_41291_.getItemInHand(p_41292_);
        if (!p_41290_.isClientSide) {
            LazyOptional<CapabilityInterface> stats1 = p_41291_.getCapability(MyCapability.INSTANCE);
            CapabilityInterface myCapabilityInterface = stats1.orElseThrow(IllegalStateException::new);
            ManaData manaData = myCapabilityInterface.getManaData();
            boolean manacheck = manaData.ManaLevelCheck(MANA_COST,false);
            if (manacheck == true) {
                //ここにAbility
                List<Monster> list = p_41290_.getEntitiesOfClass(Monster.class, p_41291_.getBoundingBox().inflate(20.0D), PREDICATE_MOB);
                if (0 !=list.size()) {
                    for(int i = 0; i < list.size(); i++) {
                        Mob mob = list.get(i);
                        mob.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 5), p_41291_);
                        LightningBolt lightningbolt = ModEntities.LIGHTNINGBOLT1.get().create(p_41290_);
                        lightningbolt.moveTo(mob.position());
                        lightningbolt.setVisualOnly(false);
                        p_41290_.addFreshEntity(lightningbolt);
                    }
                }else{
                    Component component = new TranslatableComponent("近くに敵はいません!!").withStyle(ChatFormatting.RED);
                    p_41291_.sendMessage(component, Util.NIL_UUID);
                    manaData.ManaLevelCheck(MANA_COST,true);
                }
            } else {
                Component component = new TranslatableComponent("マナが足りません!!").withStyle(ChatFormatting.RED);
                p_41291_.displayClientMessage(component, true);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, p_41290_.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(new TranslatableComponent(this.getDescriptionId()+".desc"));
    }


}
