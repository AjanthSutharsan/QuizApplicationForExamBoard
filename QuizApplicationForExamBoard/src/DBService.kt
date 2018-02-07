import java.util.*

class DBService
{
    companion object
    {
        private val connection = DBConn()

        fun close() = connection.close()


        fun allQuizNames() : ArrayList<Quiz>
        {
            val quizzes = ArrayList<Quiz>()
            connection.linesFromQuery("SELECT * FROM Quiz;").forEach { quizzes += Quiz(it.split("\t")[0].toInt(), it.split("\t")[1]) } //the linesFromQuery command would return each record in the Quiz table as a separate element and then each record is added as an element to the quizzes Arraylist
            Collections.sort(quizzes) { a: Quiz, b: Quiz -> a.quizTitle.compareTo(b.quizTitle) }
            return quizzes
        }

        fun questionsFromQuiz(quiz: Quiz): ArrayList<Question>
        {
            val questions = ArrayList<Question>() //the Arraylist of Question objects which the records from the Question table
            val allQuestionLines = connection.linesFromQuery("SELECT * FROM Question;")
            var i = 0; while (questions.size != 10) //this ensures that only 10 questions will be added to the arraylist
        {
        val questionLineSplit = allQuestionLines[i].split("\t") //splits each record from the Question table into its constituent entries
            if (questionLineSplit[6].toInt() == quiz.quizID) //checks if the id of the quiz is the one the user wants
            {
                questions += Question(
                        questionLineSplit[0].toInt(), //the questionID
                        questionLineSplit[1], //question
                        questionLineSplit[2], //correct answer
                        questionLineSplit.subList(3, 6), //the three incorrect answers
                        questionLineSplit[6].toInt() //the quizID
                )
            }
            i++
        }
            return questions
        }

        fun saveScoreToDatabase(user: User, quizID: Int, finalScore: Int)
        {
            val query = "INSERT INTO Scores (userID, quizID, score) VALUES(${user.userID}, $quizID, $finalScore);" //SQL query to add a score, a userID, and a quizID
            connection.runQuery(query) //the query is executed
        }

        fun writeQuizTitle(quiz: Quiz) = connection.runQuery("INSERT INTO Quiz (quizTitle) VALUES ('${quiz.quizTitle}');") //the query is executed

        fun writeOneQuestion(question: Question)
        {
            var query = "INSERT INTO Question (question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, quizID) VALUES('${question.title.trim()}', '${question.correctAnswer.trim()}', "
            question.incorrectAnswers.forEach { answer -> query += "'${answer.trim()}', " }
            query += question.quizID.toString() + ");" //the query to insert the question into the database is created, the first line for the question title and correct answer, the second line for the incorrect answers, and the third line for the quizID which is generated from the nextQuizID method

            connection.runQuery(query) //the query is executed
        }

        fun nextQuizID(): Int
        {
            val quizIDs = connection.linesFromQuery("SELECT quizID FROM Quiz;") //all the quizId values are saved as Strings in an ArrayList
            if (quizIDs.isNotEmpty()) return quizIDs.last().toInt() //if there are quizID values in the Quiz table, the first quizID which is free is returned
            return 1 //if there are no quizID values in the quiz table, a value of 1 is returned as it's the first quizID
        }
    }
}