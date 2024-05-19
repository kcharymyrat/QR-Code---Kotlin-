package qrcodeapi.services

import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.image.BufferedImage

@Service
class QRCodeImageGeneratorService {

    fun generateQRCOdeBufferedImage(width:Int, height: Int): BufferedImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()

        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, width, height)
        return image
    }
}