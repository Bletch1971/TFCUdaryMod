package udary.tfcudarymod.core;

import net.minecraft.block.Block;
import udary.tfcudarymod.blocks.BlockEmpty;
import udary.tfcudarymod.blocks.devices.BlockAlloyCalculator;
import udary.tfcudarymod.blocks.devices.BlockAnodisingVessel;
import udary.tfcudarymod.blocks.devices.BlockEvaporatorPan;
import udary.tfcudarymod.blocks.devices.BlockOreCooker;
import udary.tfcudarymod.blocks.materials.BlockDryingMat;
import udary.tfcudarymod.blocks.terrafirmacraft.BlockTFCLogPile;
import udary.tfcudarymod.items.ItemBlockEmpty;
import udary.tfcudarymod.items.devices.ItemAlloyCalculator;
import udary.tfcudarymod.items.devices.ItemAnodisingVessel;
import udary.tfcudarymod.items.devices.ItemEvaporatorPan;
import udary.tfcudarymod.items.devices.ItemOreCooker;
import udary.tfcudarymod.items.materials.ItemDryingMat;

import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks 
{
	// Device Render Id's
	public static int AlloyCalculatorRenderId;
	public static int AnodisingVesselRenderId;
	public static int DryingMatRenderId;
	public static int EvaporatorPanRenderId;
	public static int OreCookerRenderId;
	public static int TfcLogPileRenderId;
	
	public static Block EmptyBlock;
	
	// Devices
	public static Block AlloyCalculator;
	public static Block AnodisingVessel;
	public static Block DryingMat;
	public static Block EvaporatorPan;
	public static Block OreCooker;
	
	// TerraFirmaCraft
	public static Block TfcLogPile;
	
	public static void initialise()
	{
		System.out.println("[" + ModDetails.ModName + "] Registering Blocks");
		
		registerBlocks();
		registerTFCBlocks();
		
		System.out.println("[" + ModDetails.ModName + "] Done Registering Blocks");
	}

	private static void registerBlocks()
	{
		Block block;
		
		EmptyBlock = new BlockEmpty().setBlockName("empty");
		block = GameRegistry.registerBlock(EmptyBlock, ItemBlockEmpty.class, EmptyBlock.getUnlocalizedName());
		if (ModOptions.showVerboseStartup && block != null)
			System.out.println("[" + ModDetails.ModName + "] Registered Block: " + block.getLocalizedName() + "@" + Block.getIdFromBlock(block));
			
		
		if (ModOptions.enableAlloyCalculatorMod)
		{
			AlloyCalculatorRenderId = RenderingRegistry.getNextAvailableRenderId();
					
			AlloyCalculator = new BlockAlloyCalculator().setBlockName("Alloy Calculator").setHardness(0F).setResistance(1F);
			block = GameRegistry.registerBlock(AlloyCalculator, ItemAlloyCalculator.class, AlloyCalculator.getUnlocalizedName());	
			if (ModOptions.showVerboseStartup && block != null)
				System.out.println("[" + ModDetails.ModName + "] Registered Block: " + block.getLocalizedName() + "@" + Block.getIdFromBlock(block));
		}
		
        if (ModOptions.enableGalenaMod || ModOptions.enableLimoniteMod)
		{
        	AnodisingVesselRenderId = RenderingRegistry.getNextAvailableRenderId();
        			
			AnodisingVessel = new BlockAnodisingVessel().setBlockName("Anodising Vessel").setHardness(1F).setResistance(2F);
			block = GameRegistry.registerBlock(AnodisingVessel, ItemAnodisingVessel.class, AnodisingVessel.getUnlocalizedName());
			if (ModOptions.showVerboseStartup && block != null)
				System.out.println("[" + ModDetails.ModName + "] Registered Block: " + block.getLocalizedName() + "@" + Block.getIdFromBlock(block));
		}
		
        if (ModOptions.enableDryingMatMod)
		{
        	DryingMatRenderId = RenderingRegistry.getNextAvailableRenderId();
        			
        	DryingMat = new BlockDryingMat().setBlockName("Drying Mat").setHardness(0F).setResistance(1F);
        	block = GameRegistry.registerBlock(DryingMat, ItemDryingMat.class, DryingMat.getUnlocalizedName());
    		if (ModOptions.showVerboseStartup && block != null)
    			System.out.println("[" + ModDetails.ModName + "] Registered Block: " + block.getLocalizedName() + "@" + Block.getIdFromBlock(block));
		}
		
		if (ModOptions.enableEvaporationMod)
		{
			EvaporatorPanRenderId = RenderingRegistry.getNextAvailableRenderId();
					
			EvaporatorPan = new BlockEvaporatorPan().setBlockName("Evaporator Pan").setHardness(1F).setResistance(1F);
			block = GameRegistry.registerBlock(EvaporatorPan, ItemEvaporatorPan.class, EvaporatorPan.getUnlocalizedName());		
			if (ModOptions.showVerboseStartup && block != null)
				System.out.println("[" + ModDetails.ModName + "] Registered Block: " + block.getLocalizedName() + "@" + Block.getIdFromBlock(block));
		}
		
		if (ModOptions.enableLimoniteMod)
		{
			OreCookerRenderId = RenderingRegistry.getNextAvailableRenderId();
					
			OreCooker = new BlockOreCooker().setBlockName("Ore Cooker").setHardness(4F).setResistance(2F);
			block = GameRegistry.registerBlock(OreCooker, ItemOreCooker.class, OreCooker.getUnlocalizedName());		
			if (ModOptions.showVerboseStartup && block != null)
				System.out.println("[" + ModDetails.ModName + "] Registered Block: " + block.getLocalizedName() + "@" + Block.getIdFromBlock(block));
		}
	}
	
	private static void registerTFCBlocks()
	{
		//ModOptions.enableLogPileMod = true;
        if (ModOptions.enableLogPileMod)
		{        	
        	try 
        	{
        		TfcLogPileRenderId = -1;
        		
        		String key = GameData.getBlockRegistry().getNameForObject(TFCBlocks.logPile);
    			String name = TFCBlocks.logPile.getUnlocalizedName().replace("tile.", "");

    			TfcLogPile = new BlockTFCLogPile().setBlockName(name).setHardness(10F).setResistance(1F);
        		
				GameRegistry.addSubstitutionAlias(key, GameRegistry.Type.BLOCK, TfcLogPile);
				GameRegistry.addSubstitutionAlias(key, GameRegistry.Type.ITEM, new ItemBlockEmpty(TfcLogPile));
			} 
        	catch (Exception e) 
        	{
        		System.out.println("** ERROR ** [" + ModDetails.ModName + "] addSubstitutionAlias failed (TfcLogPile).");
        		
        		ModOptions.enableLogPileMod = false;
        		TfcLogPile = null;
			}
		}
	}
}
