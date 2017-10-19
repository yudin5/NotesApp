package ru.vitaly.notes.database;

import ru.vitaly.notes.model.Note;
import java.sql.*;

/**
 * Этот поток запускается при создании новой заметки.
 * Он соединяется с базой данных и вносит туда новую заметку.
 */
public class DBnewNote implements Runnable {

    // URL, юзер и пароль для подключения
    private static final String url = "jdbc:mysql://localhost:3306/mynotesdb";
    private static final String user = "root";
    private static final String password = "root";

    // Переменные JDBC для манипулирования данными
    private static Connection con;
    private static Statement stmt;

    // Приватная переменная - новая заметка
    private Note note;

    // Конструктор, принимающий в качестве параметра новую созданную заметку
    public DBnewNote(Note tempNote) {
        this.note = tempNote;
    }

    @Override
    public void run() {
        // Сделано без трая-с-ресурсами для наглядности
        try {
            // Открываем соединение
            con = DriverManager.getConnection(url, user, password);
            // Создаем стейтмент для запросов
            stmt = con.createStatement();

            // Вносим данные
            // Извлекаем дату и текст
            String dateAndTime = note.getDateAndTime();
            String text = note.getText();
            // Формируем строку для запроса
            String queryInsert =
                    "INSERT INTO mynotesdb.notes (date_time, note_text) " +
                            "VALUES ('" + dateAndTime + "', '" + text + "');";

            // Выполняем сам запрос на добавление заметки в БД
            stmt.executeUpdate(queryInsert);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //закрываем здесь всё
            try { con.close(); } catch(SQLException se) { }
            try { stmt.close(); } catch(SQLException se) { }
        }
    }
}
