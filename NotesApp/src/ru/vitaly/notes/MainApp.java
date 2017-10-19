package ru.vitaly.notes;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ru.vitaly.notes.model.Note;
import ru.vitaly.notes.view.NoteNewDialogController;
import ru.vitaly.notes.view.NoteOverviewController;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // Исключительно для целей использования с DB.
    // TODO: Подумать, как получить доступ к контроллеру из потока для работы с DB
    public static NoteOverviewController staticcontroller = null;

    /**
     * Данные, в виде списка заметок
     */
    private ObservableList<Note> noteData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public MainApp() {
        // Для теста можно добавить одну или несколько заметок
        //noteData.add(new Note(null, "Текст заметки 1"));
    }

    /**
     * Возвращает данные в виде наблюдаемого списка заметок
     */
    public ObservableList<Note> getNoteData() {
        return this.noteData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("NotesApp");

        initRootLayout();

        showNoteOverview();
    }

    /**
     * Инициализация корневого макета
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показываем в корневом макете информацию о заметках
     */
    private void showNoteOverview() {
        try {
            // Загружаем сведения о заметках
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NoteOverview.fxml"));
            AnchorPane noteOverview = (AnchorPane) loader.load();

            // Помещаем сведения о заметках в центр корневого макета.
            rootLayout.setCenter(noteOverview);

            // Даём контроллеру доступ к главному приложению.
            NoteOverviewController controller = loader.getController();
            controller.setMainApp(this);

            // Статическая переменная на текущий контроллер
            staticcontroller = controller;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращаем главную сцену
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Открываем диалоговое окно при нажатии на кнопку "New"
     */
    public boolean showNoteNewDialog(Note note) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NoteNewDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Note");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем заготовку заметки в контроллер
            NoteNewDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setNote(note);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
