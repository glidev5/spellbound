package spellbound.effects;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import spellbound.core.EffectEntry;
import spellbound.core.SB;
import spellbound.enums.EnumSpellType;

public class EffectFireShield extends AbstractEffect
{
	@Override
	public String getSpellDisplayName() 
	{
		return "Fire Shield";
	}

	@Override
	public void doSpellEffect(EntityPlayer caster) 
	{
		this.caster = caster;
		
		List<EffectEntry> activeEffectsForCaster = SB.activeSpellEffects.get(caster);

		if (activeEffectsForCaster == null)
		{
			List<EffectEntry> entryList = new ArrayList<EffectEntry>();
			entryList.add(new EffectEntry(this, 1200));
			SB.activeSpellEffects.put(caster, entryList);
		}
		
		else
		{
			activeEffectsForCaster.add(new EffectEntry(this, 40));
			SB.activeSpellEffects.put(caster, activeEffectsForCaster);
		}
	}

	@Override
	public void updateSpellEffect() 
	{
		
	}
	
	@Override
	public EnumSpellType getSpellType() 
	{
		return EnumSpellType.SELF;
	}

	@Override
	public void doSpellTargetEffect(World worldObj, int posX, int posY, int posZ, EntityLivingBase entityHit) {
	}
}
