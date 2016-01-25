package imgComparator.tools;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;

public class GUI {

    static final int PADDING = 50;

    public static void displayImage(Mat img)
    {
        displayImage(img, "", false);
    }
    public static void displayImage(Mat img, String title)
    {
        displayImage(img, title, false);
    }

    //Виводить зображення на екран
    public static void displayImage(Mat matImg, String title, boolean scaleToScreenSize){
        Image img = ImageConverter.Mat2BufferedImage(matImg);

        if(scaleToScreenSize){
            img = scaleImage2ScreenSize(img);
        }

        ImageIcon icon=new ImageIcon(img);
        JFrame frame = new JFrame();
        frame.setSize(img.getWidth(null)+50, img.getHeight(null)+50);
        JScrollPane jsp = new JScrollPane(new JLabel(icon));
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(jsp);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(title);
    }

    // зменшує зображення до розмірів екрану
    private static Image scaleImage2ScreenSize(Image img){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        double wRatio = img.getWidth(null) / width;
        double hRatio = img.getHeight(null) / height;

        if(wRatio > 1 || hRatio > 1){
            double ratio = wRatio > hRatio ? wRatio : hRatio;
            // зменшуємо розміри зображення зберігаючи співвідношення
            img = img.getScaledInstance((int) (img.getWidth(null)/ratio), (int) (img.getHeight(null)/ratio), Image.SCALE_DEFAULT);
        }

        return img;
    }
}
