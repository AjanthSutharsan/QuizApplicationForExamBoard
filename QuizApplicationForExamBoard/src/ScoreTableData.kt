import javafx.beans.property.SimpleStringProperty

class ScoreTableData (private val userID: Int, private val firstName: String, private val lastName: String, private val score: Int)
{
    private val _userID = SimpleStringProperty(userID.toString()) //SimpleStringProperty required in order to work with tableview
    private val _firstName = SimpleStringProperty(firstName)
    private val _lastName = SimpleStringProperty(lastName)
    private val _score = SimpleStringProperty(score.toString())

    fun getUserID() = _userID.get() //the getter methods have to be specified myself in order to use them for the tableview
    fun getFirstName() = _firstName.get()
    fun getLastName() = _lastName.get()
    fun getScore() = _score.get()
}