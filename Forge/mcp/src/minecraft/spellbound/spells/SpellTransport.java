/**********************************************
 * SpellTransport.java
 * Copyright (c) 2013 Wild Bama Boy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 **********************************************/

package spellbound.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import spellbound.entity.EntityTargetSpellDivination;
import spellbound.enums.EnumItemInUseTime;
import spellbound.enums.EnumSpellRange;

public class SpellTransport extends AbstractSpell
{
	@Override
	public String getSpellDisplayName() 
	{
		return "Transport";
	}

	@Override
	public void doSpellCasterEffect(EntityPlayer caster) 
	{
		caster.worldObj.playSoundAtEntity(caster, "mob.ghast.fireball", 1.0F, 1.0F);
		caster.worldObj.spawnEntityInWorld(new EntityTargetSpellDivination(caster, this));
	}

	@Override
	public EnumSpellRange getSpellType() 
	{
		return EnumSpellRange.TARGET;
	}

	@Override
	public void doSpellTargetEffect(World worldObj, int posX, int posY, int posZ, EntityLivingBase entityHit) 
	{
		if (!worldObj.isRemote)
		{
			final double spawnX = entityHit == null ? (double)posX : entityHit.posX;
			final double spawnY = entityHit == null ? (double)posY + 1 : entityHit.posY;
			final double spawnZ = entityHit == null ? (double)posZ : entityHit.posZ;

			final EntityPlayerMP casterMP = (EntityPlayerMP)caster;
			casterMP.mountEntity((Entity)null);
			casterMP.playerNetServerHandler.setPlayerLocation(spawnX, spawnY, spawnZ, caster.rotationYaw, caster.rotationPitch);

			caster.worldObj.playSoundAtEntity(casterMP, "mob.endermen.portal", 1.0F, 1.0F);
		}
	}

	@Override
	public EnumItemInUseTime getSpellCastDuration() 
	{
		return EnumItemInUseTime.TWO_SECONDS;
	}
}
