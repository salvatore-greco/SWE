package ORM;

public class BookDTO {
    private String title;
    private String code;
    private String isbn;

    public BookDTO(String title, String code, String isbn) {
        this.title = title;
        this.code = code;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getIsbn() {
        return isbn;
    }
}