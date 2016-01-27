package imgComparator.distanceMethods;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

import java.util.List;

/**
 * Created by Oleh7 on 1/24/2016.
 */
public interface IComparator {

    boolean isGromovMode();
    double getDistance(List<Point> contour1, List<Point> contour2);
}
