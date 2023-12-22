import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class CrimePredictor(context: Context) {
    private val interpreter: Interpreter
    private val inputSize: Int

    init {
        interpreter = Interpreter(loadModelFile(context, "model.tflite"))
        val inputTensor = interpreter.getInputTensor(0)
        inputSize = inputTensor.shape()[1]
    }

    private fun loadModelFile(context: Context, modelFileName: String): MappedByteBuffer {
        val assetManager = context.assets
        val fileDescriptor = assetManager.openFd(modelFileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun preprocessData(jumlahPenduduk: Double, jumlahCrime: Double, crimePerCapita: Double): FloatArray {
        val meanJSON = "[56938.97685749087, 59.11205846528624, 1.0930643311928245]"
        val scaleJSON = "[39000.359331530555, 175.84470812943704, 2.135285193333905]"

        val meanArray: DoubleArray = meanJSON
            .trim('[', ']')
            .split(",")
            .map { it.trim().toDouble() }
            .toDoubleArray()

        val scaleArray: DoubleArray = scaleJSON
            .trim('[', ']')
            .split(",")
            .map { it.trim().toDouble() }
            .toDoubleArray()

        val scaledJumlahPenduduk = ((jumlahPenduduk - meanArray[0]) / scaleArray[0]).toFloat()
        val scaledJumlahCrime = ((jumlahCrime - meanArray[1]) / scaleArray[1]).toFloat()
        val scaledCrimePerCapita = ((crimePerCapita - meanArray[2]) / scaleArray[2]).toFloat()

        return floatArrayOf(scaledJumlahPenduduk, scaledJumlahCrime, scaledCrimePerCapita)
    }

    fun predictCrimeRate(inputData: FloatArray): Float {
        val outputBuffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        interpreter.run(inputData, outputBuffer)
        return outputBuffer[0]
    }
}
