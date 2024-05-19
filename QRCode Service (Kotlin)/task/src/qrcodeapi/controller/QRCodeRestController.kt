package qrcodeapi.controller

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
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

    private val errorCorrectionMap: Map<String, ErrorCorrectionLevel> = mapOf(
        "L" to ErrorCorrectionLevel.L,
        "M" to ErrorCorrectionLevel.M,
        "Q" to ErrorCorrectionLevel.Q,
        "H" to ErrorCorrectionLevel.H
    )


    @GetMapping("/api/health")
    fun getHealth() = ResponseEntity("Hello World", HttpStatus.OK);

    @GetMapping("/api/qrcode")
    fun getQRCodeImage(
        @RequestParam("contents") contents: String,
        @RequestParam(value="size", defaultValue = "250") size: Int,
        @RequestParam(value = "correction", defaultValue = "L") correction: String,
        @RequestParam(value="type", defaultValue = "png") type: String,
    ): ResponseEntity<Any>? {

        if (contents.trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomError("Contents cannot be null or blank"))
        }

        if (size < 150 || size > 350) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomError("Image size must be between 150 and 350 pixels"))
        }

        val corr = correction.trim().uppercase()
        if (!errorCorrectionMap.containsKey(corr)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomError("Permitted error correction levels are L, M, Q, H"))
        }

        val mediaType = if (type.trim().equals("png", ignoreCase = true)) {
            MediaType.IMAGE_PNG
        } else if (type.trim().equals("jpeg", ignoreCase = true)) {
            MediaType.IMAGE_JPEG
        } else if (type.trim().equals("gif", ignoreCase = true)) {
            MediaType.IMAGE_GIF
        } else {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomError("Only png, jpeg and gif image types are supported"))
        }

        val bufferedImage: BufferedImage = qrCodeImageGeneratorService
            .generateQRCOdeBufferedImage(size, size, contents, errorCorrectionMap[corr]!!)
        return ResponseEntity
            .ok()
            .contentType(mediaType)
            .body(bufferedImage)
    }

}
