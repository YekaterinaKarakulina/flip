package tests.businessObjects;

import java.util.List;
import java.util.stream.Collectors;

public class Book {

    private String name;
    private List<String> authorNameList;

    public Book(List<String> authorNameList) {
        this.authorNameList = authorNameList;
    }

    public Book(String name, List<String> authorNameList) {
        this.name = name;
        this.authorNameList = authorNameList;
    }

    public String getName() {
        return name;
    }

    public List<String> getAuthorNameList() {
        return authorNameList;
    }

    public int checkBooksEqualsByAuthorsList(Book expectedBook, Book actualBook) {
        List<String> selectedAuthorsList = expectedBook.getAuthorNameList().stream().filter(item -> actualBook.getAuthorNameList().contains(item)).collect(Collectors.toList());
        return selectedAuthorsList.size();
    }

}
