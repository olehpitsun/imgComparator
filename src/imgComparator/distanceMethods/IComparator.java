package imgComparator.distanceMethods;

import org.opencv.core.MatOfPoint;

import java.util.List;

/**
 * Created by Oleh7 on 1/24/2016.
 */
public interface IComparator {

    double getDistance(List<MatOfPoint> contour1, List<MatOfPoint> contour2);
}
