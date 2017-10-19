package ru.vitaly.notes.database;

import ru.vitaly.notes.MainApp;
import ru.vitaly.notes.model.Note;
import ru.vitaly.notes.view.NoteOverviewController;
import java.sql.*;

/**
 * Этот поток запускается при запуске приложения.
 * Он соединяется с базой данных, выгружает все заметки и отображает их на экране.
 */

public class DBrequest implements Runnable {

    // URL, юзер и пароль для подключения
    private static final String url = "jdbc:mysql://localhost:3306/mynotesdb";
    private static final String user = "root";
    private static final String password = "root";

    // Переменные JDBC для манипулирования данными
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    @Override
    public void run() {

        // Сделано без трая-с-ресурсами для наглядности
        try {
            // Открываем соединение
            con = DriverManager.getConnection(url, user, password);
            // Создаем стейтмент для запросов
            stmt = con.createStatement();
            // Формируем строку для запроса
            String query = "select date_time, note_text from notes";
            // Выполняем запрос
            rs = stmt.executeQuery(query);

            // Выбираем данные с БД
            ResultSet rs = stmt.executeQuery(query);

            // Извлекаем данные из ResultSet и отображаем их
            NoteOverviewController controller = MainApp.staticcontroller;
            while (rs.next()) {
                System.out.println("dateTime: " + rs.getString("date_time") +
                                 " note_text: " + rs.getString("note_text"));
                controller.getMainApp().getNoteData().add(new Note(rs.getString("date_time"), rs.getString("note_text")));
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //закрываем здесь всё
            try { con.close(); } catch(SQLException se) { }
            try { stmt.close(); } catch(SQLException se) { }
            try { rs.close(); } catch(SQLException se) { }
        }
    }
}
