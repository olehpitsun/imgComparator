package imgComparator.tools;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym on 10.01.2016.
 */
public class ImageOperations {

  private List<Point> _contourPoints;

  public Mat readImage(String imagePath) {
    return Imgcodecs.imread(imagePath);
  }

  public Mat convertToBinary(Mat img) {
    Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
    Mat imgBinary = new Mat(img.size(), CvType.CV_8UC1);
    Imgproc.threshold(img, imgBinary, 65, 255, Imgproc.THRESH_BINARY_INV);
    return imgBinary;
  }

  public List<MatOfPoint> findContours(Mat img) {
    // ����� �������
    List<MatOfPoint> contours = new ArrayList<>();
    // ����������� �������
    Imgproc.findContours(img, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
    return contours;
  }

  public List<Point> reduceContour(MatOfPoint contour, int pointsToLeave) {
    // ��������� �������
    List<Point> inputPointList = contour.toList();
    int numberToRemove = inputPointList.size() - pointsToLeave;
    ArrayList<Point> outputPointList = new ArrayList<>(inputPointList);

    // ���� ������� ����� ��� ��������� <= 0, �� ��������� ������ ������
    if(numberToRemove <= 0){
      return outputPointList;
    }

    // ���� ������� ����� � ����� ����� 3, ��������� ������ ������
    if(outputPointList.size() <= 3){
      return outputPointList;
    }

    // ��������� ������� ������� �����
    for(int i=0; i < numberToRemove; i++){

      int minIndex = 0;           // ����� ����� ��� ��� ����� ���������� ��������
      double minArea = GeometryUtils.getTriangleArea(outputPointList.get(0),
          outputPointList.get(1), outputPointList.get(2));

      // ������ ����� � ��������� ������
      // ���� ��������� �����, ������� ����� �������������� ����� ��� ����� ����� ������
      for(int j=2; j < outputPointList.size() - 1; j++){
        //��������� ����� ��� �� ��������� ����� � ������
        double area = GeometryUtils.getTriangleArea(outputPointList.get(j-1),
            outputPointList.get(j),outputPointList.get(j+1));

        if(area < minArea){
          minIndex = j;
          minArea = area;
        }
      }
      // ��������� ����� ��� ��� ����� ��������
      outputPointList.remove(minIndex);
    }
    return outputPointList;
  }

  public List<MatOfPoint> getReducedContours(Mat image, int pointsToLeave) {
    Mat bwImage = this.convertToBinary(image);

    List<MatOfPoint> contours = this.findContours(bwImage);

    List<MatOfPoint> reducedContours = new ArrayList<MatOfPoint>();
    int i=0;
    for(MatOfPoint contour : contours) {
      if(contour.size().height > 2) {
        List<Point> tempReducedContour = this.reduceContour(contour, pointsToLeave);
        MatOfPoint mop = new MatOfPoint();
        mop.fromList(tempReducedContour);
        reducedContours.add(i, mop);
        i++;
      }
    }

    return reducedContours;
  }

  public List<Point> convertMathOfPointToPoint(List<Point> contourToSet){
    this._contourPoints = contourToSet;
    return _contourPoints;
  }
}
