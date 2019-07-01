package pl.coderslab.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.entities.Category;
import pl.coderslab.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class StringToCategoryListConverter implements Converter<String, List<Category>> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> convert(String categoryAsString) {
        List<Category> categoryList = new ArrayList<>();
        Category category = categoryRepository.findByName(categoryAsString);
        categoryList.add(category);
        return categoryList;
    }
}
