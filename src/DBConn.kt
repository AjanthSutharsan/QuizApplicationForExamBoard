import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class DBConn(private val hostUser: String = "pi", private val hostPassword: String = "enumaEli_s", private val hostURL: String = "www.musicmanager.duckdns.org", private val dbUser: String = "root", private val dbPassword: String = "yagamiLigh_t", private val dbName: String = "QuizApplication")
{
    init { connect() }
    private var connected = false
    private lateinit var session: Session
    private lateinit var config: Properties

    fun connect() //function to connect to database
    {
        if (!connected)
        {
            session = JSch().getSession(hostUser, hostURL, 22)
            session.setPassword(hostPassword)
            config = Properties()
            config.put("StrictHostKeyChecking", "no") //configuration to bypass verification step everytime connection is set up as I know that the host is safe
            session.setConfig(config)
            session.connect()
        }
        connected = true

    }

    private fun outputFromCommand(command: String): String //function which, if the command was to read data,  takes each record and saves it as separate lines using the \n notation
    {
        if (!connected) { connect() }
        val channel = session.openChannel("exec")
        val instream = BufferedReader(InputStreamReader(channel.inputStream))

        (channel as ChannelExec).setCommand(command)
        channel.connect()

        var output: String?
        var totalOutput = ""
        do
        {
            output = instream.readLine()
            totalOutput += if (output != null) { output } else { "" }
        } while (output != null)

        channel.disconnect()
        return totalOutput
    }

    fun linesFromQuery(query: String): Array<String>
    {
        val resultsAsLines = outputFromCommand("mysql --user=$dbUser --password=$dbPassword -D $dbName -e \"$query\" > foo.txt && perl -pi -e 's/\n/#/g' foo.txt && cat foo.txt").split("#")
        return if (resultsAsLines.isNotEmpty()) resultsAsLines.subList(1, resultsAsLines.size).filter { it.isNotBlank() }.toTypedArray() // first element is the column headings
        else resultsAsLines.toTypedArray() // which is empty
    }
    /*linesFromQuery takes the String produced in the outputFromCommand
     *It then sends the String to a textfile called foo.txt, where foo stands for foobar which is often used as a placeholder name in computer-related documentation
     * The perl code 's/\n/#/g' is then executed on the foo.txt textfile such that all the '\n' symbols used in the String is converted to #
     * After this, the new String is re-entered into the foo.txt textfile, and then the String is split based on the # symbols
     * This has to be done as splitting by the \n woudln't work as, in the text file, the '\n' symbol is merely part of the string*/

    fun runQuery(query: String) = outputFromCommand("mysql --user=$dbUser --password=$dbPassword -D $dbName -e \"$query\"") //runs the query on the database

    fun close() = session.disconnect() //closes the database connection

}