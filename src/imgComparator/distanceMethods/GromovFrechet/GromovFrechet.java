package imgComparator.distanceMethods.GromovFrechet;

import imgComparator.distanceMethods.Frechet.Frechet;
import imgComparator.distanceMethods.IComparator;
import imgComparator.tools.Contour;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

import java.util.List;

/**
 * Created by Oleh7 on 1/24/2016.
 */
public class GromovFrechet implements IComparator {

    public double getDistance(List<MatOfPoint> contour1, List<MatOfPoint> contour2) {
        boolean firstIteration = true;
        Frechet frechet = new Frechet();
        double result = 0.0;

        for (MatOfPoint contourA : contour1) {
            double iterationResult = 0.0;

            Contour cA = new Contour();
            cA.set(contourA.toList());
            Point center = cA.getContourCenter();

            cA.rotate(cA.getAngle(), center);

            for (MatOfPoint contourB : contour2) {
                Contour cB = new Contour();
                cB.set(contourB.toList());
                cB.move(cA.getContourCenter());

                for (int i = 0; i < cB.get().size(); i++) {
                    double localAngle = cB.getAngle(cB.get(i), center);
                    cB.rotate(localAngle, center);
                    double tempResult = frechet.getFrechetDistance(cA.get(), cB.get());

                    if (firstIteration || !firstIteration && tempResult < iterationResult) {
                        iterationResult = tempResult;
                        firstIteration = false;
                    }

                    cB.rotate(-localAngle, center);
                }
            }
            result = Math.max(result, iterationResult);
        }

        return result;
    }
}
