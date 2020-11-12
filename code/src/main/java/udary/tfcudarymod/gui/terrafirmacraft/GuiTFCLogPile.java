package udary.tfcudarymod.gui.terrafirmacraft;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import udary.tfcudarymod.containers.terrafirmacraft.ContainerTFCLogPile;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.GUI.GuiContainerTFC;
import com.bioxx.tfc.TileEntities.TELogPile;

public class GuiTFCLogPile extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_logpile.png");

	public GuiTFCLogPile(InventoryPlayer inventoryplayer, TELogPile tileEntity, World world, int x, int y, int z)
	{
		super(new ContainerTFCLogPile(inventoryplayer, tileEntity, world, x, y, z), 176, 85);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
	}
}
