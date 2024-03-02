package dataset;
import data.model.Shape;

public interface IShapeManager {
    void add(Shape shape);
    void remove(Shape shape);
    void sort();
    Shape findMax();
}
