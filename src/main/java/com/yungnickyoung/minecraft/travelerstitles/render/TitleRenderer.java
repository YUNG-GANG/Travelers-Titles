package com.yungnickyoung.minecraft.travelerstitles.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.util.LinkedList;
import java.util.function.Predicate;

public class TitleRenderer<T> {
    public final LinkedList<T> recentEntries = new LinkedList<>();
    public Component displayedTitle = null;
    public Component displayedSubTitle = null;
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
    public float titleXOffset;
    public float titleYOffset;
    public boolean isTextCentered;

    public TitleRenderer() {
    }

    public TitleRenderer(
        int maxRecentListSize,
        boolean enabled,
        int fadeInTicks,
        int displayTicks,
        int fadeOutTicks,
        String textColor,
        boolean showTextShadow,
        double textSize,
        double xOffset,
        double yOffset,
        boolean centerText
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
        this.titleXOffset = (float)xOffset;
        this.titleYOffset = (float)yOffset;
        this.isTextCentered = centerText;
    }

    public void renderText(float partialTicks, PoseStack poseStack) {
        if (displayedTitle != null && titleTimer > 0) {
            float age = (float) titleTimer - partialTicks;
            int opacity = 255;
            if (titleTimer > titleFadeOutTicks + titleDisplayTime) {
                float r = (float) (titleFadeInTicks + titleDisplayTime + titleFadeOutTicks) - age;
                opacity = (int) (r * 255.0F / (float) titleFadeInTicks);
            }

            if (titleTimer <= titleFadeOutTicks) {
                opacity = (int) (age * 255.0F / (float) titleFadeOutTicks);
            }

            opacity = Mth.clamp(opacity, 0, 255);
            if (opacity > 8) {
                // Set up render system
                poseStack.pushPose();
                if (this.isTextCentered)
                    poseStack.translate(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2D, (Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2D), 0);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                // Render title
                poseStack.pushPose();
                poseStack.scale(titleTextSize, titleTextSize, titleTextSize);
                int alpha = opacity << 24 & 0xFF000000;
                Font fontRenderer = Minecraft.getInstance().font;
                int titleWidth = fontRenderer.width(displayedTitle);

                // Currently does nothing?
                drawTextBackground(poseStack, -10, titleWidth, titleTextcolor | alpha);

                // Determine x offset
                float xOffset = this.isTextCentered
                    ? this.titleXOffset + (float) (-titleWidth / 2)
                    : this.titleXOffset;

                if (showTextShadow)
                    fontRenderer.drawShadow(poseStack, displayedTitle, xOffset, titleYOffset, titleTextcolor | alpha);
                else
                    fontRenderer.draw(poseStack, displayedTitle, xOffset, titleYOffset, titleTextcolor | alpha);
                poseStack.popPose();

                // Subtitle render. Currently unused
                if (displayedSubTitle != null) {
                    poseStack.pushPose();
                    poseStack.scale(1.3F, 1.3F, 1.3F);
                    int subtitleWidth = fontRenderer.width(displayedSubTitle);
                    drawTextBackground(poseStack, 5, subtitleWidth, 0xFFFFFF | alpha);
                    if (showTextShadow)
                        fontRenderer.drawShadow(poseStack, displayedSubTitle, (float) (-subtitleWidth / 2), -35, 0xFFFFFF | alpha);
                    else
                        fontRenderer.draw(poseStack, displayedSubTitle, (float) (-subtitleWidth / 2), -35, 0xFFFFFF | alpha);
                    poseStack.popPose();
                }

                RenderSystem.disableBlend();
                poseStack.popPose();
            }
        }
    }

    public void tick() {
        if (titleTimer > 0) {
            --titleTimer;
            if (titleTimer <= 0) {
                clearTimer();
            }
        }
        if (cooldownTimer > 0) {
            --cooldownTimer;
        }
    }

    public void displayTitle(Component titleText, Component subtitleText) {
        displayedTitle = titleText;
        titleTimer = titleFadeInTicks + titleDisplayTime + titleFadeOutTicks;
        if (subtitleText != null)
            displayedSubTitle = subtitleText;
    }

    public void clearTimer() {
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

    public void addRecentEntry(T entry) {
        if (this.recentEntries.size() >= this.maxRecentListSize && this.recentEntries.size() > 0) {
            this.recentEntries.removeFirst();
        }
        if (this.maxRecentListSize > 0) {
            recentEntries.addLast(entry);
        }
    }

    public boolean matchesAnyRecentEntry(Predicate<T> entryMatchPredicate) {
        return this.recentEntries.stream().anyMatch(entryMatchPredicate);
    }

    protected void drawTextBackground(PoseStack mStack, int yOffset, int width, int color) {
        int textBackgroundColor = Minecraft.getInstance().options.getBackgroundColor(0.0F);
        if (textBackgroundColor != 0) {
            int xOffset = -width / 2;
            GuiComponent.fill(mStack, xOffset - 2, yOffset - 2, xOffset + width + 2, yOffset + 9 + 2, FastColor.ARGB32.multiply(textBackgroundColor, color));
        }
    }
}
