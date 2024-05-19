package qrcodeapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import qrcodeapi.services.QRCodeImageGeneratorService
import java.awt.image.BufferedImage

@RestController
class QRCodeRestController @Autowired constructor(
    private val qrCodeImageGeneratorService: QRCodeImageGeneratorService
) {


    @GetMapping("/api/health")
    fun getHealth() = ResponseEntity("Hello World", HttpStatus.OK);

    @GetMapping("/api/qrcode")
    fun getQRCode(): ResponseEntity<BufferedImage> {
        val bufferedImage: BufferedImage = qrCodeImageGeneratorService
            .generateQRCOdeBufferedImage(250, 250)
        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(bufferedImage)
    }

}
