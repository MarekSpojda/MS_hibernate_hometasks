package pl.coderslab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entities.Category;
import pl.coderslab.repositories.CategoryRepository;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@Transactional
@Controller
public class CategoryController {
    private final EntityManager entityManager;

    @SuppressWarnings("FieldCanBeLocal")
    private final CategoryRepository categoryRepository;

    public CategoryController(EntityManager entityManager, CategoryRepository categoryRepository) {
        this.entityManager = entityManager;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(path = {"/savecategory"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String saveCategory(Model model) {
        model.addAttribute("category", new Category());
        return "forms/savecategory";
    }

    @RequestMapping(path = {"/savecategory"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String saveCategoryPost(@ModelAttribute("category") @Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/savecategory";
        }
        categoryRepository.save(category);
        return "forms/success";
    }

    @RequestMapping(path = {"/editcategory/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String editCategory(Model model, @PathVariable Long id) {
        Category category = categoryRepository.findByIdCustomized(id);

        model.addAttribute("categoryName", category.getName());
        model.addAttribute("categoryDescription", category.getDescription());
        model.addAttribute("category", new Category());

        return "forms/editcategory";
    }

    @RequestMapping(path = {"/editcategory/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String editCategoryPost(@ModelAttribute("category") @Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/editcategory";
        }
        entityManager.merge(category);
        return "forms/success";
    }

    @RequestMapping(path = {"/deletecategory/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String editCategory(@PathVariable Long id) {
        entityManager.remove(categoryRepository.findByIdCustomized(id));

        return "redirect:success";
    }

    @RequestMapping(path = {"/deletecategory/success"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String success() {
        return "forms/success";
    }

    @RequestMapping(path = {"/allcategories"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String allCategories() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href=\"/savecategory\">Dodaj kategorię</a><br><br>");

        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList) {
            stringBuilder.
                    append("Nazwa kategorii: ").
                    append(category.getName()).
                    append(", opis: ").
                    append(category.getDescription()).
                    append(", <a href=\"/editcategory/").append(category.getId()).append("\">Edycja</a>").
                    append(", <a href=\"/deletecategory/").append(category.getId()).append("\">Usuń</a>").
                    append("<br>");
            ;
        }
        stringBuilder.append("<br>");
        return stringBuilder.toString();
    }
}
