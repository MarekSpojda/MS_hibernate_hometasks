package pl.coderslab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entities.Author;
import pl.coderslab.entities.Category;
import pl.coderslab.model.Article;
import pl.coderslab.repositories.ArticleRepository;
import pl.coderslab.repositories.AuthorRepository;
import pl.coderslab.repositories.CategoryRepository;
import pl.coderslab.utilities.CommonCode;
import pl.coderslab.validation_groups.ArticleGroup;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@Controller
@Transactional
public class ArticleController {
    private final EntityManager entityManager;

    private final ArticleRepository articleRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    public ArticleController(EntityManager entityManager, ArticleRepository articleRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.entityManager = entityManager;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(path = {"/savearticle"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String saveArticle(Model model) {
        model.addAttribute("article", new Article());
        return "forms/savearticle";
    }

    @RequestMapping(path = {"/savearticle"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String saveArticlePost(@ModelAttribute("article") @Validated({ArticleGroup.class}) Article article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "forms/savearticle";
        }

        article.setDraft(false);
        articleRepository.save(article);
        return "redirect:/allarticles";
    }

    @RequestMapping(path = {"/editarticle/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String editAuthor(@PathVariable Long id, Model model) {
        CommonCode.handleFillingFieldsOfForm(id, model, articleRepository);
        return "forms/editarticle";
    }

    @RequestMapping(path = {"/editarticle/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String editAuthorPost(@ModelAttribute("article") @Validated({ArticleGroup.class}) Article article, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/editarticle";
        }
        entityManager.merge(article);
        return "redirect:/allarticles";
    }

    @RequestMapping(path = {"/deletearticle/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String deleteArticle(@PathVariable Long id) {
        articleRepository.delete(articleRepository.findArticleByIdCustomized(id));
        return "redirect:/allarticles";
    }

    @RequestMapping(path = {"/allarticles"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String allArticles() {
        List<Article> articleList = articleRepository.findArticlesAndAvoidDrafts();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("<a href=\"/savearticle\">Dodaj artykuł</a><br><br>").
                append("Lista artykułów:<br>");

        for (Article article : articleList) {
            stringBuilder.append("Tytuł: ").
                    append(article.getTitle()).
                    append(", <a href=\"/editarticle/").append(article.getId()).append("\">Edytuj</a>").
                    append(", <a href=\"/deletearticle/").append(article.getId()).append("\">Usuń</a>").
                    append("<br>");
        }

        return stringBuilder.toString();
    }

    @ModelAttribute("authors")
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
