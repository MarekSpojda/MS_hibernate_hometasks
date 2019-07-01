package pl.coderslab.model;

import pl.coderslab.entities.Author;
import pl.coderslab.entities.Category;
import pl.coderslab.validation_groups.ArticleGroup;
import pl.coderslab.validation_groups.DraftGroup;
import pl.coderslab.validators.MaxCategory;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings({"unused", "JpaDataSourceORMInspection"})
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 1, max = 200, message = "Tytuł musi mieć co najmniej 1 znak i nie więcej niż 200.", groups = {ArticleGroup.class, DraftGroup.class})
    private
    String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    @NotEmpty(groups = {ArticleGroup.class})
    @MaxCategory(maxCategories = 2, groups = {ArticleGroup.class})
    private List<Category> categories;

    @Size(min = 50, max = 1000, message = "Zawartość musi mieć co najmniej 50 znaków i nie więcej niż 1000 znaków.", groups = {ArticleGroup.class, DraftGroup.class})
    private String content;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    private boolean draft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public boolean isDraft() {
        return draft;
    }

    public boolean getDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }
}
