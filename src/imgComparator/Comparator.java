package imgComparator;

import imgComparator.distanceMethods.Frechet.Frechet;
import imgComparator.distanceMethods.GromovFrechet.GromovFrechet;
import imgComparator.distanceMethods.IComparator;
import imgComparator.tools.Contour;
import imgComparator.tools.ImageOperations;
import org.opencv.core.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym on 10.01.2016.
 */
public class Comparator {
    protected int distancesCount = 2;

    public double compare(String image1, String image2) {
        ImageOperations imageOperations = new ImageOperations();

        Mat imgA = imageOperations.readImage(image1);
        Mat imgB = imageOperations.readImage(image2);

        List<MatOfPoint> contoursA = imageOperations.getReducedContours(imgA, 30);
        List<MatOfPoint> contoursB = imageOperations.getReducedContours(imgB, 30);

        return execute(contoursA, contoursB);
    }

    protected double execute(List<MatOfPoint> contoursA, List<MatOfPoint> contoursB) {
        double[] results = new double[this.distancesCount];
        double[] distancesRanks = new double[this.distancesCount];
        distancesRanks[0] = 0.5;
        distancesRanks[1] = 0.5;

        boolean firstIteration = true;

        IComparator[] distancesToFind = new IComparator[this.distancesCount];
        distancesToFind[0] = new Frechet();
        distancesToFind[1] = new GromovFrechet();

        for (MatOfPoint contourA : contoursA) {
            double[] iterationResults = new double[this.distancesCount];

            Contour cA = new Contour();
            cA.set(contourA.toList());
            Point center = cA.getContourCenter();

            cA.rotate(cA.getAngle(), center);

            for (MatOfPoint contourB : contoursB) {
                Contour cB = new Contour();
                cB.set(contourB.toList());
                cB.move(cA.getContourCenter());

                double angle = cB.getAngle();
                cB.rotate(angle, center);

                for (int i=0; i<this.distancesCount; i++) {
                    double distanceResult;

                    if (distancesToFind[i].isGromovMode()) {
                        distanceResult = distancesToFind[i].getDistance(cA.get(), cB.get());
                    } else {
                        distanceResult = distancesToFind[i].getDistance(contourA.toList(), contourB.toList());
                    }

                    iterationResults[i] = firstIteration ? distanceResult : Math.min(iterationResults[i], distanceResult);
                }
                firstIteration = false;
            }

            for (int i=0; i<this.distancesCount; i++) {
                results[i] = Math.max(results[i], iterationResults[i]);
            }
        }

        double result = 0;
        for (int i=0; i<this.distancesCount; i++) {
            result += distancesRanks[i] * results[i];
        }

        return result;
    }
}

