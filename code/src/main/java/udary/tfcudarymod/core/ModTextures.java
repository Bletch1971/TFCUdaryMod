package udary.tfcudarymod.core;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ModTextures 
{
	public static IIcon GuiAlloyCalculator;
	public static IIcon GuiAlloyList;
	
	public static void registerBlockIcons(IIconRegister iconRegister)
	{
		if (iconRegister == null)
			return;
		
		GuiAlloyCalculator = iconRegister.registerIcon(ModDetails.ModID + ":" + "button_alloycalc");
		GuiAlloyList = iconRegister.registerIcon(ModDetails.ModID + ":" + "button_alloylist");

		if (ModBlocks.TfcLogPile != null)
			ModBlocks.TfcLogPile.registerBlockIcons(iconRegister);
	}

	public static void registerItemIcons(IIconRegister iconRegister)
	{
		if (iconRegister == null)
			return;
		
		if (ModItems.TfcLog != null)
			ModItems.TfcLog.registerIcons(iconRegister);
	}
}
