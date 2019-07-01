package pl.coderslab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.model.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findFirst5ByOrderByCreatedDesc();

    @Query("select a from Article a where a.id=?1")
    Article findArticleByIdCustomized(Long id);

    @Query("select a from Article a order by a.created desc")
    List<Article> findArticlesByCreatedDesc();

    @Query("select a from Article a  where a.draft=false order by a.created desc")
    List<Article> findArticlesAndAvoidDrafts();

    @Query("select a from Article a  where a.draft=true order by a.created desc")
    List<Article> findDraftsAndAvoidArticles();
}
