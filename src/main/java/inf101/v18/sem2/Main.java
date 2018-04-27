package inf101.v18.sem2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;


public class Main extends Application {
    static final int WIDTH = 720;
    static final int HEIGHT = 500;
    private Stage stage;
    private BorderPane root;
    /**
     * Generates the login window
     *
     * @return login window
     */
    private Parent loginWindow() {
        Label labelTitle = new Label("Connect-4");
        labelTitle.setUnderline(true);
        labelTitle.setId("title");

       Image bootImage = new Image("images/connect.jpg", 500, 250, true, true);
       Rectangle bootDecal = new Rectangle(bootImage.getRequestedWidth(), bootImage.getRequestedHeight());
       bootDecal.setFill(new ImagePattern(bootImage));

        Label labelUsername = new Label("Username:");
        labelUsername.setPrefWidth(120);
        labelUsername.setAlignment(Pos.CENTER);

        TextField textUsername = new TextField();
        textUsername.setPrefWidth(240);
        textUsername.setAlignment(Pos.CENTER);
        Button loginButton = new Button();
        loginButton.setText("LOGIN");
        loginButton.setPrefWidth(120);

        Text errorField = new Text();
        errorField.setFill(Color.RED);

        textUsername.setOnAction(e -> handleLogin(textUsername.getText(), errorField));
        loginButton.setOnAction(e -> handleLogin(textUsername.getText(), errorField));

        VBox loginContainer = new VBox(10);
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setPrefWidth(240);
        loginContainer.setMaxWidth(240);
        loginContainer.getChildren().addAll(labelUsername, textUsername);

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(bootDecal, loginContainer, loginButton, errorField);
        container.setPrefSize(WIDTH, HEIGHT);
        return container;
    }


    private void handleLogin(String username, Text errorField) {
        if (username == null || username.trim().isEmpty())
            errorField.setText("Please enter a non-empty player1.");
        else {

            mainMenu(username, stage);
        }
    }
    public void mainMenu(String username, Stage stage) {
        root = new BorderPane();

        Label labelWelcome = new Label("Welcome, " + username);
        labelWelcome.setPrefWidth(WIDTH);
        labelWelcome.setMinHeight((HEIGHT / 8) * 2);
        labelWelcome.setAlignment(Pos.CENTER);
        labelWelcome.setId("title");
        labelWelcome.setTextAlignment(TextAlignment.CENTER);

        Button buttonPlayVersus = new Button();
        buttonPlayVersus.setText("PLAY: VERSUS");

        Button buttonPlayEasy = new Button();
        buttonPlayEasy.setText("PLAY: EASY");

        Button buttonPlayMedium = new Button();
        buttonPlayMedium.setText("PLAY: MEDIUM");


        Button buttonPlayHard = new Button();
        buttonPlayHard.setText("PLAY: HARD");

        Button buttonQuit = new Button();
        buttonQuit.setText("QUIT");

        /*
        Checks that the username for player 2 isn't a duplicate in versus.
         */
        buttonPlayVersus.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("Enter the second players player1");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText("Enter the second players player1:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(player2 -> {
                if (!username.toLowerCase().equals(player2.toLowerCase())) {
                    createBoard(username, player2, 0);
                } else System.out.println("You can't play against yourself!");
            });
        });

        buttonPlayEasy.setOnAction(e -> createBoard(username, "Computer:Easy", 1));
        buttonPlayMedium.setOnAction(e -> createBoard(username,"Computer: Medium", 2));
        buttonPlayHard.setOnAction(e -> createBoard(username,"Computer:Hard" ,3));
        buttonQuit.setOnAction(e -> onQuit());

        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.BASELINE_CENTER);
        buttonContainer.getChildren().addAll(buttonPlayVersus, buttonPlayEasy, buttonPlayMedium, buttonPlayHard, buttonQuit);

        VBox mainContent = new VBox(0);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPrefSize(WIDTH, HEIGHT);
        mainContent.getChildren().addAll(labelWelcome, buttonContainer);

        root.setCenter(mainContent);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
    }
    private void createBoard(String player1, String player2, int difficulty) {
        /*
        Decides how difficult the game will be.
         */
        if(difficulty == 0){
            new HumanVSHuman(player1, player2);
        }
        if (difficulty == 1) {
            new HumanVSAi(1);
        }
        if(difficulty == 2){
            new HumanVSAi(5);
        }
        if (difficulty == 3){
            new HumanVSAi(7);
        }
        stage.hide();


    }
        //return gameBoard.getContainer();
        public void onQuit() {
            System.exit(0);
        }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Scene scene = new Scene(loginWindow());
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Connect four");
        stage.setResizable(false);
        stage.show();
    }
}
