package imgComparator;

import imgComparator.distanceMethods.Frechet.Frechet;
import imgComparator.distanceMethods.GromovFrechet.GromovFrechet;
import org.opencv.core.Core;

/**
 * Created by Oleh7 on 1/24/2016.
 */
public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        Comparator comparator = new Comparator();

        comparator.add(new Frechet(), 0.5);
        comparator.add(new GromovFrechet(), 0.5);
        double result = comparator.compare("src\\images\\image1.png", "src\\images\\image2.png");

        System.out.println("RESULT = " + result);
    }
}
