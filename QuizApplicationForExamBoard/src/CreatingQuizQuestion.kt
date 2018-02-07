import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class CreatingQuizQuestion {
    companion object
    {
        fun show(questionNumber: Int, user: User, quizID: Int)
        {
            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false

            val hbox = HBox()
            hbox.padding = Insets(15.0, 15.0, 100.0, 15.0)
            hbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox() //VBox which contains the userLabel, the question TextField, and the guidance text
            val dividerHBox = HBox()
            val divider = VBox()
            divider.children.add(dividerHBox)
            (0 until 7).forEach { dividerHBox.children.add(Label("       ")) } //horizontal padding between the left VBox and the right VBox

            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, divider, rightVBox)

            // user label containing the userID and the name of the user
            val userLabel = TextArea("ID: ${user.userID}\nName: ${user.firstName} ${user.lastName}")
            userLabel.isEditable = false
            userLabel.setMinSize(110.0, 20.0)
            leftVBox.children.add(userLabel)

            (0 until 7).forEach { leftVBox.children.add(Label("")) } //vertical padding between the userLabel and the textfield to enter the question

            // TextField where the user inputs the question
            val questionName = TextField("")
            questionName.promptText = "Type question here"
            leftVBox.children.add(questionName)

            (0 until 9).forEach { leftVBox.children.add(Label("")) } //vertical padding between the textfield to enter the question and the guidance text

            // TextArea containing guidance text to help the user navigate through the screen
            val guidanceText = TextArea("Ensure you don't make any spelling mistakes! \n\nREMEMBER: This is NOT the order in which \nthe answers will be shown to the users.")
            guidanceText.isEditable = false
            leftVBox.children.add(guidanceText)

            // Label which contains the Question Number
            val questionNumberHBox = HBox()
            val questionNumberLabel = Label("Question #${questionNumber}")
            questionNumberLabel.style = "-fx-font-family: Georgia; -fx-font-size: 20; -fx-underline: true;"
            rightVBox.children.add(questionNumberHBox)
            (0 until 6).forEach {questionNumberHBox.children.add(Label("\t"))} //horizontal padding between the edge  and centre of the questionNumberHBox
            questionNumberHBox.children.add(questionNumberLabel)
            (0 until 2).forEach { rightVBox.children.add(Label("")) } //vertical padding between the question number and the first of the 4 asnwers

            val answerTexts = ArrayList<TextArea>()
            (0 until 4).forEach {
                // answer header label
                val answerLabel = TextField("Answer ${it + 1} (${if (it != 3) "in" else ""}correct):")
                rightVBox.children.add(answerLabel)

                // answer text
                val answerText = TextArea("\n\n")
                rightVBox.children.add(answerText)
                (0 until 2).forEach { rightVBox.children.add(Label("")) } //vertical padding between the answers
                answerTexts += answerText
            }

            //button which moves the user onto the next question to make
            val nextButton = Button("Next!")
            nextButton.setMinSize(110.0, 60.0)
            nextButton.setOnAction {
                val question = Question(-1, questionName.text, answerTexts[3].text, arrayListOf(answerTexts[0].text, answerTexts[1].text, answerTexts[2].text), quizID) //question object is built from the suer input
                DBService.writeOneQuestion(question) // question is saved to database

                if (questionNumber != 10) show(questionNumber + 1, user, quizID)
                else FinishedCreatingQuiz.show(user)
                stage.close()
            }
            val nextButtonHBox = HBox()
            rightVBox.children.add(nextButtonHBox)
            (0 until 9).forEach{nextButtonHBox.children.add(Label("     "))} //padding between edge and centre of the nextButtonHBox
            nextButtonHBox.children.add(nextButton)

            stage.show()
        }
    }
}
