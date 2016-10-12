package com.guigarage.validation.util;

import com.guigarage.validation.model.FormModel;
import com.guigarage.validation.model.Gender;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import javax.validation.ConstraintViolation;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by hendrikebbers on 12.10.16.
 */
public abstract class AbstractFormController implements Initializable {

    private final static PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private ChoiceBox<Gender> genderChoiceBox;

    @FXML
    private Label labelForFirstNameTextField;

    @FXML
    private Label LabelForLastNameTextField;

    @FXML
    private Label LabelForActiveCheckBox;

    @FXML
    private Label LabelForGenderChoiceBox;

    @FXML
    private Button validateButton;

    private final FormModel model = new FormModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelForFirstNameTextField.setLabelFor(firstNameTextField);
        LabelForLastNameTextField.setLabelFor(lastNameTextField);
        LabelForActiveCheckBox.setLabelFor(activeCheckBox);
        LabelForGenderChoiceBox.setLabelFor(genderChoiceBox);

        validateButton.setOnAction(e -> validate());
        genderChoiceBox.getItems().addAll(Gender.MALE, Gender.FEMALE);
    }

    protected abstract void validate();

    protected <T> void updateForValidation(Control node, Set<ConstraintViolation<T>> violations) {
        if (violations == null || violations.isEmpty()) {
            markValid(node);
        } else {
            markInvalid(node, violations);
        }
    }

    private Label findLabelFor(Node node) {
        return node.getParent().getChildrenUnmodifiable().stream().filter(c -> {
            if (c instanceof Label) {
                Label label = (Label) c;
                if (label.getLabelFor() != null && label.getLabelFor().equals(node)) {
                    return true;
                }
            }
            return false;
        }).findAny().map(n -> (Label) n).orElse(null);
    }

    private void markValid(Control node) {
        Label label = findLabelFor(node);
        label.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
        node.setTooltip(null);
    }

    private <T> void markInvalid(Control node, Set<ConstraintViolation<T>> violations) {
        Label label = findLabelFor(node);
        label.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);

        String tooltipText = violations.stream().map(v -> v.getMessage()).reduce(null, (v1, v2) -> {
            if(v1 == null || v1.isEmpty()) {
                return v2;
            }
            if(v2 == null || v2.isEmpty()) {
                return v1;
            }
            return v1 + System.lineSeparator() + v2;
        });
        label.setTooltip(new Tooltip(tooltipText));
        node.setTooltip(new Tooltip(tooltipText));
    }

    public TextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public TextField getLastNameTextField() {
        return lastNameTextField;
    }

    public CheckBox getActiveCheckBox() {
        return activeCheckBox;
    }

    public ChoiceBox<?> getGenderChoiceBox() {
        return genderChoiceBox;
    }

    protected FormModel updateModelByUI() {
        model.setFirstName(firstNameTextField.getText());
        model.setLastName(lastNameTextField.getText());
        model.setActive(activeCheckBox.isSelected());
        model.setGender(genderChoiceBox.getValue());
        return model;
    }
}

