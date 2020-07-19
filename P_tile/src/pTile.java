import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class pTile {
    public static void main(String[] args) {

        // 入力・出力ファイル名
        String inname = args[0];
        String outname = args[1];
        BufferedImage img = null;

        // 指定する白の割合
        int p = Integer.valueOf(args[2]);
        // 指定した割合に相当する画素数
        int t = 0;

        // 輝度値の頻度を保存する配列
        // 輝度値にそれぞれ対応：0~255
        int pix[] = new int[256];

        // 総画素数
        int max = 0;

        int sum = 0;

        // 画像の高さと幅
        int height, width;

        // RGB値
        int R;
        int G;
        int B;

        // 輝度値
        int P = 0;

        try {
            // 画像ファイルを読み込む
            img = ImageIO.read(new File(inname));

            // 画像の縦の長さを取得
            height = img.getHeight();
            // 画像の横の長さを取得
            width = img.getWidth();

            // 総画素数
            max = height * width;

            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {

                    // 画像の(j, i)地点のRGBを取得
                    Color color = new Color(img.getRGB(j, i));

                    // Rを取得
                    R = color.getRed();
                    // Gを取得
                    G = color.getGreen();
                    // Bを取得
                    B = color.getBlue();

                    // 画像をグレースケールに変換して、
                    // Gray = (Red + Green + Blue) / 3;
                    // その輝度値に対応する配列の値を+1する
                    pix[(R + G + B) / 3]++;
                }
            }
            // 輝度値の頻度を出力
            /*
             * for (int i = 0; i < 256; i++) System.out.println(pix[i]);
             */

            // 総画素数に対する、pの占める画素数
            // max * (double)(p/100)

            for (int i = 255; i >= 0; i--) {
                // 255から画素を足していく
                sum += pix[i];
                if (sum > (max * ((double) p / 100))) { // 定めた割合に達したら
                    // その値を保存して終了
                    t = i;
                    break;
                }
            }

            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    // 画像の(j, i)地点のRGBを取得
                    Color color = new Color(img.getRGB(j, i));

                    // Rを取得
                    R = color.getRed();
                    // Gを取得
                    G = color.getGreen();
                    // Bを取得
                    B = color.getBlue();

                    // RGBを変換して
                    // 画素値を一つに統一する
                    P = (R + G + B) / 3;

                    if (P >= t) {// tより高い画素値ならば、白
                        R = 255;
                        G = 255;
                        B = 255;
                    } else { // それ以外は黒
                        R = 0;
                        G = 0;
                        B = 0;
                    }
                    // R,G,Bを合成
                    int newcolor = (R << 16) + (G << 8) + B;
                    // 色を配置
                    img.setRGB(j, i, newcolor);
                }
            }

            boolean result;
            // imgをoutname(出力PNG)に保存
            result = ImageIO.write(img, "jpg", new File(outname));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}