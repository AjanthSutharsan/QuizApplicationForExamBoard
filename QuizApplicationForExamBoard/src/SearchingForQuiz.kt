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
        fun show()
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


            // search bar for user to enter the title of the quiz they wish to play
            val searchBar = TextField()
            searchBar.style = "-fx-alignment: center;"
            searchBar.promptText = "Search for quiz"
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(searchBar)


            //List View which will contain search results
            val listView = ListView<String>()
            listView.items.add("One")
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(listView)

            // play quiz button
            val playQuizHBox = HBox()
            val playQuizButton = Button("Play quiz!")
            playQuizButton.setOnAction {
                PlayingQuizQuestion.show(0)
                stage.close()
            }
            (0 until 2).forEach { vbox.children.add(Label("")) }
            (0 until 18).forEach { playQuizHBox.children.add(Label("       ")) }
            playQuizHBox.children.add(playQuizButton)
            vbox.children.add(playQuizHBox)

            stage.show()
        }
    }
}