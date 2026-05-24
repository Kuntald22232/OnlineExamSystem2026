package in.java.oes2026.exam.controller;

import in.java.oes2026.exam.repository.NoticeRepository;
import in.java.oes2026.exam.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upcoming-exam")
@CrossOrigin(origins = "*")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;
    @GetMapping("/all")
    public ResponseEntity<?> getAllNotices() {

        return ResponseEntity.ok(
                noticeRepository.findAll()
        );
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotice(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) {

        // check empty file
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "message",
                            "Please upload a file"
                    )
            );
        }

        // allow only pdf
        if (!file.getContentType()
                .equals("application/pdf")) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "message",
                            "Only PDF files are allowed"
                    )
            );
        }

        return ResponseEntity.ok(
                noticeService.uploadNotice(
                        title,
                        file
                )
        );
    }
}