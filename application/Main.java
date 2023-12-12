package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		// TreeView on the left side
        TreeItem<String> rootItem = new TreeItem<>("Root");
        rootItem.setExpanded(true);
        TreeView<String> treeView = new TreeView<>(rootItem);

        // TextAreas and Buttons for adding users and groups
        TextArea userIdTextArea = new TextArea();
        userIdTextArea.setMaxHeight(50);
        userIdTextArea.setMaxWidth(200);
        userIdTextArea.setMinHeight(50);
        userIdTextArea.setMinWidth(200);
        userIdTextArea.setPromptText("User ID");
        Button addUserButton = new Button("Add User");

        TextArea groupIdTextArea = new TextArea();
        groupIdTextArea.setMaxHeight(50);
        groupIdTextArea.setMaxWidth(200);
        groupIdTextArea.setMinHeight(50);
        groupIdTextArea.setMinWidth(200);
        groupIdTextArea.setPromptText("Group ID");
        Button addGroupButton = new Button("Add Group");

        Button openUserViewButton = new Button("Open User View");
        
        // Buttons for showing information
        Button showUserTotalButton = new Button("Show User Total");
        Button showMessagesTotalButton = new Button("Show Messages Total");
        Button showGroupTotalButton = new Button("Show Group Total");
        Button showPositivePercentageButton = new Button("Show Positive Percentage");
        
        // Layout for add controls
        VBox addControls = new VBox(5, userIdTextArea, addUserButton, groupIdTextArea, addGroupButton, openUserViewButton);
        
        // Layout for information buttons
        HBox infoButtons = new HBox(5, showUserTotalButton, showMessagesTotalButton, showGroupTotalButton, showPositivePercentageButton);
        
        // Main layout
        BorderPane borderPane = new BorderPane();
        
        // Adding components to the layout
        borderPane.setLeft(new VBox(treeView));
        borderPane.setCenter(addControls);
        borderPane.setBottom(infoButtons);
        
        // Set margin and padding for layouts
        BorderPane.setMargin(treeView, new Insets(10));
        BorderPane.setMargin(addControls, new Insets(10));
        BorderPane.setMargin(infoButtons, new Insets(10));
        
        VBox.setMargin(userIdTextArea, new Insets(5, 0, 0, 0));
        VBox.setMargin(groupIdTextArea, new Insets(5, 0, 0, 0));
        
        HBox.setMargin(showUserTotalButton, new Insets(0, 5, 0, 0));
        HBox.setMargin(showMessagesTotalButton, new Insets(0, 5, 0, 0));
        HBox.setMargin(showGroupTotalButton, new Insets(0, 5, 0, 0));
        
        // Set actions for buttons
        // Here you would add the logic for what each button should do when clicked
        
        // Create scene and stage
        Scene scene = new Scene(borderPane, 800, 500);
        primaryStage.setTitle("Admin Control Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
