package pl.coderslab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entities.Author;
import pl.coderslab.repositories.AuthorRepository;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@Transactional
@Controller
public class AuthorController {
    private final EntityManager entityManager;

    @SuppressWarnings("FieldCanBeLocal")
    private final AuthorRepository authorRepository;

    public AuthorController(EntityManager entityManager, AuthorRepository authorRepository) {
        this.entityManager = entityManager;
        this.authorRepository = authorRepository;
    }

    @RequestMapping(path = {"/saveauthor"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String saveAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "forms/saveauthor";
    }

    @RequestMapping(path = {"/saveauthor"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String saveAuthorPost(@ModelAttribute("author") @Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/saveauthor";
        }
        author.setFullName(author.getFirstName() + " " + author.getLastName());
        authorRepository.save(author);
        return "redirect:/allauthors";
    }

    @RequestMapping(path = {"/editauthor/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String editAuthor(@PathVariable Long id, Model model) {
        Author author = authorRepository.findAuthorByIdCustomized(id);
        model.addAttribute("firstName", author.getFirstName());
        model.addAttribute("lastName", author.getLastName());
        model.addAttribute("author", new Author());
        return "forms/editauthor";
    }

    @RequestMapping(path = {"/editauthor/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.POST)
    public String editAuthorPost(@ModelAttribute("author") @Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/editauthor";
        }
        author.setFullName(author.getFirstName() + " " + author.getLastName());
        entityManager.merge(author);
        return "redirect:/allauthors";
    }

    @RequestMapping(path = {"/deleteauthor/{id}"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    public String editCategory(@PathVariable Long id) {
        entityManager.remove(authorRepository.findAuthorByIdCustomized(id));

        return "redirect:/allauthors";
    }

    @RequestMapping(path = {"/allauthors"}, produces = "text/html; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String allAuthors() {
        List<Author> authorList = authorRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("<a href=\"/saveauthor\">Dodaj autora</a><br><br>").
                append("Lista autorów:<br>");

        for (Author author : authorList) {
            stringBuilder.append("Imię i nazwisko: ").
                    append(author.getFullName()).
                    append(", <a href=\"/editauthor/").append(author.getId()).append("\">Edytuj</a>").
                    append(", <a href=\"/deleteauthor/").append(author.getId()).append("\">Usuń</a>").
                    append("<br>");
        }

        return stringBuilder.toString();
    }
}
