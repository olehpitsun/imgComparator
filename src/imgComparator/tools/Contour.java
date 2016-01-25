package imgComparator.tools;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym on 10.01.2016.
 */
public class Contour {
  private List<Point> _contourPoints;

  public void set(List<Point> contourToSet) {
    this._contourPoints = contourToSet;
  }

  public void set(int position, Point pointToSet) {
    this._contourPoints.set(position, pointToSet);
  }

  public List<Point> get() {
    return this._contourPoints;
  }

  public Point get(int i) {
    return this._contourPoints.get(i);
  }

  public List<Point> move(Point coordinatesOfNewCenter) {
    Point currentCenter = this.getContourCenter();

    double deltaX = currentCenter.x - coordinatesOfNewCenter.x;
    double deltaY = currentCenter.y - coordinatesOfNewCenter.y;

    for (int i = 0; i < this.get().size(); i++) {
      Point movedPoint = new Point();
      movedPoint.x = this.get(i).x - deltaX;
      movedPoint.y = this.get(i).y - deltaY;
      this.set(i, movedPoint);
    }

    return this.get();
  }

  public List<Point> rotate(double angle, Point center) {
    angle = Math.toRadians(angle);
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);

    for (int i = 0; i < this.get().size(); i++) {
      double subX = (this.get(i).x - center.x);
      double subY = (this.get(i).y - center.y);

      Point newPoint = new Point();
      newPoint.x = center.x + subX * cos - subY * sin;
      newPoint.y = center.y + subX * sin + subY * cos;
      this.set(i, newPoint);
    }

    return this.get();
  }

  public double getAngle(Point a, Point b) {
    // ������� �� ������ �� ����� �� ������
    double cb = GeometryUtils.getEuclideanDistance(a, b);

    // �������� ����� �� �� ������
    Point corner = new Point(b.x, a.y);

    // ������� �� ������ �� ����� �� �� ������
    double cd = GeometryUtils.getEuclideanDistance(a, corner);
    // ������� ����, ���� ��� ��������
    double cos = cd / cb;
    double angle = Math.toDegrees(Math.acos(cos));

    return a.y > b.y ? angle : -1 * angle;
  }

  public double getAngle() {
    double maxLength = 0;
    Point a = this.get(0);
    Point b = this.get(1);

    for (int z = 0; z < this.get().size(); z++) {
      for (int y = 0; y < this.get().size(); y++) {
        if (z != y) {
          double length = GeometryUtils.getEuclideanDistance(this.get(z), this.get(y));
          if (length > maxLength) {
            maxLength = length;
            a = this.get(z);
            b = this.get(y);
          }
        }
      }
    }

    return this.getAngle(a, b);
  }

  public Mat drawContour(Mat canvas, Scalar color) {
    List<MatOfPoint> listMatOfPoint = new ArrayList<>();
    listMatOfPoint.add(this._contourToMatOfPoint());

    Imgproc.drawContours(canvas, listMatOfPoint, 0, color, 1);
    return canvas;
  }

  public Mat drawContour(Mat canvas) {
    return this.drawContour(canvas, new Scalar(0, 255, 0));
  }

  public void drawContour(String title) {
    Rect boundingRect = this._getBoundingRect();
    Size imageSize = new Size(boundingRect.width * 2, boundingRect.height * 2);

    Mat canvas = new Mat(imageSize, CvType.CV_8UC3);

    this.drawContour(canvas);
    GUI.displayImage(canvas, title);
  }


  public Point getContourCenter() {
    Rect boundingRect = this._getBoundingRect();

    Point contourCenter = new Point();
    contourCenter.x = boundingRect.x + (boundingRect.width / 2);
    contourCenter.y = boundingRect.y + (boundingRect.height / 2);

    return contourCenter;
  }

  private Rect _getBoundingRect() {
    MatOfPoint contourMatOfPoint = this._contourToMatOfPoint();
    return Imgproc.boundingRect(contourMatOfPoint);
  }

  private MatOfPoint _contourToMatOfPoint() {
    MatOfPoint mop = new MatOfPoint();
    mop.fromList(this.get());
    return mop;
  }

  public List<Point> convertMatOfPointToPoint (List<Point> contourToSet ) {
    List<Point> contourPoint = contourToSet;
    return contourPoint;
  }

}
