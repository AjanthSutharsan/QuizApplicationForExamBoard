import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.FileInputStream

class MainMenu
{
    companion object
    {
        val buttonSTYLE = "-fx-alignment: CENTER; -fx-accent: #d8eeff; -fx-focus-color: #6cd4f4;"
        val BACKGROUND_COLOUR_STYLE = "-fx-background: #ffb347"
        fun show()
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            // Set up of Main hbox
            val hbox = HBox()
            hbox.style = BACKGROUND_COLOUR_STYLE
            hbox.padding = Insets(15.0, 15.0, 15.0, 15.0)
            hbox.spacing = 100.0

            val scene = Scene(hbox)
            stage.scene = scene

            // left vbox which userLabel and make a quiz button
            val leftVBox = VBox()
            hbox.children.add(leftVBox)

            // middle vbox which consists of the college logo and the play quiz button
            val middleVBox = VBox()
            hbox.children.add(middleVBox)

            // right vbox which will include the log out and make quiz buttons
            val rightVBox = VBox()
            hbox.children.add(rightVBox)

            // user label which displays the userID and the name of the user
            val userLabel = TextArea("ID: 63485\nName: Lelouch Lamperouge")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            leftVBox.children.add(userLabel)

            // PADDING between userLabel and Play a Quiz button
            (0 until 22).forEach {
                val padding = Label("")
                padding.style = "-fx-background: #ffb347"
                leftVBox.children.add(padding)
            }

            // Make a Quiz button
            val makeQuizButton = Button("Make a quiz!")
            makeQuizButton.style = buttonSTYLE
            makeQuizButton.setOnAction {
                CreatingQuizTitle.show()
                stage.close()
            }
            makeQuizButton.setMinSize(250.0, 150.0)
            leftVBox.children.add(makeQuizButton)

            // College Logo
            val image = ImageView(Image(FileInputStream("C:\\Users\\Sutharsan\\Pictures\\logo.jpg"))
            )
            image.fitHeight = 250.1
            image.fitWidth = 250.1
            middleVBox.children.add(image)

            // PADDING between College Logo and Play a Quiz button
            (0 until 10).forEach {
                val padding = Label("")
                middleVBox.children.add(padding)
            }

            // Play a Quiz button
            val playQuizButton = Button("Play a quiz!")
            playQuizButton.style = buttonSTYLE
            playQuizButton.setOnAction {
                SearchingForQuiz.show()
                stage.close()
            }
            playQuizButton.setMinSize(250.0, 150.0)
            middleVBox.children.add(playQuizButton)

            // Hbox which contains the Log Out button
            val padddingHBox = HBox()
            rightVBox.children.add(padddingHBox)

            // Horizontal padding in order to align the Log Out button to the top left of the scene
            (0 until 4).forEach {
                val padding = Label("           ")
                padddingHBox.children.add(padding)
            }


            // Log Out button
            val logOutButton = Button("Log out")
            logOutButton.style = buttonSTYLE
            logOutButton.setMinSize(100.0, 80.0)
            logOutButton.setOnAction { stage.close() }
            padddingHBox.children.add(logOutButton)

            // Vertical padding between Log Out button and the View Highscores button
            (0 until 20).forEach {
                val padding = Label("")
                rightVBox.children.add(padding)
            }

            // View highscores button
            val viewHighscoresButton = Button("View highscores!")
            viewHighscoresButton.setOnAction {
                ViewHighScores.show()
                stage.close()
            }
            viewHighscoresButton.style = buttonSTYLE
            viewHighscoresButton.setMinSize(250.0, 150.0)
            rightVBox.children.add(viewHighscoresButton)

            // Show the stage
            stage.show()
        }
    }
}