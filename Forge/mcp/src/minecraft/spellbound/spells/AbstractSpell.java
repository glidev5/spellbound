package spellbound.spells;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import spellbound.core.Constants;
import spellbound.core.SpellboundCore;
import spellbound.enums.EnumItemInUseTime;
import spellbound.enums.EnumSpellType;

public abstract class AbstractSpell 
{
	public EntityPlayer caster;

	public String getSpellChargeSound()
	{
		switch (getSpellCastDuration())
		{
		case INSTANT: return "null";
		case ONE_SECOND: return "spellbound:spellcharge1second";
		case TWO_SECONDS: return "spellbound:spellcharge2seconds";
		case THREE_SECONDS: return "spellbound:spellcharge3seconds";
		case FOUR_SECONDS: return "spellbound:spellcharge4seconds";
		case FIVE_SECONDS: return "spellbound:spellcharge5seconds";
		default: return "spellbound:spellcharge5seconds";
		}
	}
	
	public AbstractSurge doMagicSurge(EntityPlayer caster)
	{
		this.caster = caster;
		int chanceOfSurge = 1;
		
		if (SpellboundCore.instance.playerHasActiveSpell(caster, "SpellChaos"))
		{
			chanceOfSurge = 80;
		}
		
		if (SpellboundCore.instance.playerHasActiveSpell(caster, "SpellSurgeShield") || SpellboundCore.instance.playerHasActiveSpell(caster, "SpellShieldOfInvulnerability"))
		{
			chanceOfSurge = 0;
		}
		
		if (SpellboundCore.getBooleanWithProbability(chanceOfSurge))
		{
			if (chanceOfSurge == 1 && SpellboundCore.rand.nextBoolean())
			{
				//Save
				return null;
			}
			
			return Constants.SURGES[SpellboundCore.rand.nextInt(Constants.SURGES.length)];
		}
		
		else
		{
			return null;
		}
	}

	public abstract String getSpellDisplayName();
	
	public abstract EnumItemInUseTime getSpellCastDuration();

	public abstract void doSpellCasterEffect(EntityPlayer caster);

	public abstract void doSpellTargetEffect(World worldObj, int posX, int posY, int posZ, EntityLivingBase entityHit);
	
	public abstract EnumSpellType getSpellType();
}
