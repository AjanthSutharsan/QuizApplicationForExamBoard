import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*
import kotlin.collections.ArrayList

class PlayingQuizQuestion
{
    companion object
    {
        private var runningTotal = 0

        fun show(questionIndex: Int, user: User, questions: ArrayList<Question>, quizID: Int)
        {
            val question = questions[questionIndex]

            val stage = Stage()
            stage.width = 1000.0
            stage.height = 650.0
            stage.isResizable = false
            val hbox = HBox()
            hbox.style = MainMenu.BACKGROUND_COLOUR_STYLE
            hbox.padding = Insets(25.0, 15.0, 15.0, 15.0)
            val scene = Scene(hbox)
            stage.scene = scene

            val leftVBox = VBox()

            val divider = VBox()
            val dividerHBox = HBox()
            divider.children.add(dividerHBox)
            (0 until 7).forEach { dividerHBox.children.add(Label("       ")) }

            val rightVBox = VBox()
            hbox.children.addAll(leftVBox, divider, rightVBox)

            // user label which displays the userId and the name of the user
            val userLabel = TextArea("ID: ${user.userID}\nName: ${user.firstName} ${user.lastName}")
            userLabel.isEditable = false
            userLabel.setMaxSize(170.0, 45.0)
            leftVBox.children.add(userLabel)

            // Label displaying the Question Number
            val questionNumberLabel = TextField("Question #${questionIndex + 1}")
            questionNumberLabel.style = "-fx-font-family: Georgia; -fx-font-size: 20; -fx-underline: true;"
            questionNumberLabel.isEditable = false
            (0 until 8).forEach { leftVBox.children.add(Label("")) }
            leftVBox.children.add(questionNumberLabel)

            // Label displaying the actual question
            val questionTitle = TextField(question.title)
            questionTitle.isEditable = false
            leftVBox.children.add(questionTitle)

            // Guidance Text to help the user when they are answering the question
            val guidanceText = TextArea("REMEMBER: You cannot come back to a question, so be careful! \n \nTo move to the next question, either double click an answer, \n or click the answer once and press `next`.")
            guidanceText.setMaxSize(600.0, 100.0)
            guidanceText.isEditable = false
            (0 until 9).forEach { leftVBox.children.add(Label("")) }
            leftVBox.children.add(guidanceText)


            // answers + randomising
            var answerNames = ArrayList<String>() + question.correctAnswer
            question.incorrectAnswers.forEach { answerNames += it }
            Collections.shuffle(answerNames)

            fun showNextQuestion(isCorrect: Boolean)
            {
                if (isCorrect) runningTotal++
                if (questionIndex + 1 < 10) // if there is a next question to show
                {
                    show(questionIndex + 1, user, questions, quizID)
                    stage.close()
                }
                else { FinishedPlayingQuiz.show(user, quizID, runningTotal); stage.close() }
            }

            var answerCorrect = false
            var viewQuestionNext = false

            answerNames.forEach { answer
                ->
                val answerButton = Button(answer)
                answerButton.setMinSize(250.0, 70.0)
                answerButton.setOnAction {
                    answerCorrect = answer == question.correctAnswer

                    if (viewQuestionNext) showNextQuestion(answerCorrect)
                    viewQuestionNext = true
                }
                rightVBox.children.add(answerButton)
                (0 until 2).forEach { rightVBox.children.add(Label("")) }
            }

            // next button
            val nextButton = Button("Next")
            nextButton.setMinSize(110.0, 60.0)
            nextButton.setOnAction {
                if (viewQuestionNext) showNextQuestion(answerCorrect)
                else Alert(Alert.AlertType.ERROR, "You haven't submitted an answer yet").showAndWait()
            }
            val nextButtonHBox = HBox()
            rightVBox.children.add(nextButtonHBox)
            (0 until 4).forEach{nextButtonHBox.children.add(Label("     "))}
            nextButtonHBox.children.add(nextButton)

            stage.show()
        }


    }
}