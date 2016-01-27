package imgComparator.distanceMethods.GromovFrechet;

import imgComparator.distanceMethods.Frechet.Frechet;
import imgComparator.distanceMethods.IComparator;
import imgComparator.tools.Contour;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

import java.util.List;

/**
 * Created by Vadym on 10.01.2016.
 */
public class GromovFrechet implements IComparator {
    public boolean isGromovMode() {
        return true;
    }

    public double getDistance(List<Point> contour1, List<Point> contour2) {
        IComparator frechet = new Frechet();
        return frechet.getDistance(contour1, contour2);
    }
}
