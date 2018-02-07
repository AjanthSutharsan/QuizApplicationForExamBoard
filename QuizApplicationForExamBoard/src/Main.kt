import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.FileInputStream

class Main : Application()
{
    override fun start(stage: Stage)
    {
        val vbox = VBox() //main VBox which will contain all of the controls
        vbox.style = "-fx-background-color: #D1C4D6" //makes the background of the log in screen pale lavender
        vbox.padding = Insets(15.0, 15.0, 100.0, 15.0)

        val scene = Scene(vbox)
        stage.scene = scene

        stage.width = 1000.0
        stage.height = 650.0
        stage.isResizable = false

        // logo
        val logoHBox = HBox() //HBox which contains the College Logo
        val logo = ImageView(COLLEGE_LOGO)
        logo.fitHeight = 250.0
        logo.fitWidth = 250.0
        (0 until 17).forEach { logoHBox.children.add(Label("      ")) } //horizontal padding between the left edge of the logoHBox and the centre of the window
        logoHBox.children.add(logo)
        vbox.children.add(logoHBox)

        // title
        val titleHBox = HBox() //HBox which contains the College Logo
        val collegeLabel = Label("Farnborough 6th Form College Quiz Application")
        collegeLabel.style = "-fx-font-family: Georgia; -fx-font-size: 20; -fx-underline: true;" //changes the size and font of the title and makes the title underlined
        (0 until 13).forEach { titleHBox.children.add(Label("      ")) } //horizontal padding between the left edge of the titleHBox and the centre of the window
        titleHBox.children.add(collegeLabel)
        (0 until 2).forEach { vbox.children.add(Label("")) }
        vbox.children.add(titleHBox)

        // username
        val usernameHBox = HBox() //HBox which contains the 'username:' label and the textfield for the user to input their userID
        (0 until 2).forEach { vbox.children.add(Label("")) } //vertical padding between the College logo and the username textfield
        vbox.children.add(usernameHBox)
        val usernameLabel = Label("Username: \t") //
        (0 until 17).forEach { usernameHBox.children.add(Label("      ")) } //horizontal padding between the left edge of the usernameHBox and the centre of the window
        usernameHBox.children.add(usernameLabel)

        val userIDField = TextField() //textfield where user inputs their userID
        userIDField.promptText = "Please enter your college ID [E.g. 53475]"
        usernameHBox.children.add(userIDField)

        // password
        val passwordHBox = HBox() //HBox which contains the 'password:' label and the passwordfield for the user to input their password
        (0 until 2).forEach { vbox.children.add(Label("")) }
        vbox.children.add(passwordHBox)

        val passwordField = PasswordField()
        passwordField.promptText = "Please enter your college password."

        val passwordLabel = Label("Password: \t")
        (0 until 17).forEach { passwordHBox.children.add(Label("      ")) } //horizontal padding between the left edge of the passwordHBox and the centre of the window
        passwordHBox.children.addAll(passwordLabel, passwordField)

        //Log In Button
        val loginHBox = HBox() //HBox which contains the button to log the user in
        val loginButton = Button("Log In!")
        loginButton.setOnAction {
            if (DBService.userExists(userIDField.text.trim().toInt(), passwordField.text.trim())) //spaces are removed from the ends of the user input and then the user input is checked to see if the combination of userId and password is valid
            {
                MainMenu.show(DBService.getUserFromID(userIDField.text.toInt())) // if the combination is valid, the user's information would be retrieved and would be displayed on the home scren
                stage.close()
            }
            else Alert(Alert.AlertType.ERROR, "Invalid college ID and/or password.\nRemember, college ID should be 5 digits long.").showAndWait() //if the combination wasn't valid then an alert would appear to tell the user that it was invalid and give them guidance as to what the input should be
            stage.close()
        } //when the button is now clicked, the application will validate the user's input
        (0 until 22).forEach { loginHBox.children.add(Label("      ")) }
        loginHBox.children.add(loginButton)
        (0 until 2).forEach { vbox.children.add(Label("")) } //horizontal padding between the left edge of the loginHBox and the centre of the window
        vbox.children.add(loginHBox) //vertical padding between the passwordfield and the log in button

        stage.show()

    }

    companion object
    {
        @JvmStatic fun main(args: Array<String>)
        {
            launch(Main::class.java)
            DBService.close()
        }
        val COLLEGE_LOGO = Image(FileInputStream("C:/Users/Sutharsan/Pictures/logo.jpg")) //College Logo image file
    }
}