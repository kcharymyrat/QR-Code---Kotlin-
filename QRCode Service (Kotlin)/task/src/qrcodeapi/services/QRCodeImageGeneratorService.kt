package qrcodeapi.services

import org.springframework.stereotype.Service
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.image.BufferedImage

@Service
class QRCodeImageGeneratorService {

    fun generateQRCOdeBufferedImage(
        width:Int, height: Int, contents : String, errorCorrectionLevel: ErrorCorrectionLevel
    ): BufferedImage {
        val writer = QRCodeWriter()
        val hints = mapOf(EncodeHintType.ERROR_CORRECTION to errorCorrectionLevel)
        val bitMatrix = writer.encode(
            contents, BarcodeFormat.QR_CODE, width, height, hints
            )

        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }
}