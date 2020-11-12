package udary.tfcudarymod.handlers.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import udary.tfcudarymod.gui.devices.GuiAlloyCalculator;
import udary.tfcudarymod.gui.devices.GuiAnodisingVessel;
import udary.tfcudarymod.gui.devices.GuiEvaporatorPan;
import udary.tfcudarymod.gui.devices.GuiOreCooker;
import udary.tfcudarymod.gui.materials.GuiDryingMat;
import udary.tfcudarymod.gui.terrafirmacraft.GuiTFCLogPile;
import udary.tfcudarymod.tileentities.devices.TileEntityAlloyCalculator;
import udary.tfcudarymod.tileentities.devices.TileEntityAnodisingVessel;
import udary.tfcudarymod.tileentities.devices.TileEntityEvaporatorPan;
import udary.tfcudarymod.tileentities.devices.TileEntityOreCooker;
import udary.tfcudarymod.tileentities.materials.TileEntityDryingMat;

import com.bioxx.tfc.TileEntities.TELogPile;

public class GuiHandler extends udary.tfcudarymod.handlers.GuiHandler
{
	@Override
	public Object getClientGuiElement(int Id, EntityPlayer player, World world, int x, int y, int z) 
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
				return new GuiAnodisingVessel(player.inventory, (TileEntityAnodisingVessel)tileEntity, world, x, y, z);
			case GuiIdOreCooker:
				return new GuiOreCooker(player.inventory, (TileEntityOreCooker)tileEntity, world, x, y, z);
			case GuiIdAlloyCalculator:
				return new GuiAlloyCalculator(player.inventory, (TileEntityAlloyCalculator)tileEntity, world, x, y, z);
			case GuiIdEvaporatorPan:
				return new GuiEvaporatorPan(player.inventory, (TileEntityEvaporatorPan)tileEntity, world, x, y, z);
			case GuiIdDryingMat:
				return new GuiDryingMat(player.inventory, (TileEntityDryingMat)tileEntity, world, x, y, z);
			
			case GuiIdTFCLogPile:
				return new GuiTFCLogPile(player.inventory, (TELogPile)tileEntity, world, x, y, z);
				
			default:
				return null;
		}
	}
}
