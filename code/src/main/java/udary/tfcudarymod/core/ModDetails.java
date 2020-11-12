package udary.tfcudarymod.core;

public class ModDetails 
{
	public static final String ModID = "tfcudarymod";
	public static final String ModName = "TFCUdaryMod";

	public static final int VersionMajor = 0;
	public static final int VersionMinor = 2;
	public static final int VersionRevision = 34;

	public static final String ModVersion = VersionMajor + "." + VersionMinor + "." + VersionRevision;
	public static final String ModDependencies = "after:TerraFirmaCraft";
	public static final String ModChannel = "TFCUdaryMod";
	public static final String SERVER_PROXY_CLASS = "udary.tfcudarymod.core.ModCommonProxy";
	public static final String CLIENT_PROXY_CLASS = "udary.tfcudarymod.core.ModClientProxy";
	
	public static final String AssetPath = "/assets/" + ModID + "/";
	public static final String AssetPathGui = "textures/gui/";
	
	public static final String ConfigFilePath = "/config/udary/";
	public static final String ConfigFileName = "TFCUdaryMod.cfg";
	
	public static final int GuiOffset = 10000;
	public static final int GuiTFCOffset = 20000;
	
	public static final String VersionCheckerUpdatePath = "https://dl.dropboxusercontent.com/u/87519140/TFC/tfc_{0}/update.json";
	
	public static final String MODID_NEI = "NotEnoughItems";
	public static final String MODID_TFC = "terrafirmacraft";
	public static final String MODID_WAILA = "Waila";

	public static final String MODNAME_NEI = "Not Enough Items";
	public static final String MODNAME_TFC = "TerraFirmaCraft";
	public static final String MODNAME_WAILA = "Waila";
}
