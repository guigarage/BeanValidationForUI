package com.guigarage.validation;

import com.guigarage.validation.model.FormModel;

import javax.validation.BeanValidator;
import javax.validation.BeanValidatorProvider;
import javax.validation.ConstraintViolation;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by hendrikebbers on 12.10.16.
 */
public class FormController1 extends AbstractFormController {

    private BeanValidator<FormModel> validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        validator = BeanValidatorProvider.build(FormModel.class).
                withProperty("firstName", () -> getFirstNameTextField().getText(), v -> updateForValidation(getFirstNameTextField(), v)).
                withProperty("lastName", () -> getLastNameTextField().getText(), v -> updateForValidation(getLastNameTextField(), v)).
                withProperty("active", () -> getActiveCheckBox().isSelected(), v -> updateForValidation(getActiveCheckBox(), v)).
                withProperty("gender", () -> getGenderChoiceBox().getValue(), v -> updateForValidation(getGenderChoiceBox(), v));
    }

    @Override
    protected void validate() {
        Set<ConstraintViolation<FormModel>> violations = validator.validate(UIGroup.class);

        if(violations == null || violations.isEmpty()) {
            System.out.println("We have no violations!");
            FormModel model = updateModelByUI();
            // Send model to server...

        } else {
            System.out.println("We have violations!");
            violations.forEach(v -> System.out.println(v.getMessage()));
        }
    }
}
