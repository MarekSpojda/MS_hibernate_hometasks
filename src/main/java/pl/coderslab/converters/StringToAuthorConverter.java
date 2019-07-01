package pl.coderslab.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.entities.Author;
import pl.coderslab.repositories.AuthorRepository;

public class StringToAuthorConverter implements Converter<String, Author> {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author convert(String s) {
        return authorRepository.findAuthorByIdCustomized(Long.parseLong(s));
    }
}
