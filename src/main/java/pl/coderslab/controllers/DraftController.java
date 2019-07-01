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
import pl.coderslab.validation_groups.DraftGroup;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
@Transactional
public class DraftController {
    private final EntityManager entityManager;

    private final ArticleRepository articleRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    public DraftController(EntityManager entityManager, ArticleRepository articleRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.entityManager = entityManager;
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(path = {"/alldrafts"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String allDrafts() {
        List<Article> articleList = articleRepository.findDraftsAndAvoidArticles();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("<a href=\"/savedraft\">Dodaj szkic</a><br><br>").
                append("Lista szkiców:<br>");

        for (Article article : articleList) {
            stringBuilder.append("Tytuł: ").
                    append(article.getTitle()).
                    append(", <a href=\"/editdraft/").append(article.getId()).append("\">Edytuj</a>").
                    append(", <a href=\"/deletedraft/").append(article.getId()).append("\">Usuń</a>").
                    append("<br>");
        }

        return stringBuilder.toString();
    }

    @RequestMapping(path = {"/savedraft"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String savedraft(Model model) {
        model.addAttribute("article", new Article());
        return "forms/savearticle";
    }

    @RequestMapping(path = {"/savedraft"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String saveArticlePost(@ModelAttribute("article") @Validated({DraftGroup.class}) Article article, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "forms/savearticle";
        }

        article.setDraft(true);
        articleRepository.save(article);
        return "redirect:/alldrafts";
    }

    @RequestMapping(path = {"/deletedraft/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String deleteDraft(@PathVariable Long id) {
        articleRepository.delete(articleRepository.findArticleByIdCustomized(id));
        return "redirect:/alldrafts";
    }

    @RequestMapping(path = {"/editdraft/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String editAuthor(@PathVariable Long id, Model model) {
        CommonCode.handleFillingFieldsOfForm(id, model, articleRepository);
        return "forms/editarticle";
    }

    @RequestMapping(path = {"/editdraft/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String editAuthorPost(@ModelAttribute("article") @Validated({DraftGroup.class}) Article article, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/editarticle";
        }
        article.setDraft(true);
        entityManager.merge(article);
        return "redirect:/alldrafts";
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
