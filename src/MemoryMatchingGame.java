import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;

public class MemoryMatchingGame extends Application {

    private static final int SIZE = 4; // 4x4 grid
    private Button[][] buttons = new Button[SIZE][SIZE];
    private String[][] values = new String[SIZE][SIZE];
    private Button first = null;
    private Button second = null;
    private boolean waiting = false;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < (SIZE * SIZE) / 2; i++) {
            String value = String.valueOf((char) ('A' + i));
            pairs.add(value);
            pairs.add(value);
        }
        Collections.shuffle(pairs);

        Iterator<String> it = pairs.iterator();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String val = it.next();
                values[i][j] = val;

                Button btn = new Button();
                btn.setFont(new Font(20));
                btn.setMinSize(60, 60);
                int finalI = i;
                int finalJ = j;
                btn.setOnAction(e -> handleClick(btn, finalI, finalJ));

                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        primaryStage.setTitle("Memory Matching Game");
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }

    private void handleClick(Button btn, int i, int j) {
        if (waiting || btn.getText().length() > 0) return;

        btn.setText(values[i][j]);

        if (first == null) {
            first = btn;
        } else if (second == null && btn != first) {
            second = btn;
            checkMatch();
        }
    }

    private void checkMatch() {
        waiting = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    if (!first.getText().equals(second.getText())) {
                        first.setText("");
                        second.setText("");
                    }
                    first = null;
                    second = null;
                    waiting = false;
                });
            }
        }, 1000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
