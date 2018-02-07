import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class SearchingForQuiz
{
    companion object
    {
        fun show(user: User)
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val vbox = VBox()
            vbox.padding = Insets(15.0, 15.0, 100.0, 15.0)
            vbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(vbox)
            scene.stylesheets.add("Styles.css")
            stage.scene = scene

            // College Logo
            val imageHBox = HBox()
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.0
            image.fitWidth = 250.0
            (0 until 17).forEach { imageHBox.children.add(Label("      ")) }
            imageHBox.children.add(image)
            vbox.children.add(imageHBox)


            val listView = ListView<Quiz>()
            val allSortedQuizzes = FXCollections.observableArrayList(DBService.allQuizNames())

            // search bar for user to enter the title of the quiz they wish to play
            val searchBar = TextField()
            searchBar.style = "-fx-alignment: center;"
            searchBar.promptText = "Search for quiz"
            searchBar.textProperty().addListener { _, _, searchText
                ->
                val searchTrimmed = searchText.trim().toLowerCase()
                if (searchTrimmed.isNotBlank())
                {
                    listView.items.clear()
                    allSortedQuizzes.forEach { if (it.toString().toLowerCase().contains(searchTrimmed)) listView.items.add(it) }
                }
                else { listView.items = FXCollections.observableArrayList(DBService.allQuizNames()) }
            }
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(searchBar)

            fun showQuestionsInQuiz(user: User) //function which will move the user to the first question of their desired quiz
            {
                if (listView.selectionModel.selectedIndex < listView.items.size) //if the item clicked in the list view is actually a Quiz object and not empty
                {
                    val quiz = listView.selectionModel.selectedItem
                    PlayingQuizQuestion.show(0, user, DBService.questionsFromQuiz(quiz), quiz.quizID) //moves the user onto the first question of their desired quiz
                    stage.close()
                }
            }

            // list view
            listView.items = FXCollections.observableArrayList(DBService.allQuizNames())
            var playQuizNext = false //variable which will be used to allow double clicking of an item
            listView.selectionModel.selectedItemProperty().addListener { _, _, _ ->
                if (playQuizNext) showQuestionsInQuiz(user)
                else playQuizNext = true
            }


            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(listView)

            // play quiz button
            val playQuizHBox = HBox()
            val playQuizButton = Button("Play quiz!")
            playQuizButton.setOnAction {
                if (playQuizNext) showQuestionsInQuiz(user)
                else playQuizNext = true
            }
            (0 until 2).forEach { vbox.children.add(Label("")) }
            (0 until 18).forEach { playQuizHBox.children.add(Label("       ")) }
            playQuizHBox.children.add(playQuizButton)
            vbox.children.add(playQuizHBox)

            stage.show()
        }
    }
}