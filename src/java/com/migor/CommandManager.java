package com.migor;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

import static com.migor.utils.Preconditions.checkNotNull;

public class CommandManager
{
    private static final String COMMAND_LINE = "1 - exit, " +
                                               "2 - add book, " +
                                               "3 - add user, " +
                                               "4 - books list, " +
                                               "5 - users list, " +
                                               "6 - give out book, " +
                                               "7 - return book, " +
                                               "8 - user's books list";

    private static final String INPUT_USER_ID_COMMAND = "Введите id пользователя";
    private static final String INPUT_BOOK_ID_COMMAND = "Введите id книги";
    private static final String USER_NOT_FOUND = "Не найден пользователь (id: {0})";
    private static final String BOOK_NOT_FOUND = "Не найдена книга (id: {0})";
    private static final String USERS_BOOK_NOT_FOUND = "У пользователя (id: {0}) не найдена книга (id: {1})";

    private static final Map<String, BiConsumer<Book, String>> addBookActions = createAddBookActionsMap();

    private final BookStorage bookStorage;
    private final UserStorage userStorage;
    private final Scanner scanner;

    public CommandManager(BookStorage bookStorage, UserStorage userStorage, Scanner scanner)
    {
        this.scanner = checkNotNull(scanner);
        this.bookStorage = checkNotNull(bookStorage);
        this.userStorage = checkNotNull(userStorage);
    }

    public void start()
    {
        while (true)
        {
            System.out.println(COMMAND_LINE);

            String cmd = readInputString();
            switch (cmd)
            {
                case "1":
                    return;
                case "2":
                {
                    addBook();
                    continue;
                }
                case "3":
                {
                    addUser();
                    continue;
                }
                case "4":
                {
                    showBooks();
                    continue;
                }
                case "5":
                {
                    showUsers();
                    continue;
                }
                case "6":
                {
                    giveOutBook();
                    continue;
                }
                case "7":
                {
                    returnBook();
                    continue;
                }
                case "8":
                {
                    showUserBooks();
                    continue;
                }
                default:
                {
                    System.out.println("command not found");
                }
            }
        }
    }

    private void addBook()
    {
        Book book = new Book();

        addBookActions.forEach((k, v) -> {
            System.out.println(k);
            String value = readInputString();
            v.accept(book, value);
        });

        bookStorage.add(book);

        System.out.println("добавлена книга " + book);
    }

    private void addUser()
    {
        System.out.println("введите имя пользователя");

        String name = readInputString();

        User user = new User(name);

        userStorage.add(user);

        System.out.println("добавлен пользователь " + user);
    }

    private void showBooks()
    {
        System.out.println("список доступных книг");
        bookStorage.findAll().forEach(System.out::println);
    }

    private void showUsers()
    {
        System.out.println("список пользователей");
        userStorage.findAll().forEach(System.out::println);
    }

    private void giveOutBook()
    {
        System.out.println(INPUT_USER_ID_COMMAND);
        int userId = readInputInt();

        User user = userStorage.findById(userId)
                               .orElseThrow(() -> new RuntimeException(MessageFormat.format(USER_NOT_FOUND, userId)));

        System.out.println(INPUT_BOOK_ID_COMMAND);
        int bookId = readInputInt();

        Book book = bookStorage.findById(bookId)
                               .orElseThrow(() -> new RuntimeException(MessageFormat.format(BOOK_NOT_FOUND, bookId)));

        // TODO: по хорошему, наверное, надо как-то обеспечить атомарность операции
        bookStorage.remove(book);
        user.addBook(book);

        System.out.println(MessageFormat.format("Пользователю {0} выдана книга {1}", user, book));
    }

    private void returnBook()
    {
        System.out.println(INPUT_USER_ID_COMMAND);
        int userId = readInputInt();

        User user = userStorage.findById(userId)
                               .orElseThrow(() -> new RuntimeException(MessageFormat.format(USER_NOT_FOUND, userId)));

        System.out.println(INPUT_BOOK_ID_COMMAND);
        int bookId = readInputInt();

        Book book = user.findBookById(bookId)
                        .orElseThrow(() -> new RuntimeException(MessageFormat.format(USERS_BOOK_NOT_FOUND, userId, bookId)));

        // TODO: по хорошему, наверное, надо как-то обеспечить атомарность операции
        user.removeBook(book);
        bookStorage.add(book);
    }

    private void showUserBooks()
    {
        System.out.println(INPUT_USER_ID_COMMAND);
        int userId = readInputInt();

        User user = userStorage.findById(userId)
                               .orElseThrow(() -> new RuntimeException(MessageFormat.format(USER_NOT_FOUND, userId)));

        user.getBooks().forEach(System.out::println);
    }

    private String readInputString()
    {
        return scanner.nextLine();
    }

    private int readInputInt()
    {
        return Integer.parseInt(scanner.nextLine());
    }

    private static Map<String, BiConsumer<Book, String>> createAddBookActionsMap()
    {
        Map<String, BiConsumer<Book, String>> map = new HashMap<>();

        map.put("введите автора книги", (Book::setAuthor));
        map.put("введите название книги", (Book::setName));

        return Collections.unmodifiableMap(map);
    }
}
