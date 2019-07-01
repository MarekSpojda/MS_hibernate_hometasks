package pl.coderslab.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.entities.Category;
import pl.coderslab.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class StringTableToCategoryListConverter implements Converter<String[], List<Category>> {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> convert(String[] strings) {
        List<Category> categoryList = new ArrayList<>();
        for (String str : strings) {
            Category category = categoryRepository.findByName(str);
            categoryList.add(category);
        }
        return categoryList;
    }
}
