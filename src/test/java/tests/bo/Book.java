package tests.bo;

public class Book {

    private String name;
    private String authorName;
    private int publicationYear;

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public static boolean checkBooksEquals(Book book1, Book book2) {
        if (book1.getAuthorName().equals(book2.getAuthorName())) {
            return true;
        } else {
            return false;
        }
    }

}
