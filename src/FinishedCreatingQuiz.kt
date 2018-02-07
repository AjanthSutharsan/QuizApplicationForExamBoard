import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class FinishedCreatingQuiz
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
            stage.scene = scene

            // College Logo
            val imageHBox = HBox()
            (0 until 15).forEach { imageHBox.children.add(Label("       ")) }
            val image = ImageView(Main.COLLEGE_LOGO)
            image.fitHeight = 250.1
            image.fitWidth = 250.1
            imageHBox.children.add(image)
            vbox.children.add(imageHBox)

            // Label explaining to user that the quiz has been made and asks user if they want to return to the home screen
            val explanatoryHBox = HBox()
            (0 until 14).forEach { explanatoryHBox.children.add(Label("       ")) }
            val explanatoryLabel = TextArea("You have finished making the quiz! \nWould you like to return to the main menu?")
            explanatoryLabel.isEditable = false
            explanatoryLabel.setMaxSize(300.0, 60.0)
            explanatoryHBox.children.add(explanatoryLabel)
            (0 until 2).forEach { vbox.children.add(Label("")) }
            vbox.children.add(explanatoryHBox)

            // HBox which contains the yes and no buttons
            val yesNoHBox = HBox()
            (0 until 9).forEach { yesNoHBox.children.add(Label("       ")) }
            (0 until 1).forEach { vbox.children.add(Label("")) }
            vbox.children.add(yesNoHBox)

            // yes button which returns user to home screen
            val yesButton = Button("Yes!")
            yesButton.setMinSize(250.0, 120.0)
            yesButton.setOnAction {
                MainMenu.show(user)
                stage.close()
            }
            yesNoHBox.children.add(yesButton)
            (0 until 1).forEach { yesNoHBox.children.add(Label("        ")) }

            // no button which exits application
            val noButton = Button("No!")
            noButton.setMinSize(250.0, 120.0)
            noButton.setOnAction { stage.close() }
            yesNoHBox.children.add(noButton)

            stage.show()

        }
    }
}