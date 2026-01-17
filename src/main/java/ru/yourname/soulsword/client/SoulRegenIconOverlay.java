package ru.yourname.soulsword.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.yourname.soulsword.SoulSwordMod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = SoulSwordMod.MODID, value = Side.CLIENT)
public class SoulRegenIconOverlay {

    private static final ResourceLocation REGEN_ICON =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/regen_inventory.png");

    @SubscribeEvent
    public static void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
        GuiScreen gui = event.getGui();
        if (!(gui instanceof GuiInventory)) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return;

        Collection<PotionEffect> effects = mc.player.getActivePotionEffects();
        if (effects.isEmpty()) return;

        List<PotionEffect> list = new ArrayList<>(effects);
        Collections.sort(list);

        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPotion() == MobEffects.REGENERATION) {
                index = i;
                break;
            }
        }

        if (index == -1) return;

        int guiLeft = getPrivateInt(gui, "guiLeft", "field_147003_i");
        int guiTop = getPrivateInt(gui, "guiTop", "field_147009_r");
        int xSize = getPrivateInt(gui, "xSize", "field_146999_f");

        int x = guiLeft - 124;
        if (guiLeft < 124) {
            x = guiLeft + xSize + 6;
        }

        int spacing = 33;
        if (list.size() > 5) {
            spacing = 132 / (list.size() - 1);
        }

        int y = guiTop + index * spacing;

        GlStateManager.color(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(REGEN_ICON);
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    private static int getPrivateInt(GuiScreen gui, String deobf, String obf) {
        return ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer) gui, deobf, obf);
    }
}
