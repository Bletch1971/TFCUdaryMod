package udary.tfcudarymod.containerslots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class SlotAlloyCalculator extends SlotSize 
{
	protected EntityPlayer player;
	
	public SlotAlloyCalculator(EntityPlayer player, IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) 
	{
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
		
		this.player = player;
		
		this.setSize(EnumSize.HUGE);
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		// allow all smeltable items.
		return (is != null && 
				is.getItem() instanceof ISmeltable);
	}
}
