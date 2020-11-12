package udary.tfcudarymod.blocks.terrafirmacraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import udary.tfcudarymod.TFCUdaryMod;
import udary.tfcudarymod.core.ModBlocks;
import udary.tfcudarymod.core.ModItems;
import udary.tfcudarymod.handlers.GuiHandler;
import udary.tfcudarymod.tileentities.terrafirmacraft.TileEntityTFCLogPile;

import com.bioxx.tfc.Blocks.BlockLogPile;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.TFCItems;

public class BlockTFCLogPile extends BlockLogPile
{
	protected boolean isOpaqueCube;
	protected boolean renderAsNormalBlock;
	protected int renderType;
	
	public BlockTFCLogPile()
	{
		super();
		
		this.isOpaqueCube = false;
		this.renderAsNormalBlock = false;
		this.renderType = ModBlocks.TfcLogPileRenderId;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return this.isOpaqueCube;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return this.renderAsNormalBlock;
	}

	@Override
	public int getRenderType()
	{
		return this.renderType;
	}
	
	protected boolean isLog(ItemStack is)
	{
		if (is == null)
			return false;

		Item item = is.getItem();
		
		return 	item == TFCItems.logs || 
				item == ModItems.TfcLog;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return true;
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity == null || !(tileEntity instanceof TELogPile))
			return false;
		
		ItemStack is = player.getCurrentEquippedItem();

		if (is != null && isLog(is))
			return false;
		
		//player.openGui(TerraFirmaCraft.instance, 0, world, x, y, z);
		player.openGui(TFCUdaryMod.instance, GuiHandler.GuiIdTFCLogPile, world, x, y, z);
		
		return true;
	}

//	@Override
//	public void Eject(World world, int x, int y, int z)
//	{
//		if (!world.isRemote)
//		{
//			TileEntity tileEntity = world.getTileEntity(x, y, z);
//			
//			if (tileEntity != null && tileEntity instanceof TileEntityTFCLogPile)
//			{
//				TileEntityTFCLogPile logPile = (TileEntityTFCLogPile)tileEntity;
//				
//				// prevent a player trying to add wood to a removed log pile
//				logPile.forceCloseContainers();				
//			}
//		}
//		
//		super.Eject(world, x, y, z);		
//	}

//	@Override
//	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i)
//	{
//	}
//
//	@Override
//	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
//	{
//		return world.setBlockToAir(x, y, z);
//	}
//	
//	@Override
//	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
//	{
//		Eject(world, x, y, z);
//	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityTFCLogPile();
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TELogPile)
		{
			TELogPile logPile = (TELogPile)tileEntity;
			
			return logPile.getNumberOfLogs() >= 16;
		}
			
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TELogPile)
		{
			TELogPile logPile = (TELogPile)tileEntity;
			
			if (logPile.getNumberOfLogs() > 0)
				return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + Math.min((Math.ceil(logPile.getNumberOfLogs() / 4f)) * 0.25, 1), (double)z + 1);
		}

		return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + 0.25, (double)z + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TELogPile)
		{
			TELogPile logPile = (TELogPile)tileEntity;
			
			if (logPile.getNumberOfLogs() > 0)
				return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + Math.min((Math.ceil(logPile.getNumberOfLogs() / 4f)) * 0.25, 1), (double)z + 1);
		}

		return AxisAlignedBB.getBoundingBox(x, (double)y + 0, (double)z + 0, (double)x + 1, y + 0.25, (double)z + 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (tileEntity != null && tileEntity instanceof TELogPile)
		{
			TELogPile logPile = (TELogPile)tileEntity;
			
			if (logPile.getNumberOfLogs() > 0)
			{
				this.setBlockBounds(0f, 0f, 0f, 1f, (float) Math.min((Math.ceil(logPile.getNumberOfLogs()/4f))*0.25, 1f), 1f);
				return;
			}
		}

		this.setBlockBounds(0f, 0f, 0f, 0f, 0.25f, 0f);
	}
}
