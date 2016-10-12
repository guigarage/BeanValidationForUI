package com.guigarage.validation.model;

import com.guigarage.validation.util.UIGroup;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by hendrikebbers on 12.10.16.
 */
public class FormModel {

    @NotNull(message = "You must provide a first name", groups = UIGroup.class)
    @Size(min = 1, max = 64, message = "The lenght of the entered first name must be between 1 and 64 chars",groups = UIGroup.class)
    private String firstName;

    @NotNull(message = "You must provide a last name", groups = UIGroup.class)
    @Size(min = 1, max = 64, message = "The lenght of the entered last name must be between 1 and 64 chars",groups = UIGroup.class)
    private String lastName;

    @AssertTrue(message = "The active flag must be selected", groups = UIGroup.class)
    private boolean active;

    @NotNull(message = "You must select a gender", groups = UIGroup.class)
    private Gender gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
