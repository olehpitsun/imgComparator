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

        Comparator comparator = new Comparator();
        double result = comparator.compare("src\\images\\image1.png", "src\\images\\image1.png");
        System.out.println("RESULT = " + result);
    }
}
