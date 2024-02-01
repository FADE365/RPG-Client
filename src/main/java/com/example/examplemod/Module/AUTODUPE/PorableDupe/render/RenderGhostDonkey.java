package com.example.examplemod.Module.AUTODUPE.PorableDupe.render;

import com.example.examplemod.Module.AUTODUPE.PorableDupe.FamilyFunPack;
import net.minecraft.client.renderer.entity.RenderAbstractHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/* Render ghost donkey with its special (yet shitty) skin */

@SideOnly(Side.CLIENT)
public class RenderGhostDonkey extends RenderAbstractHorse {

  private static final ResourceLocation texture = new ResourceLocation(FamilyFunPack.MODID, "textures/entity/horse/ghost_donkey.png");

  public RenderGhostDonkey(RenderManager manager) {
    super(manager, 0.87F);
  }

  protected ResourceLocation getEntityTexture(AbstractHorse p_getEntityTexture_1_) {
    return RenderGhostDonkey.texture;
  }
}
