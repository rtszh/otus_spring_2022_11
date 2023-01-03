package ru.otus.spring.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.processors.OutputProcessor;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;

    private final OutputProcessor outputProcessor;

    @Autowired
    public ShellCommands(BookService bookService, OutputProcessor outputProcessor) {
        this.bookService = bookService;
        this.outputProcessor = outputProcessor;
    }

    @ShellMethod(value = "Command for show all books", key = {"sb", "showBooks"})
    public void getAllBooks() {
        outputProcessor.outputString(bookService.getAllBooksAsString());
    }

    @ShellMethod(value = "Command for show book by title. Entry book title WITHOUT DOUBLE QUOTES!", key = {"sbt", "showBookByTitle"})
    public void getBookByTitle(@ShellOption String bookTitle) {
        outputProcessor.outputString(bookService.getBookByTitleAsString(bookTitle));
    }

    @ShellMethod(value = "Command for delete book by title. Entry book title WITHOUT DOUBLE QUOTES!", key = {"dbt", "deleteBookByTitle"})
    public void deleteBookByTitle(@ShellOption String bookTitle) {
        bookService.deleteBookByTitle(bookTitle);
        outputProcessor.outputString("Book deleted successfully");
    }

    @ShellMethod(value = "Command for insert book", key = {"ib", "insertBook"})
    public void insertBook() {
        bookService.insertBook();
        outputProcessor.outputString("Book inserted successfully");
    }

    @ShellMethod(value = "Command for update book", key = {"ub", "updateBook"})
    public void updateBook() {
        bookService.updateBook();
        outputProcessor.outputString("Book updated successfully");
    }

}
