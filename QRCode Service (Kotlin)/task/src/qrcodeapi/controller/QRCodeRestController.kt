package qrcodeapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import qrcodeapi.custom_error.CustomError
import qrcodeapi.services.QRCodeImageGeneratorService
import java.awt.image.BufferedImage

@RestController
class QRCodeRestController @Autowired constructor(
    private val qrCodeImageGeneratorService: QRCodeImageGeneratorService
) {


    @GetMapping("/api/health")
    fun getHealth() = ResponseEntity("Hello World", HttpStatus.OK);

    @GetMapping("/api/qrcode")
    fun getQRCodeImage(
        @RequestParam(value="size", defaultValue = "250") size: Int,
        @RequestParam(value="type", defaultValue = "png") type: String
    ): ResponseEntity<out Any>? {

        if (size < 150 || size > 350) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomError("Image size must be between 150 and 350 pixels"))
        }
        val mediaType = if (type.trim { it <= ' ' }.equals("png", ignoreCase = true)) {
            MediaType.IMAGE_PNG
        } else if (type.trim { it <= ' ' }.equals("jpeg", ignoreCase = true)) {
            MediaType.IMAGE_JPEG
        } else if (type.trim { it <= ' ' }.equals("gif", ignoreCase = true)) {
            MediaType.IMAGE_GIF
        } else {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body<Any>(CustomError("Only png, jpeg and gif image types are supported"))
        }

        val bufferedImage: BufferedImage = qrCodeImageGeneratorService
            .generateQRCOdeBufferedImage(size, size)
        return ResponseEntity
            .ok()
            .contentType(mediaType)
            .body(bufferedImage)
    }

}
