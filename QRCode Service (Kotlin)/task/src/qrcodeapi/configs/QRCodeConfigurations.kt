package qrcodeapi.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.BufferedImageHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import java.awt.image.BufferedImage

@Configuration
class QRCodeConfigurations {
    @Bean
    fun bufferedImageHttpMessageConverter(): HttpMessageConverter<BufferedImage> {
        return BufferedImageHttpMessageConverter();
    }
}