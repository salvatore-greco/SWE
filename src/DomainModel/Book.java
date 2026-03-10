package DomainModel;

import java.util.ArrayList;

public class Book {
    // Builder like item2 of effective java

    public static class BookBuilder{
        private String ISBN;
        private String title;
        private ArrayList<String> authors;
        private String code; //book code to identify multiple copies

        public BookBuilder setISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }
        public BookBuilder setTitle(String title){
            this.title = title;
            return this;
        }
        public BookBuilder setAuthors(ArrayList<String> authors){
            this.authors = authors;
            return this;
        }

        public BookBuilder setCode(String code){
            this.code = code;
            return this;
        }
        public Book build(){
            return new Book(this);
        }

    }
    private Book(BookBuilder builder){
        this.ISBN = builder.ISBN;
        this.title = builder.title;
        this.code = builder.code;
        this.authors = builder.authors;
    }
    private String ISBN;
    private String title;
    private ArrayList<String> authors; //FIXME: togliere o aggiornare db
    private String code; //book code to identify multiple copies

    public void setAsLoaned() {
    }
    public void setAsReturned() {
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getCode() {
        return code;
    }
}
