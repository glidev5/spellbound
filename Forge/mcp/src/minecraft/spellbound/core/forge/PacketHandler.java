/**********************************************
 * PacketHandler.java
 * Copyright (c) 2013 Wild Bama Boy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 **********************************************/

package spellbound.core.forge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		try
		{
			final EntityPlayer entityPlayer = (EntityPlayer)player;
			
			if (packet.channel.equals("SB_LIGHTNING"))
			{
				handleLightningPacket(packet, entityPlayer);
			}
			
			else if (packet.channel.equals("SB_CHATMESSAGE"))
			{
				handleChatMessagePacket(packet, entityPlayer);
			}
			
			else if (packet.channel.equals("SB_FLIGHT"))
			{
				handleFlightPacket(packet, entityPlayer);
			}
		}

		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public static Packet250CustomPayload createLightningPacket(double posX, double posY, double posZ)
	{
		try
		{
			//Initialize
			final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			final ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
			final Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "SB_LIGHTNING";
			//---------------------------------------------------------------------------------------
			
			//Write data
			objectOutput.writeObject(posX);
			objectOutput.writeObject(posY);
			objectOutput.writeObject(posZ);
			
			//---------------------------------------------------------------------------------------
			
			//Cleanup and return
			packet.data = byteOutput.toByteArray();
			packet.length = packet.data.length;
			return packet;
		}

		catch (IOException exception)
		{
			exception.printStackTrace();
			return null;
		}
	}

	private void handleLightningPacket(Packet250CustomPayload packet, EntityPlayer entityPlayer) throws IOException, ClassNotFoundException
	{
		//Initialize
		final ByteArrayInputStream byteInput = new ByteArrayInputStream(packet.data);
		final ObjectInputStream objectInput = new ObjectInputStream(byteInput);
		//---------------------------------------------------------------------------------------
		
		//Read data
		final double posX = (Double) objectInput.readObject();
		final double posY = (Double) objectInput.readObject();
		final double posZ = (Double) objectInput.readObject();
		
		//---------------------------------------------------------------------------------------
		
		//Process
		final EntityLightningBolt lightning = new EntityLightningBolt(entityPlayer.worldObj, posX, posY, posZ);
		entityPlayer.worldObj.spawnEntityInWorld(lightning);
	}
	
	public static Packet250CustomPayload createChatMessagePacket(String message)
	{
		try
		{
			//Initialize
			final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			final ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
			final Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "SB_CHATMESSAGE";
			//---------------------------------------------------------------------------------------
			
			//Read data
			objectOutput.writeObject(message);
			
			//---------------------------------------------------------------------------------------

			//Cleanup and return
			packet.data = byteOutput.toByteArray();
			packet.length = packet.data.length;
			return packet;
		}

		catch (IOException exception)
		{
			exception.printStackTrace();
			return null;
		}
	}

	private void handleChatMessagePacket(Packet250CustomPayload packet, EntityPlayer entityPlayer) throws IOException, ClassNotFoundException
	{
		//Initialize
		final ByteArrayInputStream byteInput = new ByteArrayInputStream(packet.data);
		final ObjectInputStream objectInput = new ObjectInputStream(byteInput);
		//---------------------------------------------------------------------------------------
		
		//Read data
		final String message = (String) objectInput.readObject();

		//---------------------------------------------------------------------------------------
		
		//Process
		entityPlayer.addChatMessage(message);
	}
	
	public static Packet250CustomPayload createFlightPacket(Boolean doEnable)
	{
		try
		{
			//Initialize
			final ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			final ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
			final Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "SB_FLIGHT";
			//---------------------------------------------------------------------------------------
			
			//Write data
			objectOutput.writeObject(doEnable);
			
			//---------------------------------------------------------------------------------------
			
			//Cleanup and return
			packet.data = byteOutput.toByteArray();
			packet.length = packet.data.length;
			return packet;
		}

		catch (IOException exception)
		{
			exception.printStackTrace();
			return null;
		}
	}

	private void handleFlightPacket(Packet250CustomPayload packet, EntityPlayer entityPlayer) throws IOException, ClassNotFoundException
	{
		//Initialize
		final ByteArrayInputStream input = new ByteArrayInputStream(packet.data);
		final ObjectInputStream objectInput = new ObjectInputStream(input);
		//---------------------------------------------------------------------------------------
		
		//Read data
		final boolean doEnable = (Boolean) objectInput.readObject();
		
		//---------------------------------------------------------------------------------------
		
		//Process
		if (doEnable)
		{
			entityPlayer.capabilities.allowFlying = true;
			entityPlayer.fallDistance = 0;
			entityPlayer.motionY += 1.0D;
			entityPlayer.capabilities.isFlying = true;
		}
		
		else
		{
			entityPlayer.capabilities.allowFlying = false;
			entityPlayer.capabilities.isFlying = false;
		}
	}
}