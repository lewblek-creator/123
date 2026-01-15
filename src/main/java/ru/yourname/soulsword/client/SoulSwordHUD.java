package ru.yourname.soulsword.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.util.ResourceLocation;
import ru.yourname.soulsword.SoulSwordMod;
import ru.yourname.soulsword.item.ItemSoulSword;
import ru.yourname.soulsword.progression.AwakeningStage;
import ru.yourname.soulsword.soul.SoulData;
import net.minecraft.client.gui.Gui;

@Mod.EventBusSubscriber(modid = SoulSwordMod.MODID, value = Side.CLIENT)
public class SoulSwordHUD {

    private static final int ICON_SIZE = 12;
    private static final ResourceLocation ICON_STAGE =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_stage.png");
    private static final ResourceLocation ICON_SOUL =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_soul.png");
    private static final ResourceLocation ICON_VAMPIRISM =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_vampirism.png");
    private static final ResourceLocation ICON_WAVE_PURPLE =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_wave_purple.png");
    private static final ResourceLocation ICON_WAVE_RED =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_wave_red.png");
    private static final ResourceLocation ICON_WAVE_GOLD =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_wave_gold.png");
    private static final ResourceLocation ICON_BLOOD_SHIELD =
            new ResourceLocation(SoulSwordMod.MODID, "textures/gui/icon_blood_shield.png");

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent.Text event) {

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return;

        ItemStack stack = mc.player.getHeldItemMainhand();
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemSoulSword)) return;

        ScaledResolution res = event.getResolution();
        int x = 8;
        int y = res.getScaledHeight() - 58;
        int textX = x + ICON_SIZE + 4;

        AwakeningStage stage = SoulData.getAwakeningStage(stack);
        AwakeningStage next = stage.next();
        int stageNumber = SoulData.getAwakeningStageId(stack) + 1;

        int souls = SoulData.getSouls(stack);
        float bonus = SoulData.getBonusDamage(stack);

        drawIcon(mc, ICON_STAGE, x, y + 8);
        mc.fontRenderer.drawStringWithShadow(
                I18n.format("hud.soulsword.stage", stageNumber),
                textX, y + 10, 0xFFFFFF
        );

        drawIcon(mc, ICON_SOUL, x, y + 18);
        mc.fontRenderer.drawStringWithShadow(
                next != null
                        ? I18n.format("hud.soulsword.souls", souls, next.getRequiredKills())
                        : I18n.format("hud.soulsword.souls.max", souls),
                textX, y + 20, 0xFFFFFF
        );

        drawIcon(mc, ICON_VAMPIRISM, x, y + 28);
        mc.fontRenderer.drawStringWithShadow(
                I18n.format("hud.soulsword.damage", bonus),
                textX, y + 30, 0xFFFFFF
        );

        int stageId = stageNumber - 1;
        int iconY = y + 42;
        int iconX = x;

        if (stage.getMeleeVampirism() > 0f) {
            drawIcon(mc, ICON_VAMPIRISM, iconX, iconY);
            iconX += ICON_SIZE + 4;
        }

        ResourceLocation waveIcon = getWaveIcon(stageId);
        if (waveIcon != null) {
            drawIcon(mc, waveIcon, iconX, iconY);
            iconX += ICON_SIZE + 4;
        }

        if (stageId >= 4) {
            drawIcon(mc, ICON_BLOOD_SHIELD, iconX, iconY);
        }
    }

    private static ResourceLocation getWaveIcon(int stageId) {
        if (stageId >= 9) return ICON_WAVE_GOLD;
        if (stageId >= 5) return ICON_WAVE_RED;
        if (stageId >= 3) return ICON_WAVE_PURPLE;
        return null;
    }

    private static void drawIcon(Minecraft mc, ResourceLocation icon, int x, int y) {
        if (icon == null) return;
        GlStateManager.color(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }
}

