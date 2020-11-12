package udary.tfcudarymod;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import udary.tfcudarymod.core.ModBlocks;
import udary.tfcudarymod.core.ModCommonProxy;
import udary.tfcudarymod.core.ModDetails;
import udary.tfcudarymod.core.ModFluids;
import udary.tfcudarymod.core.ModHeat;
import udary.tfcudarymod.core.ModItems;
import udary.tfcudarymod.core.ModOreDictionary;
import udary.tfcudarymod.core.ModRecipes;
import udary.tfcudarymod.core.ModTabs;
import udary.tfcudarymod.core.managers.AnodisingVesselAnodiseManager;
import udary.tfcudarymod.core.managers.AnodisingVesselFluidManager;
import udary.tfcudarymod.core.managers.DryingMatManager;
import udary.tfcudarymod.core.managers.EvaporatorPanManager;
import udary.tfcudarymod.core.managers.OreCookerManager;
import udary.tfcudarymod.core.managers.TuckerBagManager;
import udary.tfcudarymod.core.player.ModPlayerTracker;
import udary.tfcudarymod.handlers.ChunkEventHandler;
import udary.tfcudarymod.handlers.CraftingHandler;
import udary.tfcudarymod.handlers.FuelHandler;
import udary.tfcudarymod.handlers.network.InitClientWorldPacket;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModDetails.ModID, name = ModDetails.ModName, version = ModDetails.ModVersion, dependencies = ModDetails.ModDependencies)
public class TFCUdaryMod
{
	@Instance(ModDetails.ModID)
	public static TFCUdaryMod instance;

	@SidedProxy(clientSide = ModDetails.CLIENT_PROXY_CLASS, serverSide = ModDetails.SERVER_PROXY_CLASS)
	public static ModCommonProxy proxy;
	
	public File getMinecraftDirectory()
	{
		return proxy.getMinecraftDirectory();
	}
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e)
	{
		ModContainer mod = Loader.instance().getIndexedModList().get(ModDetails.MODID_TFC);
		if (mod != null)
		{
			String updatePath = ModDetails.VersionCheckerUpdatePath.replace("{0}", mod.getVersion());
			FMLInterModComms.sendRuntimeMessage(ModDetails.ModID, "VersionChecker", "addVersionCheck", updatePath);
		}

		instance = this;		
		
		// Load our settings
		proxy.loadOptions();
		
		proxy.registerTickHandler();
		
		ModBlocks.initialise();	

		// Register Key Bindings(Client only)
		proxy.registerKeys();
		// Register KeyBinding Handler (Client only)
		proxy.registerKeyBindingHandler();
		// Register Handler (Client only)
		proxy.registerHandlers();
		// Register Tile Entities
		proxy.registerTileEntities(true);
		// Register Sound Handler (Client only)
		proxy.registerSoundHandler();
		
		ModFluids.initialise();
		ModItems.initialise();
		ModTabs.initialise();
		
		// Register Gui Handler
		proxy.registerGuiHandler();		
	}

	@EventHandler
	public void initialize(FMLInitializationEvent e)
	{
		AnodisingVesselAnodiseManager.initialise();
		AnodisingVesselFluidManager.initialise();
		DryingMatManager.initialise();
		EvaporatorPanManager.initialise();
		OreCookerManager.initialise();
		TuckerBagManager.initialise();

		// Register packets in the TFC PacketPipeline
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(InitClientWorldPacket.class);
		
		// Register the player tracker
		FMLCommonHandler.instance().bus().register(new ModPlayerTracker());
		
		// Register the tool classes
		proxy.registerToolClasses();

		// Register Crafting Handler
		FMLCommonHandler.instance().bus().register(new CraftingHandler());

		// Register the Chunk Load/Save Handler
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
		
		// Register all the render stuff for the client
		proxy.registerRenderInformation();

		// Register Liquids
		ModFluids.initialiseFluidContainers();
		proxy.registerFluidIcons();
		
		ModOreDictionary.initialise();
		ModRecipes.initialise();
		
		ModHeat.initialise();

		GameRegistry.registerFuelHandler(new FuelHandler());
		
		// Register WAILA classes
		proxy.registerWailaClasses();
		proxy.hideNEIItems();		
	}

	@EventHandler
	public void postInitialize(FMLPostInitializationEvent e)
	{
	}
}
