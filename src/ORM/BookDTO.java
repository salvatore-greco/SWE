package ORM;

public class BookDTO {
    private String code;
    private String isbn;
    private String title;

    public BookDTO(String code, String isbn, String title) {
        this.code = code;
        this.isbn = isbn;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }
}