package udary.tfcudarymod.items.terrafirmacraft;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import udary.tfcudarymod.core.ModBlocks;

import com.bioxx.tfc.Items.ItemLogs;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.TFCBlocks;

public class ItemTFCLog extends ItemLogs
{
	protected Block logPileBlock;
	
	public ItemTFCLog()
	{
		super();
		
		this.logPileBlock = ModBlocks.TfcLogPile;
	}
	
//	protected static final String METHOD_CREATEPILE = "CreatePile";

//	protected boolean CreateLogPile(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, int dir)
//	{
//		try 
//		{
//			Method method = ItemLogs.class.getDeclaredMethod(METHOD_CREATEPILE, ItemStack.class, EntityPlayer.class, World.class, int.class, int.class, int.class, int.class, int.class);
//			
//			method.setAccessible(true);
//			
//			int before = Block.getIdFromBlock(TFCBlocks.LogPile);
//			int before2 = Block.getIdFromBlock(ModBlocks.TfcLogPile);
//			
//			Boolean result = (Boolean)method.invoke(this, new Object[] { is, player, world, x, y, z, side, dir} );
//			
//			return result.booleanValue();
//		} 
//		catch (Exception e) 
//		{
//			return false;
//		}
//	}
	
	protected boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		TELogPile te = null;
		if(world.isAirBlock(x, y, z) && isValid(world, x, y, z))
		{
			world.setBlock(x, y, z, getLogPileBlock(), l, 3);
			te = (TELogPile)world.getTileEntity(x, y, z);
		}
		else
		{
			return false;
		}

		if(te != null)
		{
			te.storage[0] = new ItemStack(this,1,itemstack.getItemDamage());
			if(entityplayer.capabilities.isCreativeMode)
			{
				te.storage[0] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[1] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[2] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[3] = new ItemStack(this,4,itemstack.getItemDamage());
			}
		}

		return true;
	}

	protected boolean isLogPile(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		
		return isLogPile(block);
	}

	protected boolean isLogPile(Block block)
	{
		if (block == null)
			return false;

		return 	block == TFCBlocks.logPile || 
				block == ModBlocks.TfcLogPile;
	}
	
	@Override
	public boolean isValid(World world, int x, int y, int z)
	{
		return world.isSideSolid(x, y-1, z, ForgeDirection.UP);
	}
	
	protected Block getLogPileBlock()
	{
		return logPileBlock;
	}
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(player.isSneaking() && (!isLogPile(world, x, y, z) || (side != 1 && side != 0)))
			{
				int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
				if (side == 0)
					--y;
				else if (side == 1)
					++y;
				else if (side == 2)
					--z;
				else if (side == 3)
					++z;
				else if (side == 4)
					--x;
				else if (side == 5)
					++x;
				if(world.canPlaceEntityOnSide(getLogPileBlock(), x, y, z, false, side, player, is))
					if (CreatePile(is, player, world, x, y, z, side, dir)) 
					{
						is.stackSize = is.stackSize-1;
						playSound(world, x, y, z);
					}
				return true;
			}
			else if(isLogPile(world, x, y, z))
			{
				TELogPile te = (TELogPile)world.getTileEntity(x, y, z);
				if(te != null)
				{
					if(te.storage[0] != null && te.contentsMatch(0,is)) {
						te.injectContents(0,1);
					} else if(te.storage[0] == null) {
						te.addContents(0, new ItemStack(this,1, is.getItemDamage()));
					} else if(te.storage[1] != null && te.contentsMatch(1,is)) {
						te.injectContents(1,1);
					} else if(te.storage[1] == null) {
						te.addContents(1, new ItemStack(this,1, is.getItemDamage()));
					} else if(te.storage[2] != null && te.contentsMatch(2,is)) {
						te.injectContents(2,1);
					} else if(te.storage[2] == null) {
						te.addContents(2, new ItemStack(this,1, is.getItemDamage()));
					} else if(te.storage[3] != null && te.contentsMatch(3,is)) {
						te.injectContents(3,1);
					} else if(te.storage[3] == null) {
						te.addContents(3, new ItemStack(this,1, is.getItemDamage()));
					} else
					{
						int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
						if (side == 0)
							--y;
						else if (side == 1)
							++y;
						else if (side == 2)
							--z;
						else if (side == 3)
							++z;
						else if (side == 4)
							--x;
						else if (side == 5)
							++x;
						if (!CreatePile(is, player, world, x, y, z, side, dir)) {
							return true;
						}

					}
					playSound(world, x, y, z);
					is.stackSize = is.stackSize-1;
					world.markBlockForUpdate(x, y, z);
					return true;
				}

			}
			else
			{
				int m = is.getItemDamage();
				Block block = m>15?TFCBlocks.woodVert2:TFCBlocks.woodVert;

				if(side == 0 && block.canPlaceBlockAt(world, x, y-1, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y-1, z, false, side, null, is))
				{
					world.setBlock(x, y-1, z, block, m,0x2);
					is.stackSize = is.stackSize-1;
					playSound(world, x, y, z);
				}
				else if(side == 1 && block.canPlaceBlockAt(world, x, y+1, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y+1, z, false, side, null, is))
				{
					world.setBlock(x, y+1, z, block, m,0x2);
					is.stackSize = is.stackSize-1;
					playSound(world, x, y, z);
				}
				else if(side == 2 && block.canPlaceBlockAt(world, x, y, z-1) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y, z-1, false, side, null, is))
				{
					setSide(world, is, m, side, x, y, z-1);
				}
				else if(side == 3 && block.canPlaceBlockAt(world, x, y, z+1) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y, z+1, false, side, null, is))
				{
					setSide(world, is, m, side, x, y, z+1);
				}
				else if(side == 4 && block.canPlaceBlockAt(world, x-1, y, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x-1, y, z, false, side, null, is))
				{
					setSide(world, is, m, side, x-1, y, z);
				}
				else if(side == 5 && block.canPlaceBlockAt(world, x+1, y, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x+1, y, z, false, side, null, is))
				{
					setSide(world, is, m, side, x+1, y, z);
				}
				return true;
			}
		}
		return false;
	}

	protected void playSound(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.logNatural.stepSound.func_150496_b(), (TFCBlocks.logNatural.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.logNatural.stepSound.getPitch() * 0.8F);
	}
	
	@Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
    {
        return true;
    }
}
