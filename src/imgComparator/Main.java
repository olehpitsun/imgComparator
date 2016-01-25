package imgComparator;

import imgComparator.distanceMethods.GromovFrechet.GromovFrechet;

import imgComparator.tools.ImageOperations;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import imgComparator.Comparator;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Oleh7 on 1/24/2016.
 */
public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        String image1 = "C:\\Projects\\images-comparator-master\\images-comparator\\images\\image1.png";
        String image2 = "C:\\Projects\\images-comparator-master\\images-comparator\\images\\image2.png";

        Comparator comparator = new Comparator();
        comparator.compare(image1, image2);
    }
}
