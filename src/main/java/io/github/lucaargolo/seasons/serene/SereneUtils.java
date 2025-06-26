package io.github.lucaargolo.seasons.serene;

public class SereneUtils {
    public static int multiplyColours(int colour1, int colour2) {
        //Convert each colour to a scale between 0 and 1 and multiply them
        //Multiply by 255 to bring back between 0 and 255
        return (int) ((colour1 / 255.0F) * (colour2 / 255.0F) * 255.0F);
    }

    public static int overlayBlendChannel(int underColour, int overColour) {
        int retVal;
        if (underColour < 128) {
            retVal = multiplyColours(2 * underColour, overColour);
        } else {
            retVal = multiplyColours(2 * (255 - underColour), 255 - overColour);
            retVal = 255 - retVal;
        }
        return retVal;
    }

    public static int overlayBlend(int underColour, int overColour) {
        int r = overlayBlendChannel((underColour >> 16) & 255, (overColour >> 16) & 255);
        int g = overlayBlendChannel((underColour >> 8) & 255, (overColour >> 8) & 255);
        int b = overlayBlendChannel(underColour & 255, overColour & 255);

        return (r & 255) << 16 | (g & 255) << 8 | (b & 255);
    }

    public static int mixColours(int a, int b, float ratio) {
        if (ratio > 1f) {
            ratio = 1f;
        } else if (ratio < 0f) {
            ratio = 0f;
        }
        float iRatio = 1.0f - ratio;

        int aA = (a >> 24 & 0xff);
        int aR = ((a & 0xff0000) >> 16);
        int aG = ((a & 0xff00) >> 8);
        int aB = (a & 0xff);

        int bA = (b >> 24 & 0xff);
        int bR = ((b & 0xff0000) >> 16);
        int bG = ((b & 0xff00) >> 8);
        int bB = (b & 0xff);

        int A = (int) ((aA * iRatio) + (bA * ratio));
        int R = (int) ((aR * iRatio) + (bR * ratio));
        int G = (int) ((aG * iRatio) + (bG * ratio));
        int B = (int) ((aB * iRatio) + (bB * ratio));

        return A << 24 | R << 16 | G << 8 | B;
    }

    public static int saturateColour(int colour, float saturationMultiplier) {
        SereneColor newColor = new SereneColor(colour);
        double[] hsv = newColor.toHSV();
        hsv[1] *= saturationMultiplier;
        newColor = SereneColor.convertHSVtoRGB(hsv[0], hsv[1], hsv[2]);
        return newColor.toInt();
    }
}
