package ru.vitaly.notes.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.Instant;

/**
 * Это класс (модель) для Заметки.
 */

public class Note {

    private final StringProperty dateAndTime; // Вначале создал LocalDate, но затем для простоты поменял на String
    private final StringProperty text;

    /**
     * Конструктор по умолчанию.
     */
    public Note() {
        this(null, null);
    }

    /**
     * Конструктор с начальными данными
     */
    public Note(String dateAndTime, String text) {

        // TODO: Сделать нормально с помощью форматтера. Разобраться с DATETIME SQL.
        if (dateAndTime == null) {
            Instant instant = Instant.now().plusSeconds(3600*4); // Прибавляем 4 часа к дефолтному времени. Разобраться, как установить зональное время.
            dateAndTime = instant.toString();
            dateAndTime = dateAndTime.substring(0, dateAndTime.length() - 5).replace("T", " ");
        }

        this.dateAndTime = new SimpleStringProperty(dateAndTime);
        this.text = new SimpleStringProperty(text);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public String getDateAndTime() {
        return dateAndTime.get();
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime.set(dateAndTime);
    } // Нигде не используется, но на будущее возможность установить дату и время в уже внесенной заметке.

    public StringProperty dateAndTimeProperty() {
        return dateAndTime;
    }

}
