import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class CreatingQuizTitle {
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

            val topHBox = HBox() //hbox will consist of the userLabel and the College Logo
            val middleHBox = HBox() //hbox will contain the Label for the quiz title and the text field for the user to enter the title of the quiz
            val bottomHBox = HBox() //hbox which will only contain the next button

            vbox.children.addAll(topHBox)
            val scene = Scene(vbox)
            stage.scene = scene

            // user label which displays the userID and the name of the user
            val userLabel = TextArea("ID: 63485\nName: Lelouch Lamperouge")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            topHBox.children.add(userLabel)

            // College Logo
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.1
            image.fitWidth = 250.1
            (0 until 10).forEach { topHBox.children.add(Label("      ")) } //horizontal padding from edge of window to the college logo
            topHBox.children.add(image)

            // Label consisting of "Quiz Title: "
            val quizTitleLabel = TextField("Quiz title: \t")
            quizTitleLabel.isEditable = false
            (0 until 17).forEach { middleHBox.children.add(Label("      ")) } //horizontal padding from edge of window to the quizTitle Label

            (0 until 2).forEach { vbox.children.add(Label("      ")) } //vertical padding between the topHBox and the middleHBox
            middleHBox.children.add(quizTitleLabel)
            vbox.children.add(middleHBox)

            // TextField for user to enter title of the quiz they're making
            val quizName = TextField()
            quizName.promptText = "Enter the quiz title here"
            middleHBox.children.add(quizName)

            // next button which should allow the user to move onto the screen to make the questions
            val nextButton = Button("Next!")
            nextButton.setOnAction {
                CreatingQuizQuestion.show(1)
                stage.close()
            }

            (0 until 23).forEach { bottomHBox.children.add(Label("      ")) } //horizontal padding from edge of window to the next button
            bottomHBox.children.add(nextButton)
            (0 until 5).forEach { vbox.children.add(Label("      ")) } //vertical padding between the middleHBox and the bottomHBox
            vbox.children.add(bottomHBox)

            // Show the Stage
            stage.show()
        }
    }
}

