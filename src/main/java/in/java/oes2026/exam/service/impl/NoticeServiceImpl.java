package in.java.oes2026.exam.service.impl;

import in.java.oes2026.exam.entity.notice.Notice;
import in.java.oes2026.exam.repository.NoticeRepository;
import in.java.oes2026.exam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl
        implements NoticeService {

    private final NoticeRepository
            noticeRepository;

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

            if (file == null
                    || file.isEmpty()) {

                response.put(
                        "message",
                        "Please select a PDF file"
                );

                return response;
            }

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

            // ✅ ABSOLUTE PATH
            String uploadDir =
                    System.getProperty(
                            "user.dir"
                    )
                            + "/uploads/notices/";

            Path uploadPath =
                    Paths.get(
                            uploadDir
                    );

            // create folder
            if (!Files.exists(
                    uploadPath
            )) {

                Files.createDirectories(
                        uploadPath
                );
            }

            // unique filename
            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file
                            .getOriginalFilename();

            Path filePath =
                    uploadPath.resolve(
                            fileName
                    );

            // replace existing if needed
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "FILE SAVED: "
                            + filePath
            );

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
                    "https://onlineexamsystem2026.onrender.com/uploads/notices/"
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