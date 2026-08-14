package com.takoy3466.manaitapp.event;

import com.takoy3466.manaitapp.Manaitapp;
import com.takoy3466.manaitapp.core.registry.register.KeyMappingRegister;
import com.takoy3466.manaitapp.dataComponent.MTKData;
import com.takoy3466.manaitapp.init.DataInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = Manaitapp.MOD_ID, value = Dist.CLIENT)
public class ClientModEventBus {

    @SubscribeEvent
    public static void keyRegister(RegisterKeyMappingsEvent event) {
        KeyMappingRegister.entries.forEach(event::register);
    }

    @SubscribeEvent
    public static void onRenderGuiEvent(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }
        MTKData mtkData = player.getMainHandItem().get(DataInit.MTK_DATA);
        if (mtkData == null) {
            return;
        }


    }

    @SubscribeEvent
    public static void onRenderToolTipEvent(RenderTooltipEvent.Color event) {
        // バニラ標準の枠線（外枠・内枠）を透明にして描画させない
        event.setBorderStart(0x00000000);
        event.setBorderEnd(0x00000000);

    }

    @SubscribeEvent
    public static void onRenderToolTipEvent(RenderTooltipEvent.Pre event) {
        // ツールチップの全体サイズを取得
        int x = event.getX();
        int y = event.getY();
        int width = event.getScreenWidth();
        int height = event.getScreenHeight();

        // 枠線の座標を計算（バニラの外枠と同じ位置、文字の周り1マス外側）
        int left = x - 4;
        int right = x + width + 4;
        int top = y - 4;
        int bottom = y + height + 4;

        GuiGraphics graphics = event.getGraphics();

        // アニメーションの速度調整（数値が大きいほどゆっくり変化）
        float speed = 3000.0f;
        float time = (System.currentTimeMillis() % (int)speed) / speed;

        // 【ここが肝】四隅の色を「時計回り」に少しずつずらす
        // 1.0 = 虹一周 分。0.1〜0.25 ずつずらすことで、色が繋がって流れるようになります
        float offset = 0.20f;
        int colorTopLeft     = getRainbowColor(time);
        int colorTopRight    = getRainbowColor(time + offset);
        int colorBottomRight = getRainbowColor(time + (offset * 2));
        int colorBottomLeft  = getRainbowColor(time + (offset * 3));

        // --- 四辺の描画 (外枠) ---
        // 1. 上辺 (左上 から 右上 へ水平グラデーション)
        graphics.fillGradient(left, top, right, top + 1, colorTopLeft, colorTopRight);

        // 2. 右辺 (右上 から 右下 へ垂直グラデーション)
        graphics.fillGradient(right - 1, top + 1, right, bottom - 1, colorTopRight, colorBottomRight);

        // 3. 下辺 (左下 から 右下 へ水平グラデーション) ※逆向きに繋げる
        graphics.fillGradient(left, bottom - 1, right, bottom, colorBottomLeft, colorBottomRight);

        // 4. 左辺 (左上 から 左下 へ垂直グラデーション)
        graphics.fillGradient(left, top + 1, left + 1, bottom - 1, colorTopLeft, colorBottomLeft);


        // --- (任意) 内枠の描画 ---
        // もしバニラのように2重枠にしたい場合は、1マス内側（left+1, right-1等）を
        // 少し暗めの虹色（明度や彩度を落としたもの）で同様に描画してください。

    }

    private static int getRainbowColor(float hueOffset) {
        float hue = hueOffset % 1.0f;
        int rgb = Mth.hsvToRgb(hue, 1.0f, 1.0f);
        return rgb | 0xFF000000;
    }
}
