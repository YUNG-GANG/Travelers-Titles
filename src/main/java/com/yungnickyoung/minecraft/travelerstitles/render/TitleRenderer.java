package com.yungnickyoung.minecraft.travelerstitles.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.function.Predicate;

public class TitleRenderer<T> {
    public final LinkedList<T> recentEntries = new LinkedList<>();
    public ITextComponent displayedTitle = null;
    public ITextComponent displayedSubTitle = null;
    public int titleTimer = 0;
    public int cooldownTimer = 0;

    // User-customizable text effects
    public int maxRecentListSize;
    public boolean enabled;
    public int titleFadeInTicks;
    public int titleDisplayTime;
    public int titleFadeOutTicks;
    public int titleTextcolor;
    public String titleDefaultTextColor;
    public boolean showTextShadow;
    public float titleTextSize;
    public float titleYOffset;

    public TitleRenderer(
        int maxRecentListSize,
        boolean enabled,
        int fadeInTicks,
        int displayTicks,
        int fadeOutTicks,
        String textColor,
        boolean showTextShadow,
        double textSize,
        double yOffset
    ) {
        this.maxRecentListSize = maxRecentListSize;
        this.enabled = enabled;
        this.titleFadeInTicks = fadeInTicks;
        this.titleDisplayTime = displayTicks;
        this.titleFadeOutTicks = fadeOutTicks;
        this.setColor(textColor);
        this.titleDefaultTextColor = textColor;
        this.showTextShadow = showTextShadow;
        this.titleTextSize = (float)textSize;
        this.titleYOffset = (float)yOffset;
    }

    @SuppressWarnings("deprecation")
    public void renderText(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (displayedTitle != null && titleTimer > 0) {
                float age = (float) titleTimer - event.getPartialTicks();
                int opacity = 255;
                if (titleTimer > titleFadeOutTicks + titleDisplayTime) {
                    float r = (float) (titleFadeInTicks + titleDisplayTime + titleFadeOutTicks) - age;
                    opacity = (int) (r * 255.0F / (float) titleFadeInTicks);
                }

                if (titleTimer <= titleFadeOutTicks) {
                    opacity = (int) (age * 255.0F / (float) titleFadeOutTicks);
                }

                opacity = MathHelper.clamp(opacity, 0, 255);
                if (opacity > 8) {
                    // Set up render system
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef((float) (Minecraft.getInstance().getMainWindow().getScaledWidth() / 2), (float) (Minecraft.getInstance().getMainWindow().getScaledHeight() / 2), 0.0F);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();

                    // Render title
                    RenderSystem.pushMatrix();
                    RenderSystem.scalef(titleTextSize, titleTextSize, titleTextSize);
                    int alpha = opacity << 24 & 0xFF000000;
                    FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
                    int titleWidth = fontRenderer.getStringPropertyWidth(displayedTitle);
                    drawTextBackground(event.getMatrixStack(), -10, titleWidth, titleTextcolor | alpha);
                    if (showTextShadow)
                        fontRenderer.func_243246_a(event.getMatrixStack(), displayedTitle, (float) (-titleWidth / 2), titleYOffset, titleTextcolor | alpha);
                    else
                        fontRenderer.func_243248_b(event.getMatrixStack(), displayedTitle, (float) (-titleWidth / 2), titleYOffset, titleTextcolor | alpha);
                    RenderSystem.popMatrix();

                    // Subtitle render. Currently unused
                    if (displayedSubTitle != null) {
                        RenderSystem.pushMatrix();
                        RenderSystem.scalef(1.3F, 1.3F, 1.3F);
                        int subtitleWidth = fontRenderer.getStringPropertyWidth(displayedSubTitle);
                        drawTextBackground(event.getMatrixStack(), 5, subtitleWidth, 0xFFFFFF | alpha);
                        if (showTextShadow)
                            fontRenderer.func_243246_a(event.getMatrixStack(), displayedSubTitle, (float) (-subtitleWidth / 2), -35, 0xFFFFFF | alpha);
                        else
                            fontRenderer.func_243248_b(event.getMatrixStack(), displayedSubTitle, (float) (-subtitleWidth / 2), -35, 0xFFFFFF | alpha);
                        RenderSystem.popMatrix();
                    }

                    RenderSystem.disableBlend();
                    RenderSystem.popMatrix();
                }
            }
        }
    }

    public void tick() {
        if (titleTimer > 0) {
            --titleTimer;
            if (titleTimer <= 0) {
                reset();
            }
        }
        if (cooldownTimer > 0) {
            --cooldownTimer;
        }
    }

    public void displayTitle(ITextComponent titleText, @Nullable ITextComponent subtitleText) {
        displayedTitle = titleText;
        titleTimer = titleFadeInTicks + titleDisplayTime + titleFadeOutTicks;
        if (subtitleText != null)
            displayedSubTitle = subtitleText;
    }

    public void reset() {
        titleTimer = 0;
    }

    public void setColor(String textColor) {
        try {
            this.titleTextcolor = (int) Long.parseLong(textColor, 16);
        } catch (Exception e) {
            TravelersTitles.LOGGER.error("Text color {} is not a valid RGB color. Defaulting to white...", textColor);
            TravelersTitles.LOGGER.error(e.toString());
            this.titleTextcolor = 0xFFFFFF;
        }
    }

    public void addRecentEntry(T biome) {
        if (this.recentEntries.size() >= this.maxRecentListSize) {
            this.recentEntries.removeFirst();
        }
        recentEntries.addLast(biome);
    }

    public boolean containsEntry(Predicate<T> entryMatchPredicate) {
        return this.recentEntries.stream().anyMatch(entryMatchPredicate);
    }

    protected void drawTextBackground(MatrixStack mStack, int yOffset, int width, int color) {
        int textBackgroundColor = Minecraft.getInstance().gameSettings.getTextBackgroundColor(0.0F);
        if (textBackgroundColor != 0) {
            int xOffset = -width / 2;
            AbstractGui.fill(mStack, xOffset - 2, yOffset - 2, xOffset + width + 2, yOffset + 9 + 2, ColorHelper.PackedColor.blendColors(textBackgroundColor, color));
        }
    }
}
