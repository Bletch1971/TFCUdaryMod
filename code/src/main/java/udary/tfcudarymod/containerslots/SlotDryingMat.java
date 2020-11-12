package udary.tfcudarymod.containerslots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import com.bioxx.tfc.api.Enums.EnumSize;

public class SlotDryingMat extends SlotSize 
{
	protected EntityPlayer player;
	
	public SlotDryingMat(EntityPlayer player, IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition)
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
		
		this.player = player;
		
		this.setSize(EnumSize.HUGE);
	}
}
