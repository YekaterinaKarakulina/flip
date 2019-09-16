package tests.businessObjects;

import java.util.List;

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

    public boolean checkBooksEqualsByAuthorsList(Book expectedBook, Book actualBook) {
        return actualBook.getAuthorNameList().stream().anyMatch(expectedBook.getAuthorNameList()::contains);
    }

}
