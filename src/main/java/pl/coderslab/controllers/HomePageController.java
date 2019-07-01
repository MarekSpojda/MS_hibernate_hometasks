package pl.coderslab.controllers;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entities.Category;
import pl.coderslab.model.Article;
import pl.coderslab.repositories.ArticleRepository;
import pl.coderslab.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class HomePageController {
    private StringBuilder stringBuilder;
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public HomePageController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(path = {"/"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
//        List<Article> articles = articleRepository.findFirst5ByOrderByCreatedDesc();
        List<Article> articles = articleRepository.findArticlesAndAvoidDrafts();
        int size = articles.size();
        if (size > 5) {
            articles = articles.subList(0, 5);
        }

        stringBuilder = new StringBuilder();
        displaySpecifiedArticles(articles);

        stringBuilder.append("Lista kategorii (kliknij, żeby zobaczyć artykuły z danej kategorii):<br>");

        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            stringBuilder.append("Nazwa kategorii: ").
                    append(" <a href=\"categoryarticles/").
                    append(category.getName()).
                    append("\">").
                    append(category.getName()).
                    append("</a>").
                    append("<br>");
        }

        return stringBuilder.toString();
    }

    @RequestMapping(path = {"/categoryarticles/{categoryName}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String categoryArticles(@PathVariable String categoryName) {
        List<Article> sourceArticles = articleRepository.findAll();
        List<Article> articles = new ArrayList<>();
        Category specifiedCategory = categoryRepository.findByName(categoryName);

        for (Article article : sourceArticles) {
            List<Category> categoriesForArticle = article.getCategories();
            for (Category category : categoriesForArticle) {
                if (category.getId().equals(specifiedCategory.getId())) {
                    articles.add(article);
                    break;
                }
            }
        }

        stringBuilder = new StringBuilder();
        stringBuilder.append("Lista artykułów z kategorii ").
                append(specifiedCategory.getName()).
                append(":<br><br>");

        if (articles.size() == 0) {
            stringBuilder.
                    append("Nie ma artykułów oznaczonych kategorią ").
                    append(categoryName).
                    append(".<br>");
        }

        displaySpecifiedArticles(articles);
        return stringBuilder.toString();
    }

    private void displaySpecifiedArticles(List<Article> articles) {
        for (Article article : articles) {
            String article200;
            try {
                article200 = article.getContent().substring(0, 200);
            } catch (Exception e) {
                article200 = article.getContent();
            }

            stringBuilder.append("Title: ").
                    append(article.getTitle()).
                    append("<br>").
                    append("Content: ").
                    append(article200).
                    append("<br><br>");
        }
    }
}
