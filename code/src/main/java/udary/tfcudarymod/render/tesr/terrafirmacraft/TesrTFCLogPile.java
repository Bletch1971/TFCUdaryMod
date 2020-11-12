package udary.tfcudarymod.render.tesr.terrafirmacraft;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Render.TESR.TESRBase;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.Constant.Global;

public class TesrTFCLogPile extends TESRBase
{
	protected Tessellator tessellator = Tessellator.instance;

	protected void renderLog(float originX, float originY, float originZ, boolean[] renderFaces, String woodType)
	{
		float maxX = originX + (0.25f);
        float maxY = originY + 0.25f;
        float maxZ = originZ + (1.0f);
        
        float a = 0.0f;
        float b = 0.25f;
        float c = 1.0f;
                
       	bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/wood/trees/" + woodType + " Log Top.png"));
       	//bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/devices/Log Pile End.png"));
        
		//back
       	if(renderFaces[0])
       	{
       		tessellator.startDrawingQuads();
			tessellator.setNormal(0, 0, 1.0f);
			tessellator.addVertexWithUV(originX, maxY, originZ, c, a);
			tessellator.addVertexWithUV(maxX, maxY, originZ, c, c);
			tessellator.addVertexWithUV(maxX, originY, originZ, a, c);
			tessellator.addVertexWithUV(originX, originY, originZ, a, a);
			tessellator.draw();
       	}
		
		//front
       	if(renderFaces[1])
       	{
			tessellator.startDrawingQuads();
			tessellator.setNormal(0, 0, -1.0f);
			tessellator.addVertexWithUV(originX, originY, maxZ, a, a);
			tessellator.addVertexWithUV(maxX, originY, maxZ, a, c);
			tessellator.addVertexWithUV(maxX, maxY, maxZ, c, c);
			tessellator.addVertexWithUV(originX, maxY, maxZ, c, a);
			tessellator.draw();
       	}
		
		bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/wood/trees/" + woodType + " Log Side.png"));
		//bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/devices/Log Pile Side 0.png"));
		
		// top
		if(renderFaces[2])
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(0, 1.0f, 0);
			tessellator.addVertexWithUV(originX, maxY, originZ, a, a);
			tessellator.addVertexWithUV(originX, maxY, maxZ, c, a);
			tessellator.addVertexWithUV(maxX, maxY, maxZ, c, b);
			tessellator.addVertexWithUV(maxX, maxY, originZ, a, b);
			tessellator.draw();
		}
		
		//bottom
		if(renderFaces[3])
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(0, -1.0f, 0);
			tessellator.addVertexWithUV(maxX, originY, originZ, b, c);
			tessellator.addVertexWithUV(maxX, originY, maxZ, a, c);
			tessellator.addVertexWithUV(originX, originY, maxZ, a, a);
			tessellator.addVertexWithUV(originX, originY, originZ, b, a);
			tessellator.draw();
		}

		bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/wood/trees/" + woodType + " Log Side.png"));
		//bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/blocks/devices/Log Pile Side 1.png"));
		
		//right
		if(renderFaces[4])
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0f, 0, 0);
			tessellator.addVertexWithUV(maxX, maxY, originZ, a, a);
			tessellator.addVertexWithUV(maxX, maxY, maxZ, c, a);
			tessellator.addVertexWithUV(maxX, originY, maxZ, c, b);
			tessellator.addVertexWithUV(maxX, originY, originZ, a, b);
			tessellator.draw();
		}
		
		//left
		if(renderFaces[5])
		{
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0f, 0, 0);
			tessellator.addVertexWithUV(originX, originY, originZ, a, b);
			tessellator.addVertexWithUV(originX, originY, maxZ, c, b);
			tessellator.addVertexWithUV(originX, maxY, maxZ, c, a);
			tessellator.addVertexWithUV(originX, maxY, originZ, a, a);
			tessellator.draw();
		}
	}
	
	protected void renderAt(TELogPile tileEntity, double xDis, double yDis, double zDis)
	{
		if (tileEntity == null)
			return;

		if (tileEntity.getWorldObj() != null && tileEntity.getNumberOfLogs() != 0)
		{
			int meta = tileEntity.getBlockMetadata();
			
			GL11.glPushMatrix(); //start
			
			GL11.glTranslatef((float)xDis + 0.0F, (float)yDis + 0.0F, (float)zDis + 0.0F); //size
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			GL11.glTranslatef(0.5f, 0.0f, 0.5f); //size
			GL11.glRotatef((meta*90), 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5f, 0.0f, -0.5f); //size
			
			int logStack = 0;
			int logStackLog = 0;
			
			for (int l = 0; l < tileEntity.getNumberOfLogs(); l++)
			{
				while(logStack < tileEntity.storage.length && tileEntity.storage[logStack] == null)
				{
					logStack++;
				}
				
				if (tileEntity.storage[logStack] != null)
				{				
					int layer = ((l + 4) / 4);
					float x = (l % 4)*0.25f;
					float y = (layer - 1)*0.25f;
					float z = 0f;
					
					boolean[] renderFaces = new boolean[6];
					//Back
					renderFaces[0] = true; 
					//Front
					renderFaces[1] = true;
					//Top
					renderFaces[2] = (((tileEntity.getNumberOfLogs()+4)/4)-layer < 1 || ((tileEntity.getNumberOfLogs()%4 <= (l%4)) && (layer == ((tileEntity.getNumberOfLogs()+4)/4) - 1)));
					//Bottom
					renderFaces[3] = (y==0);
					//Right
					renderFaces[4] = (l == (tileEntity.getNumberOfLogs() - 1) || (l%4 == 3));
					//Left
					renderFaces[5] = (l%4 == 0); 

					renderLog(x, y, z, renderFaces, Global.WOOD_ALL[tileEntity.storage[logStack].getItemDamage()]);
					
					logStackLog++;
					if(logStackLog >= tileEntity.storage[logStack].stackSize)
					{
						logStack++;
						logStackLog = 0;
					}
				}
			}			
			
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double xDis, double yDis, double zDis, float f)
	{
		renderAt((TELogPile)tileEntity, xDis, yDis, zDis);
	}
}
