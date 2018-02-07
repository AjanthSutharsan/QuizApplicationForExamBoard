import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
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


            val table = TableView<ScoreTableData>() //tableview which will display the highscores is now a tableview which uses ScoreTableData objects
            table.minWidth = 800.0
            val highscoreCombo = ComboBox<Quiz>(FXCollections.observableArrayList(DBService.allQuizNames())) //shows the user all the quizzes in alphabetical order
            highscoreCombo.selectionModel.select(0)
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(highscoreCombo)

            // bottom -- columns of tableview
            bottomHBox.children.add(table)
            val idCol = TableColumn<ScoreTableData, String>("User ID") //all the columns also use ScoreTableData now
            val firstNameCol = TableColumn<ScoreTableData, String>("First Name")
            val lastNameCol = TableColumn<ScoreTableData, String>("Last Name")
            val scoreCol = TableColumn<ScoreTableData, String>("Score")
            table.columns.addAll(idCol, firstNameCol, lastNameCol, scoreCol)

            //these lines show which of the attributes of the ScoreTableData objects should be shown in which column
            idCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("userID")  //the userID attribute of each object will be displayed in this column
            firstNameCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("firstName") //the firstName attribute of each object will be displayed in this column
            lastNameCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("lastName") //the lastName attribute of each object will be displayed in this column
            scoreCol.cellValueFactory = PropertyValueFactory<ScoreTableData, String>("score") //the score attribute of each object will be displayed in this column

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
                if (highscoreTypeCombo.selectionModel.selectedItem == "All Users")
                    table.items = FXCollections.observableArrayList(DBService.getScoreData(highscoreCombo.selectionModel.selectedItem.quizID)) //will simply show the highest scores achieved by all users
                else
                    table.items = FXCollections.observableArrayList(DBService.getScoreData(highscoreCombo.selectionModel.selectedItem.quizID).filter { it.getUserID().toInt() == user.userID }) //the records will be filtered such that only the records with the user's userID remain
            }
            (0 until 1).forEach { rightVBox.children.add(Label("")) }
            rightVBox.children.add(displayButton)
            (0 until 2).forEach { rightVBox.children.add(Label("")) }

            stage.show()
        }
    }
}