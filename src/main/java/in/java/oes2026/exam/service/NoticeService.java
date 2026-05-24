package in.java.oes2026.exam.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface NoticeService {

    Map<String, String> uploadNotice(
            String title,
            MultipartFile file
    );
}