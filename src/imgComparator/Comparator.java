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

    public ImageOperations imageOperations;

    public double compare(String image1, String image2) {
        this.imageOperations = new ImageOperations();

        Mat imgA = this.imageOperations.readImage(image1);
        Mat imgB = this.imageOperations.readImage(image2);

        List<MatOfPoint> contoursA = getReducedContours(imgA,30);
        List<MatOfPoint> contoursB = getReducedContours(imgB,30);

        Frechet fr = new Frechet();
        double FRresult = fr.getDistance(contoursA, contoursB);
        System.out.println("Ферше: " + FRresult);

        GromovFrechet gromovFrechet = new GromovFrechet();
        double GRFRresult = gromovFrechet.getDistance(contoursA, contoursB);
        System.out.println("Громов - Ферше: " + GRFRresult);

        return 0;
    }

    /**
     * Можливо пригодиться і прийдеться щось додумати, бо в Інших немає параметру pointsToLeave
     * @param img
     * @param pointsToLeave
     * @return
     */
    private List<MatOfPoint> getReducedContours(Mat img, int pointsToLeave){

        List<MatOfPoint> contour = this.imageOperations.getReducedContours(img, pointsToLeave);
        return contour;
    }

    /*
    public double asd(List<MatOfPoint> contour1, List<MatOfPoint> contour2, List<IComparator> icontour) {
        boolean firstIteration = true;
        Frechet frechet = new Frechet();
        double result = 0.0;

        List<Double> res = new ArrayList<>();

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

                    for (int k=0;k<icontour.size(); k++) {
                        res[k] = icontour.get(k).getDistance(cA.get(), cB.get());
                    }

                    double tempResult = frechet.getDistance(cA.get(), cB.get());

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
    */
}
