package imgComparator.distanceMethods.Frechet;

import imgComparator.tools.Contour;
import imgComparator.tools.GeometryUtils;
import imgComparator.tools.ImageOperations;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleh7 on 1/24/2016.
 */
public class Frechet {
    private double ca[][];
    private List<Point> contourA;
    private List<Point> contourB;
    private List<Point> _contourPointsA;
    private List<Point> _contourPointsB;

    private double c(int i, int j) {
        if(this.ca[i][j] > -1) {
            return ca[i][j];
        } else if(i == 0 && j == 0) {
            ca[i][j] = GeometryUtils.getEuclideanDistance(this.contourA.get(0), this.contourB.get(0));
        } else if(i > 0 && j == 0) {
            ca[i][j] = Math.max(this.c(i-1, 0), GeometryUtils.getEuclideanDistance(this.contourA.get(i), this.contourB.get(0)));
        } else if(i == 0 && j > 0) {
            ca[i][j] = Math.max(this.c(0, j-1), GeometryUtils.getEuclideanDistance(this.contourA.get(0), this.contourB.get(j)));
        } else if(i > 0 && j > 0) {
            double min = Math.min(this.c(i-1, j), this.c(i-1, j-1));
            min = Math.min(min, this.c(i, j-1));
            this.ca[i][j] = Math.max(min, GeometryUtils.getEuclideanDistance(this.contourA.get(i), this.contourB.get(j)));
        } else {
            ca[i][j] = 1;
        }
        return ca[i][j];
    }

    public double frechet(List<Point> contourA, List<Point> contourB) {
        int aSize = contourA.size();
        int bSize = contourB.size();
        this.contourA = contourA;
        this.contourB = contourB;

        this.ca = new double[aSize][bSize];

        // ���������� ����� -1
        for(int i=0; i<aSize; i++) {
            for(int j=0; j<bSize; j++) {
                this.ca[i][j] = -1;
            }
        }
        return this.c(aSize-1, bSize-1);
    }

    /**
     * метод визначення відстані для метолу Ферше
     * перетворення List<MatOfPoint> в List<Point>
     *
     * @param contour1
     * @param contour2
     * @return
     */
    public double getDistance(List<MatOfPoint> contour1, List<MatOfPoint> contour2) {
        Contour cp = new Contour();

        for (MatOfPoint contourA : contour1) {
            _contourPointsA = cp.convertMatOfPointToPoint(contourA.toList());
        }
        for (MatOfPoint contourB : contour2) {
            _contourPointsB = cp.convertMatOfPointToPoint(contourB.toList());
        }
        return this.frechet(_contourPointsA, _contourPointsB);
    }

    /**
     * метод, який визивається в GromovFerchet
     * @param contour1
     * @param contour2
     * @return
     */
    public double getFrechetDistance(List<Point> contour1, List<Point> contour2) {
        return this.frechet(contour1, contour2);
    }
}

