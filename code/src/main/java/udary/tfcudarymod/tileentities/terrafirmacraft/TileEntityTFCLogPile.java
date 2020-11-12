package udary.tfcudarymod.tileentities.terrafirmacraft;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import udary.tfcudarymod.containers.terrafirmacraft.ContainerTFCLogPile;

import com.bioxx.tfc.TileEntities.TELogPile;

public class TileEntityTFCLogPile extends TELogPile
{
	protected static final String FIELD_LOGPILEOPENERS = "logPileOpeners";
	
	/**
	 * Used to keep the rendering up to date, don't update manually (use getNumberOfLogs() instead)
	 */
	protected int numberOfLogs = 0;

	protected Field getField(String fieldName)
	{
		Field[] fields = TELogPile.class.getDeclaredFields();
		
		for (Field field : fields)
		{
			if (field.getName() == fieldName)
				return field;
		}		
		
		return null;
	}

	protected int getLogPileOpeners()
	{
		Field field = getField(FIELD_LOGPILEOPENERS);
		if (field == null)
			return 0;
		
		try
		{
			return field.getInt(this);
		} 
		catch (Exception e) 
		{
			return 0;
		}
	}
	
	protected void setLogPileOpeners(int value)
	{
		Field field = getField(FIELD_LOGPILEOPENERS);
		if (field == null)
			return;

		try
		{
			field.setInt(this, value);
		} 
		catch (Exception e) 
		{
			return;
		}
	}
	
	@Override
	public ItemStack takeLog(int slot)
	{
		return null;
	}
	
	@Override
	public void closeInventory()
	{
		//--logPileOpeners;
		int logPileOpeners = getLogPileOpeners();
		setLogPileOpeners(--logPileOpeners);
	}
	
	/**
	 * Closes all open containers for this log pile
	 */
	@SuppressWarnings("rawtypes")
	public void forceCloseContainers()
	{
		float f = 5.0F;
		List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - f, this.yCoord - f, this.zCoord - f, this.xCoord + 1 + f, this.yCoord + 1 + f, this.zCoord + 1 + f));
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			EntityPlayer entityplayer = (EntityPlayer)iterator.next();

			if (entityplayer.openContainer instanceof ContainerTFCLogPile)
			{
				if(((ContainerTFCLogPile)entityplayer.openContainer).isLinkedLogPile(this))
				{
					entityplayer.closeScreen();
				}
			}
		}
		
		//logPileOpeners = 0;
		setLogPileOpeners(0);
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	@Override
	public void updateEntity()
	{
		if (!worldObj.isSideSolid(xCoord , yCoord-1, zCoord, ForgeDirection.UP))
		{
			if (!mergeDown())
			{
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
	
		int logPileOpeners = getLogPileOpeners();
		
		if (getNumberOfLogs() == 0 && logPileOpeners == 0)
		{
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
		
		//Update the block so SMP clients stay in sync
		if (numberOfLogs != getNumberOfLogs())
		{
			numberOfLogs = getNumberOfLogs();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public boolean mergeDown()
	{
		TileEntity teBelow = (TileEntity)worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		
		if (teBelow != null && teBelow instanceof TELogPile)
		{
			TELogPile logPileBelow = (TELogPile)teBelow;
			
			for (int i = 0; i < logPileBelow.getSizeInventory(); i++)
			{
				for (int j = 0; j < getSizeInventory(); j++)
				{
					if (storage[j] != null)
					{
						if (logPileBelow.storage[i] == null)
						{
							logPileBelow.setInventorySlotContents(i, decrStackSize(j, storage[j].stackSize));
						}
						else
						{
							if (logPileBelow.contentsMatch(i, storage[j]))
							{
								int logs = logPileBelow.getInventoryStackLimit() - logPileBelow.storage[i].stackSize;
								logPileBelow.injectContents(i, decrStackSize(j, logs).stackSize);
							}
						}
					}
				}
			}
			
			if (logPileBelow.getNumberOfLogs() == 16)
			{
				return true;
			}
		}
		
		return false;
	}
}
