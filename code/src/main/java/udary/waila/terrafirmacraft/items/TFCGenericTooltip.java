package udary.waila.terrafirmacraft.items;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import udary.tfcudarymod.core.ModFluids;
import udary.tfcudarymod.core.ModTags;
import udary.waila.WailaUtils;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemGlassBottle;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.ItemTuyere;
import com.bioxx.tfc.Items.Pottery.ItemPotteryJug;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomAxe;
import com.bioxx.tfc.Items.Tools.ItemCustomBow;
import com.bioxx.tfc.Items.Tools.ItemCustomBucket;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.Items.Tools.ItemCustomFishingRod;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.Items.Tools.ItemCustomPickaxe;
import com.bioxx.tfc.Items.Tools.ItemCustomSaw;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.Items.Tools.ItemFlintSteel;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.Items.Tools.ItemProPick;
import com.bioxx.tfc.Items.Tools.ItemSpindle;
import com.bioxx.tfc.Items.Tools.ItemSteelBucketBlue;
import com.bioxx.tfc.Items.Tools.ItemSteelBucketRed;
import com.bioxx.tfc.Items.Tools.ItemTerraTool;
import com.bioxx.tfc.Items.Tools.ItemWeapon;
import com.bioxx.tfc.api.Metal;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TFCGenericTooltip 
{
    @SubscribeEvent 
    public void onTooltip(ItemTooltipEvent event)
    {
    	ItemStack itemStack = event.itemStack;
    	if (itemStack == null) return;
    	    		
    	Item item = itemStack.getItem();
    	if (item == null) return;
    	   	
		if (item instanceof ItemChisel ||
			item instanceof ItemCustomAxe ||
			item instanceof ItemCustomBow ||
			item instanceof ItemCustomFishingRod ||
			item instanceof ItemCustomHoe ||
			item instanceof ItemCustomPickaxe ||
			item instanceof ItemCustomSaw ||
			item instanceof ItemFirestarter ||
			item instanceof ItemFlintSteel ||
			item instanceof ItemKnife ||
			item instanceof ItemProPick ||
			item instanceof ItemSpindle ||
			item instanceof ItemTerraTool ||
			item instanceof ItemWeapon ||
			item instanceof ItemTFCArmor ||
			item instanceof ItemTuyere)
		{
			String damageString = WailaUtils.getDamageInformation(itemStack);
			if (damageString != "")
	    		event.toolTip.add(damageString);
		}
		
		if (item instanceof ItemOre)
		{
			Metal metal = ((ItemOre)item).getMetalType(itemStack);
			String metalString = WailaUtils.getMetalInformation(metal, true);
			if (metalString != "")
	    		event.toolTip.add(metalString);
		}
		
		if (item instanceof ItemFoodTFC)
		{
			if (event.entityPlayer != null)
			{
				String tasteFactorString = WailaUtils.getTasteFactorInformation(itemStack, event.entityPlayer);
				if (tasteFactorString != "")
		    		event.toolTip.add(tasteFactorString);
			}
		}
		
		if (item instanceof ItemTerra)
		{
			String unlocalizedName = item.getUnlocalizedName();
			if (unlocalizedName.endsWith("Quern"))
			{
				String damageString = WailaUtils.getDamageInformation(itemStack);
				if (damageString != "")
		    		event.toolTip.add(damageString);
			}
		}
		
		if (item instanceof ItemCustomBucket)
		{
			String capacityString = WailaUtils.getCapacityInformation(ModFluids.WoodenBucketVolume);
			if (capacityString != "")
				event.toolTip.add(capacityString);
		}
		
		if (item instanceof ItemCustomBucketMilk)
		{
			// add 'Amount' to the front of the amount line (if exists)
			if (event.toolTip.size() > 1)
				event.toolTip.set(1, StatCollector.translateToLocal("gui.food.amount")+" "+event.toolTip.get(1));
			
			ArrayList<String> arraylist = new ArrayList<String>();
			
			// add the size information
			ItemTerra.addSizeInformation(itemStack, arraylist);
			
			// add the capacity information
			String capacityString = WailaUtils.getCapacityInformation(ModFluids.WoodenBucketVolume);
			if (capacityString != "")
				event.toolTip.add(capacityString);

			// insert the arraylist before the amount information 
			event.toolTip.addAll(1, arraylist);
			
			// add the taste information
			NBTTagCompound nbt = itemStack.getTagCompound();
			if (nbt != null)
			{
				if (nbt.hasKey(ModTags.TAG_FOOD_WEIGHT) && nbt.getFloat(ModTags.TAG_FOOD_WEIGHT) != 999)
				{
					if (TFC_Core.showCtrlInformation())
						ItemFoodTFC.addTasteInformation(itemStack, event.entityPlayer, event.toolTip);
					else
						event.toolTip.add(StatCollector.translateToLocal("gui.showtaste"));				
				}
			}
		}
		
		if (item instanceof ItemGlassBottle)
		{
			String capacityString = WailaUtils.getCapacityInformation(ModFluids.GlassBottleVolume);
			if (capacityString != "")
				event.toolTip.add(capacityString);
		}
		
		if (item instanceof ItemPotteryJug)
		{
			if (itemStack.getItemDamage() > 0)
			{
				String capacityString = WailaUtils.getCapacityInformation(ModFluids.CeramicJugVolume);
				if (capacityString != "")
					event.toolTip.add(2, capacityString);
			}
		}
		
		if (item instanceof ItemSteelBucketBlue)
		{
			String capacityString = WailaUtils.getCapacityInformation(ModFluids.BlueSteelBucketVolume);
			if (capacityString != "")
				event.toolTip.add(capacityString);
		}
		
		if (item instanceof ItemSteelBucketRed)
		{
			String capacityString = WailaUtils.getCapacityInformation(ModFluids.RedSteelBucketVolume);
			if (capacityString != "")
				event.toolTip.add(capacityString);
		}
    }
}
