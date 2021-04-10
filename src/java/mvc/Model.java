package mvc;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import lombok.Getter;

import java.util.Comparator;

@Getter
public class Model {

    private final ObservableList<Point> tablePoints;

    private final ObservableList<XYChart.Series<Double, Double>> chartSeries;

    public Model() {

        tablePoints = FXCollections.observableArrayList();
        chartSeries = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<Double, Double>> data = FXCollections.observableArrayList();
        for (double x = -100; x < 101; x++) {

            Point point = new Point(x / 10);
            tablePoints.add(point);

            data.add(new XYChart.Data<>(x / 10, point.getY()));
        }

        chartSeries.add(new XYChart.Series<>(data));

    }

    public int addPoint(double x) {
        Point point = new Point(x);

        tablePoints.add(point);
        tablePoints.sort(Comparator.comparing(Point::getX));

        chartSeries.get(0).getData().add(new XYChart.Data<>(x, point.getY()));

        int i = 0;
        for ( ; i < tablePoints.size(); i++) {
            if (tablePoints.get(i).getX() == x) {
                break;
            }
        }
        return i;
    }

    public int removePoint(int index) {
        tablePoints.remove(index);
        chartSeries.get(0).getData().remove(index);
        if (index == tablePoints.size() - 1) {
            return index - 1;
        }
        else {
            return index;
        }
    }

    public int editPoint(int index, double x) {
        removePoint(index);
        return addPoint(x);
    }

    public static class Point {

        private final SimpleObjectProperty<XYChart.Data<Double, Double>> point;

        protected Point(double x) {
            this.point = new SimpleObjectProperty<>(
                    new XYChart.Data<>(x, mathFunction(x))
            );
        }

        public Double getX() {
            return point.get().getXValue();
        }

        public Double getY() {
            return point.get().getYValue();
        }

        private double mathFunction(double x) {
            return Math.pow(x, 2);
        }
    }
}
