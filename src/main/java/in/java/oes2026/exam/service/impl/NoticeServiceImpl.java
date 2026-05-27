package in.java.oes2026.exam.service.impl;

import in.java.oes2026.exam.entity.notice.Notice;
import in.java.oes2026.exam.repository.NoticeRepository;
import in.java.oes2026.exam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl
        implements NoticeService {

    private final NoticeRepository
            noticeRepository;

    private static final String
            UPLOAD_DIR =
            "uploads/notices/";

    @Override
    public Map<String, String>
    uploadNotice(
            String title,
            MultipartFile file
    ) {

        Map<String, String>
                response =
                new HashMap<>();

        try {

            // validation
            if (file == null
                    || file.isEmpty()) {

                response.put(
                        "message",
                        "Please select a PDF file"
                );

                return response;
            }

            // only PDF allowed
            String contentType =
                    file.getContentType();

            if (contentType == null
                    || !contentType.equals(
                    "application/pdf"
            )) {

                response.put(
                        "message",
                        "Only PDF files are allowed"
                );

                return response;
            }

            // create upload folder
            Path uploadPath =
                    Paths.get(
                            UPLOAD_DIR
                    );

            if (!Files.exists(
                    uploadPath
            )) {

                Files.createDirectories(
                        uploadPath
                );
            }

            // generate unique filename
            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file
                            .getOriginalFilename();

            // save file path
            Path filePath =
                    uploadPath.resolve(
                            fileName
                    );

            // save file
            Files.copy(
                    file.getInputStream(),
                    filePath
            );

            // save DB
            Notice notice =
                    new Notice();

            notice.setTitle(
                    title
            );

            notice.setFileName(
                    fileName
            );

            noticeRepository
                    .save(notice);

            response.put(
                    "message",
                    "Notice uploaded successfully"
            );

            response.put(
                    "fileName",
                    fileName
            );

            response.put(
                    "pdfUrl",
                    "/uploads/notices/"
                            + fileName
            );

        } catch (
                IOException e
        ) {

            e.printStackTrace();

            response.put(
                    "message",
                    "Upload failed: "
                            + e.getMessage()
            );

        } catch (
                Exception e
        ) {

            e.printStackTrace();

            response.put(
                    "message",
                    "Server error: "
                            + e.getMessage()
            );
        }

        return response;
    }
}