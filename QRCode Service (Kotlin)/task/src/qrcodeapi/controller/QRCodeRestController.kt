package qrcodeapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QRCodeRestController {

    @GetMapping("/api/health")
    fun getHealth() = ResponseEntity("Hello World", HttpStatus.OK);

    @GetMapping("/api/qrcode")
    fun getQRCode() = ResponseEntity("", HttpStatus.NOT_IMPLEMENTED);

}
