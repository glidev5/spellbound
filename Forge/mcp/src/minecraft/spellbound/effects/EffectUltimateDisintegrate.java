package spellbound.effects;

import net.minecraft.entity.player.EntityPlayer;
import spellbound.enums.EnumSpellType;

public class EffectUltimateDisintegrate extends AbstractEffect
{
	@Override
	public String getSpellDisplayName()
	{
		return "Disintegrate";
	}

	@Override
	public void doSpellEffect(EntityPlayer caster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpellEffect() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public EnumSpellType getSpellType() 
	{
		return EnumSpellType.TARGET;
	}
}