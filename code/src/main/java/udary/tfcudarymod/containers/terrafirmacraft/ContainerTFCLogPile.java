package udary.tfcudarymod.containers.terrafirmacraft;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.ContainerLogPile;
import com.bioxx.tfc.TileEntities.TELogPile;

public class ContainerTFCLogPile extends ContainerLogPile
{
	protected TELogPile logpile;
	
	public ContainerTFCLogPile(InventoryPlayer inventoryPlayer, TELogPile logPile, World world, int x, int y, int z) 
	{
		super(inventoryPlayer, logPile, world, x, y, z);
		
		this.logpile = logPile;
	}

	/**
	 * Is this container linked to the passed Tile Entity. 
	 */
	public boolean isLinkedLogPile(TELogPile tileEntity)
	{
		return tileEntity == logpile;
	}
}
