package imgComparator.tools;

import org.opencv.core.Point;

import java.util.List;

/**
 * Created by Vadym on 10.01.2016.
 */
public class GeometryUtils {
  // �������� ������� �� ����� �������
  public static double getEuclideanDistance(Point p1, Point p2) {
    return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
  }

  // ����� ���������� �� 3 �������, ������� ������
  public static double getTriangleArea(Point a, Point b, Point c) {
    //ab, bc, ca - ������� ������ ������� ������� a,b,c
    double ab = getEuclideanDistance(a, b);
    double bc = getEuclideanDistance(b, c);
    double ca = getEuclideanDistance(c, a);
    double p = (ab + bc + ca) / 2;
    return Math.sqrt(p * (p - ab) * (p - bc) * (p - ca));
  }
}
