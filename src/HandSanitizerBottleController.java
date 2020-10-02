import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class HandSanitizerBottleController implements Initializable {

    @FXML
    private TextField priceTextField;

    @FXML
    private DatePicker expiryDatePicker;

    @FXML
    private ComboBox<String> brandComboBox;

    @FXML
    private Spinner<Integer> capacitySpinner;

    @FXML
    private Label msgLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brandComboBox.getItems().addAll("Utsav's Magic Elixer","Lindsay Speed Cleaner","Michaels Lucky Guesses");

        //configure the Spinner to handle Integer objects in a valid range
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 33);
        capacitySpinner.setValueFactory(valueFactory);
        capacitySpinner.setEditable(true);
        TextField spinnerEditor = capacitySpinner.getEditor();

//        SpinnerChangeListener scl = new SpinnerChangeListener();
//        spinnerEditor.textProperty().addListener(scl);

        spinnerEditor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                        String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                    msgLabel.setText("");
                } catch (NumberFormatException e)
                {
                    spinnerEditor.setText(oldValue);
                    msgLabel.setTextFill(Color.RED);
                    msgLabel.setText("Only whole numbers are allowed");

                }
            }
        });
    }


    public void createButtonPushed()
    {
        System.out.printf("The Expiry Date is: %s ", expiryDatePicker.getValue());
        System.out.printf("The TextField said: '%s'%n", priceTextField.getText());
        System.out.printf("The ComboBox said: '%s'%n", brandComboBox.getValue());
    }


}