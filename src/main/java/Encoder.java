import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eric on 8/25/2015.
 */
public class Encoder {

    public static File encode(File inFile, String message, String outFileName){
        BufferedImage img = null;
        try{
            img = ImageIO.read(inFile);
        }catch(IOException ioe){
            System.out.println("error: can't read file");
        }
        ColorModel cm = img.getColorModel();
        BufferedImage newImg = new BufferedImage(cm, img.getRaster(), cm.isAlphaPremultiplied(), null);

        byte[] imageBytes = ((DataBufferByte) newImg.getRaster().getDataBuffer()).getData();
        byte[] messageBytes = message.getBytes();
        for(int i = 0; i < messageBytes.length; i++){
            imageBytes[i*4] = (byte)((imageBytes[i*4] & 0xFC) | (messageBytes[i] >> 6 & 0x3));
            imageBytes[i*4+1] = (byte)((imageBytes[i*4+1] & 0xFC) | (messageBytes[i] >> 4 & 0x3));
            imageBytes[i*4+2] = (byte)((imageBytes[i*4+2] & 0xFC) | (messageBytes[i] >> 2 & 0x3));
            imageBytes[i*4+3] = (byte)((imageBytes[i*4+3] & 0xFC) | (messageBytes[i] & 0x3));
        }
        File outFile = new File(outFileName);
        try {
            ImageIO.write(img, outFileName.substring(outFileName.indexOf(".")+1), outFile);
        } catch (IOException e) {
            System.out.println("lol");
        }
        return outFile;
    }

    public static String decode(File file){
        BufferedImage img = null;
        try{
            img = ImageIO.read(file);
        }catch(IOException ioe){
            System.out.println("error: can't read file");
        }
        byte[] imageBytes = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        byte[] extract = new byte[imageBytes.length/4];
        byte curByte = 0x00;
        int i = 0;
        do{
            curByte = (byte)(((imageBytes[i++] & 0x3) << 6) | ((imageBytes[i++] & 0x3) << 4) | ((imageBytes[i++] & 0x3) << 2) | (imageBytes[i] & 0x3)) ;
            extract[i/4] = (curByte);
            i++;
        }while(curByte != 0x00 && i < imageBytes.length);
        byte[] smallerArray = new byte[(i/4)-1];
        for(int j = 0; j < smallerArray.length; j++) smallerArray[j] = extract[j];
        return new String(smallerArray);
    }

}
