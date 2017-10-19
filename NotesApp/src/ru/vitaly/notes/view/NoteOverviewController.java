package ru.vitaly.notes.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.vitaly.notes.MainApp;
import ru.vitaly.notes.database.DBrequest;
import ru.vitaly.notes.model.Note;

public class NoteOverviewController {
    /**
     * Аннотация @FXML необходима для того, чтобы fxml-файл имел доступ к приватным полям и методам.
     * К тому же Scene Builder видит такие переменные и методы.
     */
    @FXML
    private TableView<Note> noteTable;
    @FXML
    private TableColumn<Note, String> dateAndTimeColumn;
    @FXML
    private TableColumn<Note, String> textColumn;

    @FXML
    private Label dateAndTimeLabel;
    @FXML
    private Label textLabel;

    // Ссылка на главное приложение.
    private MainApp mainApp;

    // Геттер главного приложения
    public MainApp getMainApp() {
        return mainApp;
    }

    /**
     * Конструктор вызывается раньше метода initialize().
     */
    public NoteOverviewController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы сообщений с двумя столбцами.
        dateAndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dateAndTimeProperty());
        textColumn.setCellValueFactory(cellData -> cellData.getValue().textProperty());

        // Очистка информации о заметке
        showNoteDetails(null);

        // Используем интерфейс ChangeListener с единственным методом changed(...) через лямбда-выражение
        // Слушаем изменения выбора, и при изменении отображаем текст заметки
        noteTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNoteDetails(newValue));
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Thread dbThread = new Thread(new DBrequest());
        dbThread.start();
        // Добавление в таблицу данных из наблюдаемого списка
        noteTable.setItems(mainApp.getNoteData());
    }

    /**
     * Заполняет поля, отображая подробности о заметке - то есть показывает текст.
     * Если заметка = null, то текст очищается.
     */
    private void showNoteDetails(Note note) {
        if (note != null) {
            // Заполняем метки информацией из объекта note.
            dateAndTimeLabel.setText(note.getDateAndTime());
            textLabel.setText(note.getText());
        } else {
            // Если note == null, то убираем весь текст.
            dateAndTimeLabel.setText("");
            textLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке "New"
     * Открывает диалоговое окно с возможностью ввести текст
     */
    @FXML
    private void handleNewNote() {
        Note tempNote = new Note();
        // Подставляем в метку текущие дату и время.
        boolean okClicked = mainApp.showNoteNewDialog(tempNote);
        if (okClicked) {
            mainApp.getNoteData().add(tempNote);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке "Delete"
     * TODO: Сделать, чтобы кнопка "Delete" была просто неактивна, если пользователь не выбрал заметку.
     * TODO: Убрал пока вообще эту кнопку.
     */
    /*
    @FXML
    private void handleDeleteNote() {
        int selectedIndex = noteTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            noteTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No any note has been selected");
            alert.setContentText("Please select a note in the table.");

            alert.showAndWait();
        }
    }
    */

    /**
     * Вызывается, когда пользователь кликает по кнопке "DB sync"
     * Пока что просто загружает в базу данных наши заметки
     * TODO: Убрал пока вообще эту кнопку.
     */
    /*
    @FXML
    private void handleDBsync() {
        // Создаем отдельный поток
        Thread dbThread = new Thread(new DBrequest());
        dbThread.start();
    }
    */
}
