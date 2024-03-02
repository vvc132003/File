package dataset;

import data.model.Shape;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShapeManager implements IShapeManager {
    private List<Shape> shapeList = new ArrayList<>();

    @Override
    public void add(Shape shape) {
        shapeList.add(shape);
    }

    @Override
    public void remove(Shape shape) {
        shapeList.remove(shape);
    }

    @Override
    public void sort() {
        shapeList.sort(new Comparator<Shape>() {
            @Override
            public int compare(Shape o1, Shape o2) {
                return Double.compare(o1.chuVi(), o2.chuVi());
            }
        });
    }

    @Override
    public Shape findMax() {
        if (shapeList.isEmpty()) {
            return null;
        }

        Shape maxShape = shapeList.get(0);
        double maxArea = maxShape.dienTich();

        for (Shape shape : shapeList) {
            double area = shape.dienTich();
            if (area > maxArea) {
                maxArea = area;
                maxShape = shape;
            }
        }

        return maxShape;
    }
}
