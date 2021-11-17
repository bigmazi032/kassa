package ru.zinyakova.kassa.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DigitTextField extends TextField {
    public DigitTextField () {
        initSpellListener();
    }
    public final void initSpellListener() {
        this.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.setText(newValue.replaceAll("[^\\d]", ""));/*The comma here "[^\\d,]" can be changed with the dot*/
// ниже код, если нужно ограничить ввод десятичной дробью
//
//                StringBuilder aus = new StringBuilder();
//                aus.append(this.getText());
//                boolean firstPointFound = false;
//
//                for (int i = 0; i < aus.length(); i++) {
//                    if (aus.charAt(i) == ',') {/*Change the , with . if you want the . to be the decimal separator*/
//                        if (!firstPointFound) {
//                            firstPointFound = true;
//                        } else {
//                            aus.deleteCharAt(i);
//                        }
//                    }
//                }
//                newValue = aus.toString();
//                this.setText(newValue);
            } else {
                this.setText(newValue);
            }
        });
    }
}

