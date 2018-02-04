
import javafx.application.Application
import javafx.scene.image.Image
import javafx.stage.Stage
import java.io.FileInputStream


class Main : Application()
{
    override fun start(stage: Stage) {
        MainMenu.show()
        stage.show()
        stage.close()
    }

    companion object
    {
        @JvmStatic fun main(args: Array<String>)
        {
            launch(Main::class.java)
        }
        val COLLEGE_LOGO = Image(FileInputStream("C:/Users/Sutharsan/Pictures/logo.jpg"))
    }
}


