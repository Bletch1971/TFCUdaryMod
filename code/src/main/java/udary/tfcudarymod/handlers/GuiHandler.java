package udary.tfcudarymod.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import udary.tfcudarymod.containers.devices.ContainerAlloyCalculator;
import udary.tfcudarymod.containers.devices.ContainerAnodisingVessel;
import udary.tfcudarymod.containers.devices.ContainerEvaporatorPan;
import udary.tfcudarymod.containers.devices.ContainerOreCooker;
import udary.tfcudarymod.containers.materials.ContainerDryingMat;
import udary.tfcudarymod.containers.terrafirmacraft.ContainerTFCLogPile;
import udary.tfcudarymod.core.ModDetails;
import udary.tfcudarymod.tileentities.devices.TileEntityAlloyCalculator;
import udary.tfcudarymod.tileentities.devices.TileEntityAnodisingVessel;
import udary.tfcudarymod.tileentities.devices.TileEntityEvaporatorPan;
import udary.tfcudarymod.tileentities.devices.TileEntityOreCooker;
import udary.tfcudarymod.tileentities.materials.TileEntityDryingMat;

import com.bioxx.tfc.TileEntities.TELogPile;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public static final int GuiIdAnodisingVessel = ModDetails.GuiOffset + 1;
	public static final int GuiIdOreCooker = ModDetails.GuiOffset + 2;
	public static final int GuiIdAlloyCalculator = ModDetails.GuiOffset + 3;
	public static final int GuiIdEvaporatorPan = ModDetails.GuiOffset + 4;
	public static final int GuiIdDryingMat = ModDetails.GuiOffset + 5;
	
	public static final int GuiIdTFCLogPile = ModDetails.GuiTFCOffset + 1;
	
	@Override
	public Object getServerGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity;
		
		try
		{
			tileEntity = world.getTileEntity(x, y, z);
		}
		catch(Exception e)
		{
			return null;
		}
		
		switch (Id)
		{
			case GuiIdAnodisingVessel:
				return new ContainerAnodisingVessel(player.inventory, (TileEntityAnodisingVessel)tileEntity, world, x, y, z);
			case GuiIdOreCooker:
				return new ContainerOreCooker(player.inventory, (TileEntityOreCooker)tileEntity, world, x, y, z);
			case GuiIdAlloyCalculator:
				return new ContainerAlloyCalculator(player.inventory, (TileEntityAlloyCalculator)tileEntity, world, x, y, z);
			case GuiIdEvaporatorPan:
				return new ContainerEvaporatorPan(player.inventory, (TileEntityEvaporatorPan)tileEntity, world, x, y, z);
			case GuiIdDryingMat:
				return new ContainerDryingMat(player.inventory, (TileEntityDryingMat)tileEntity, world, x, y, z);
				
			case GuiIdTFCLogPile:
				return new ContainerTFCLogPile(player.inventory, (TELogPile)tileEntity, world, x, y, z);
				
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
