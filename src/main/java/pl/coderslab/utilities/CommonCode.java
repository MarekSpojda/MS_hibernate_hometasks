package pl.coderslab.utilities;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import pl.coderslab.model.Article;
import pl.coderslab.repositories.ArticleRepository;

public class CommonCode {
    public static void handleFillingFieldsOfForm(@PathVariable Long id, Model model, ArticleRepository articleRepository) {
        Article article = articleRepository.findArticleByIdCustomized(id);
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());
        Article art = new Article();
        art.setContent(article.getContent());
        model.addAttribute("article", art);
    }
}
