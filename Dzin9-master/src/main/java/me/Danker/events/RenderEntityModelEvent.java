 package me.Danker.events;
 
 import net.minecraft.client.model.ModelBase;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraftforge.fml.common.eventhandler.Event;
 
 public class RenderEntityModelEvent
   extends Event {
   public EntityLivingBase entity;
   public float limbSwing;
   public float limbSwingAmount;
   public float ageInTicks;
   public float headYaw;
   public float headPitch;
   public float scaleFactor;
   public ModelBase model;
   
   public RenderEntityModelEvent(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scaleFactor, ModelBase model) {
     this.entity = entity;
     this.limbSwing = limbSwing;
     this.limbSwingAmount = limbSwingAmount;
     this.ageInTicks = ageInTicks;
     this.headYaw = headYaw;
     this.headPitch = headPitch;
     this.scaleFactor = scaleFactor;
     this.model = model;
   }
 }


/* Location:              C:\Users\niko\OneDrive\Pulpit\ShadyAddons-2.5.0.jar!\cheaters\get\banned\events\RenderEntityModelEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1 */