import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SpinnerChangeListener implements ChangeListener {
    @Override
    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
        System.out.printf("Old Value: %s   New Value: %s %n", oldValue, newValue);
    }
}
