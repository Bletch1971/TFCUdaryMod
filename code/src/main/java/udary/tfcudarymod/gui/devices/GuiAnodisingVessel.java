package udary.tfcudarymod.gui.devices;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import udary.common.enums.EnumRecipeMatchType;
import udary.tfcudarymod.containers.devices.ContainerAnodisingVessel;
import udary.tfcudarymod.core.ModDetails;
import udary.tfcudarymod.core.managers.AnodisingVesselAnodiseManager;
import udary.tfcudarymod.core.recipes.AnodisingVesselAnodiseRecipe;
import udary.tfcudarymod.tileentities.devices.TileEntityAnodisingVessel;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.GUI.GuiContainerTFC;

public class GuiAnodisingVessel extends GuiContainerTFC
{
	protected static final ResourceLocation guiTexture = new ResourceLocation(ModDetails.ModID, ModDetails.AssetPathGui + "gui_AnodisingVessel.png");
	
	public static final int BUTTON_SEAL = 0;
	public static final int BUTTON_EMPTY = 1;
	
	protected TileEntityAnodisingVessel tileEntity;
	protected InventoryPlayer inventoryPlayer;
	protected EntityPlayer player;

	public GuiAnodisingVessel(InventoryPlayer inventoryPlayer, TileEntityAnodisingVessel tileEntity, World world, int x, int y, int z)
	{
		super(new ContainerAnodisingVessel(inventoryPlayer, tileEntity, world, x, y, z), 176, 85);

		this.tileEntity = tileEntity;
		this.inventoryPlayer = inventoryPlayer;
		this.player = this.inventoryPlayer.player;
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		if (tileEntity.getSealed())
		{
			((GuiButton)buttonList.get(BUTTON_SEAL)).displayString = StatCollector.translateToLocal("gui.Barrel.Unseal");
			((GuiButton)buttonList.get(BUTTON_EMPTY)).enabled = false;
		}
		else
		{
			((GuiButton)buttonList.get(BUTTON_SEAL)).displayString = StatCollector.translateToLocal("gui.Barrel.Seal");
			((GuiButton)buttonList.get(BUTTON_EMPTY)).enabled = true;
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		
		createButtons();
	}

	@SuppressWarnings("unchecked")
	protected void createButtons()
	{
		buttonList.clear();
		
		if (!tileEntity.getSealed())
			buttonList.add(new GuiButton(BUTTON_SEAL, guiLeft+48, guiTop+48, 50, 20, StatCollector.translateToLocal("gui.Barrel.Seal")));
		else
			buttonList.add(new GuiButton(BUTTON_SEAL, guiLeft+48, guiTop+48, 50, 20, StatCollector.translateToLocal("gui.Barrel.Unseal")));
		
		buttonList.add(new GuiButton(BUTTON_EMPTY, guiLeft+98, guiTop+48, 50, 20, StatCollector.translateToLocal("gui.Barrel.Empty")));
	}

	@Override
	public void drawTooltip(int mouseX, int mouseY, String text) 
	{
		List<String> list = new ArrayList<String>();
		list.add(text);
		
		drawHoveringText(list, mouseX, mouseY+15, this.fontRendererObj);
		RenderHelper.disableStandardItemLighting();
		
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		switch (guiButton.id)
		{
			case BUTTON_SEAL:
				if (tileEntity.getSealed())
					tileEntity.actionUnSeal(0, player);
				else
					tileEntity.actionSeal(0, player);
				break;
			case BUTTON_EMPTY:
				tileEntity.actionEmpty();
				break;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		TFC_Core.bindTexture(guiTexture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if (tileEntity != null)
		{
			FluidStack fs = tileEntity.getFluidStack();
			ItemStack is = tileEntity.getStackInSlot(ContainerAnodisingVessel.SLOT_SOLUTE);			
			
			AnodisingVesselAnodiseRecipe recipe = AnodisingVesselAnodiseManager.getInstance().findRecipe(fs, is, EnumRecipeMatchType.MINIMUM);
			
			if (fs != null)
			{
				int scale = tileEntity.getLiquidScaled(50);
				
				IIcon liquidIcon = fs.getFluid().getIcon(fs);
				int color = fs.getFluid().getColor(fs);
				byte f1 = (byte) (color >> 16 & 255);
				byte f2 = (byte) (color >>  8 & 255);
				byte f3 = (byte) (color       & 255);
				byte f4 = (byte) (0xaa        & 255);
				
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				GL11.glColor4ub(f1, f2, f3, f4);
				
				int div = (int)Math.floor(scale/8);
				int rem = scale-(div*8);
				drawTexturedModelRectFromIcon(guiLeft+12, guiTop+65-scale, liquidIcon, 8, div > 0 ? 8 : rem);
				
				for(int c = 0; div > 0 && c < div; c++)
				{
					drawTexturedModelRectFromIcon(guiLeft+12, guiTop+65-(8+(c*8)), liquidIcon, 8, 8);
				}
				
				GL11.glColor3f(0, 0, 0);

				drawCenteredString(fontRendererObj, EnumChatFormatting.WHITE+fs.getLocalizedName(), guiLeft+98, guiTop+4, 0x555555);
			}
			
			if (recipe != null)
			{
				drawCenteredString(fontRendererObj, EnumChatFormatting.UNDERLINE+recipe.getLocalizedResultName(false), guiLeft+98, guiTop+15, 0x555555);

				if (tileEntity.getCanAnodise())
				{
					int percent = tileEntity.getProcessPercentage();
					drawCenteredString(fontRendererObj, recipe.getLocalizedProcessingMessage()+(percent > 0 ? " ("+String.valueOf(percent)+"%)" : ""), guiLeft+98, guiTop+72, 0x555555);
				}
				else if (tileEntity.getSealed())
					drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.idle"), guiLeft+98, guiTop+72, 0x555555);
			}
			else if (tileEntity.getSealed())
				drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.idle"), guiLeft+98, guiTop+72, 0x555555);
		}
		
		PlayerInventory.drawInventory(this, width, height, ySize - PlayerInventory.invYSize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (mouseInRegion(12, 15, 9, 50, mouseX, mouseY))
		{
			if (tileEntity != null)
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(tileEntity.getFluidLevel()+StatCollector.translateToLocal("gui.barrel.unit1"));
				
				drawHoveringText(list, mouseX-guiLeft, mouseY-guiTop+8, this.fontRendererObj);
			}
		}
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String text, int x, int y, int color)
	{
		fontrenderer.drawString(text, x - fontrenderer.getStringWidth(text) / 2, y, color);
	}
}
