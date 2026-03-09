package BusinessLogic;

public enum role {
    //Nomi dei campi della enum esattamente come definiti su postgre così possiamo usare .name() nelle query
    librarian,
    libraryUser,
    libraryAdministrator;

}
// 'librarian', 'libraryUser', 'libraryAdministrator'