import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage


class ViewHighScores
{
    companion object
    {
        fun show(user: User)
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val mainVBox = VBox()
            mainVBox.padding = Insets(15.0, 15.0, 100.0, 85.0)
            mainVBox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(mainVBox)
            stage.scene = scene

            // hboxes
            val topHBox = HBox()
            val bottomHBox = HBox()
            mainVBox.children.addAll(topHBox, bottomHBox)

            // vboxes
            val leftVBox = VBox()
            val lefterVBox = VBox()
            val lefterHBox = HBox()
            (0 until 8).forEach { lefterHBox.children.add(Label("       ")) }
            lefterVBox.children.add(lefterHBox)
            val divider = VBox() //a VBox which is used for padding
            val dividerHBox = HBox()
            divider.children.add(dividerHBox)
            (0 until 1).forEach { dividerHBox.children.add(Label("      ")) }
            val rightVBox = VBox()
            topHBox.children.addAll(lefterVBox, leftVBox, dividerHBox, rightVBox)

            // left vbox -- labels
            val quizLabel = Label("Quiz: \t") //label next to Combo Box which will contain all of the quizzes
            val highscoresLabel = Label("Highscore to display: \t") //label next to combo box which will ask the user to choose either to display their own highscores or the highscores of all users
            (0 until 4).forEach { leftVBox.children.add(Label("")) }
            leftVBox.children.addAll(quizLabel, Label(""), highscoresLabel)

            // center vbox
            val highscoresHeader = Label("HIGHSCORES") //title
            highscoresHeader.style = "-fx-font-family: Georgia; -fx-font-size: 20; -fx-underline: true;"
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoresHeader)

            val highscoreTypeCombo = ComboBox<String>(FXCollections.observableArrayList(arrayListOf("All Users", "Mine Only"))) //combo box which will ask the user to choose either to display their own highscores or the highscores of all users
            highscoreTypeCombo.selectionModel.select(0)
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoreTypeCombo)

            val table = TableView<String>() //tableview which will display the highscores
            table.minWidth = 800.0
            val highscoreCombo = ComboBox<String>()
            highscoreCombo.selectionModel.select(0)
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoreCombo)

            // bottom -- columns of tableview
            bottomHBox.children.add(table)
            val idCol = TableColumn<String, String>("User ID")
            val firstNameCol = TableColumn<String, String>("First Name")
            val lastNameCol = TableColumn<String, String>("Last Name")
            val scoreCol = TableColumn<String, String>("Score")
            table.columns.addAll(idCol, firstNameCol, lastNameCol, scoreCol)

            idCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))
            firstNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))
            lastNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))
            scoreCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25))

            //ensuring the columns cannot show anything apart from the highscores
            idCol.isSortable = false
            firstNameCol.isSortable = false
            lastNameCol.isSortable = false
            scoreCol.isSortable = false

            //button which, when pressed, will display the highscores on the tableview
            val displayButton = Button("Display!")
            displayButton.setOnAction {
                Alert(Alert.AlertType.ERROR, "This button doesn’t do anything yet").showAndWait()
            }
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(displayButton)
            (0 until 2).forEach { rightVBox.children.add(Label("")) }

            stage.show()
        }
    }
}