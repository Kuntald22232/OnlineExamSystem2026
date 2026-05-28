package in.java.oes2026.exam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig
        implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry
    ) {

        String uploadPath =
                System.getProperty("user.dir")
                        + File.separator
                        + "uploads"
                        + File.separator;

        System.out.println(
                "UPLOAD PATH: "
                        + uploadPath
        );

        registry
                .addResourceHandler(
                        "/uploads/**"
                )
                .addResourceLocations(
                        "file:///" +
                                uploadPath
                );
    }
}