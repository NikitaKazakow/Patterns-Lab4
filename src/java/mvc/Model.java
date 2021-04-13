package mvc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import lombok.Getter;

import java.util.Comparator;

@Getter
public class Model {

    private final ObservableList<XYChart.Data<Double, Double>> points;

    public Model() {

        points = FXCollections.observableArrayList();

        for (double x = -100; x < 101; x++) {
            points.add(new XYChart.Data<>(x / 10, mathFunction(x / 10)));
        }
    }

    private double mathFunction(double x) {
        return Math.pow(x, 2) + 7 * x - 3;
    }

    //region Data manipulation methods
    public int addPoint(double x) {
        points.add(new XYChart.Data<>(x, mathFunction(x)));
        points.sort(Comparator.comparing(XYChart.Data::getXValue));

        int i = 0;
        for ( ; i < points.size(); i++) {
            if (points.get(i).getXValue() == x) {
                break;
            }
        }
        return i;
    }

    public int removePoint(int index) {
        points.remove(index);
        if (index == points.size() - 1) {
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
    //endregion
}
