import java.io.File
import java.io.FilenameFilter
import java.io.IOException
import java.nio.channels.FileChannel
import java.io.FileInputStream
import java.io.FileOutputStream



/**
 * Created by xu on 2016/12/13.
 */
class CopyFile {

    companion object{
        @Throws(IOException::class)
        public fun copyFileUsingFileChannels(source: File, dest: File) {
            var inputChannel: FileChannel? = null
            var outputChannel: FileChannel? = null
            try {
                inputChannel = FileInputStream(source).channel
                outputChannel = FileOutputStream(dest).channel
                outputChannel!!.transferFrom(inputChannel, 0, inputChannel!!.size())
            } finally {
                inputChannel!!.close()
                outputChannel!!.close()
            }
        }
    }

}

fun main(args: Array<String>) {
    val dirPath = "D:\\Project\\Paas\\文档\\paas\\工作周报";
    val files = File(dirPath).listFiles(FilenameFilter { file, s -> s.endsWith(".doc") })
    //files.map { println(it.name) }
    val sourceFile = File(dirPath,"templet.xlsx")
    files.map {
        val destName = it.name.split(".")[0] + "工作周报.xlsx"
        val destFile = File(dirPath,destName)
        CopyFile.copyFileUsingFileChannels(sourceFile,destFile)
        println("$destName copy complete.")
    }
}