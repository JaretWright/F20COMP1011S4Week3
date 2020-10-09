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

    @FXML
    private Slider alcoholSlider;

    @FXML
    private TextField alcoholTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgLabel.setText("");

        //configure the combobox
        brandComboBox.setPromptText("Select a Brand");
        brandComboBox.getItems().addAll("Utsav's Elixer", "Bikers' Clean Hand", "Coffee Scent");

        //configure the Spinner to handle Integer objects in a valid range
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 33);
        capacitySpinner.setValueFactory(valueFactory);
        capacitySpinner.setEditable(true);
        TextField spinnerEditor = capacitySpinner.getEditor();

//        SpinnerChangeListener scl = new SpinnerChangeListener();
//        spinnerEditor.textProperty().addListener(scl);

//        spinnerEditor.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue,
//                                        String oldValue, String newValue) {
//                try {
//                    Integer.parseInt(newValue);
//                    msgLabel.setText("");
//                } catch (NumberFormatException e)
//                {
//                    spinnerEditor.setText(oldValue);
//                    msgLabel.setTextFill(Color.RED);
//                    msgLabel.setText("Only whole numbers are allowed");
//                }
//            }
//        });
        spinnerEditor.textProperty().addListener((observableValue, oldValue, newValue)->
            {
                try {
                    Integer.parseInt(newValue);
                    msgLabel.setText("");
                } catch (NumberFormatException e)
                {
                    spinnerEditor.setText(oldValue);
                    msgLabel.setTextFill(Color.RED);
                    msgLabel.setText("Only whole numbers are allowed");
                }
            });

        //configure the Slider
        alcoholSlider.setMin(70);
        alcoholSlider.setMax(100);
        alcoholSlider.setValue(85);
        alcoholTextField.setText(String.format("%.1f",alcoholSlider.getValue()));

        alcoholSlider.valueProperty().addListener((observableValue, oldValue, newValue)->
        {
            alcoholTextField.setText(String.format("%.1f",alcoholSlider.getValue()));
        });

        expiryDatePicker.setPromptText("Select the expiry date");
    }

    @FXML
    private void alcoholTextFieldUpdated()
    {
        //strip out any unwanted characters
        String newValue = alcoholTextField.getText().replaceAll("[^0-9.]","");
        try {
            double alcohol = Double.parseDouble(newValue);
            alcoholSlider.setValue(alcohol);
        }catch (Exception e){};
        alcoholTextField.setText(String.format("%.1f",alcoholSlider.getValue()));
    }


    public void createButtonPushed() {
        if (allFieldsHaveValues()) {
            try {
                System.out.println("Before: "+priceTextField.getText());
                String priceString = priceTextField.getText().replaceAll("[^0-9.]","");
                System.out.println("After: "+priceString);

                HandSanitizerBottle newBottle = new HandSanitizerBottle(
                        brandComboBox.getValue(),
                        capacitySpinner.getValue(),
                        alcoholSlider.getValue(),
                        expiryDatePicker.getValue(),
                        Double.parseDouble(priceString));
                msgLabel.setText(newBottle.toString());
            } catch (Exception e) {
                msgLabel.setText(e.getMessage());
            }
        }
    }

    public boolean allFieldsHaveValues()
    {
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append("Please update the following field(s): ");
        if (brandComboBox.getValue() == null)
            errorMsg.append("Brand");
        if (expiryDatePicker.getValue() == null)
            errorMsg.append(errorMsg.length()>38?", expiry date":"expiry date");
        if (priceTextField.getText().isBlank())
            errorMsg.append(errorMsg.length()>38?", price":"price");

        if (errorMsg.length()>38)
        {
            msgLabel.setTextFill(Color.RED);
            msgLabel.setText(errorMsg.toString());
            return false;
        }
        return true;
    }

}