package udary.tfcudarymod.handlers.network;

import udary.tfcudarymod.core.ModRecipes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.Handlers.Network.AbstractPacket;

public class InitClientWorldPacket extends AbstractPacket
{
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) 
	{
	}

	@Override
	public void handleClientSide(EntityPlayer player) 
	{
		ModRecipes.initialiseAnvil(); 
	}

	@Override
	public void handleServerSide(EntityPlayer player) 
	{
	}
}
