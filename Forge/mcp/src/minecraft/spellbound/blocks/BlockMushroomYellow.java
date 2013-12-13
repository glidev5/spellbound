package spellbound.blocks;

import spellbound.effects.AbstractEffect;

public class BlockMushroomYellow extends AbstractMushroom
{
	public BlockMushroomYellow(int itemId) 
	{
		super(itemId);
	}

	@Override
	public AbstractEffect getMushroomEffect() 
	{
		return null;
	}

	@Override
	public void setName() 
	{
		this.setUnlocalizedName("YellowHybrid");
	}

	@Override
	public void setTexture() 
	{
		this.setTextureName("spellbound:mushroom_yellow");
	}
}
