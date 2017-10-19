package ru.vitaly.notes.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.vitaly.notes.database.DBnewNote;
import ru.vitaly.notes.model.Note;

/**
 * Окно для добавления новой заметки
 */
public class NoteNewDialogController {

    @FXML
    private TextField textField;
    @FXML
    private Label dateAndTimeLabel;

    private Stage dialogStage;
    private Note note;
    private boolean okClicked = false;

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Устанавливает сцену для этого окна.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Отображение заметки
     */
    public void setNote(Note note) {
        this.note = note;

        dateAndTimeLabel.setText(note.getDateAndTime());
        textField.setText(note.getText());
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        note.setText(textField.getText());

        okClicked = true;
        dialogStage.close();

        Thread DBnewNoteThread = new Thread(new DBnewNote(note));
        DBnewNoteThread.start();
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
