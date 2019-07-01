package pl.coderslab.validators;

import pl.coderslab.entities.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxCategoryValidator implements ConstraintValidator<MaxCategory, List<Category>> {
    private int maxCategories;

    @Override
    public void initialize(MaxCategory constraintAnnotation) {
        this.maxCategories = constraintAnnotation.maxCategories();
    }

    @Override
    public boolean isValid(List<Category> categories, ConstraintValidatorContext constraintValidatorContext) {
        return categories.size() <= this.maxCategories;
    }
}
