package qrcodeapi.services

import org.springframework.stereotype.Service
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage

@Service
class QRCodeImageGeneratorService {

    fun generateQRCOdeBufferedImage(
        width:Int, height: Int, contents : String
    ): BufferedImage {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, width, height)

        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }
}