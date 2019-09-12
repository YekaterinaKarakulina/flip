package tests.bo;

public class Book {
    private String name;
    private String authorName;
    private int publicationYear;

    public Book(String name, String authorName, int publicationYear) {
        this.name = name;
        this.authorName = authorName;
        this.publicationYear = publicationYear;
    }

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



}
