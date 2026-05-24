package in.java.oes2026.exam.service.impl;

import in.java.oes2026.exam.entity.notice.Notice;
import in.java.oes2026.exam.repository.NoticeRepository;
import in.java.oes2026.exam.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class NoticeServiceImpl
        implements NoticeService {

    @Autowired
    private NoticeRepository
            noticeRepository;

    // ✅ Better upload path
    private final String
            UPLOAD_DIR =
            System.getProperty(
                    "user.dir"
            )
                    + File.separator
                    + "uploads"
                    + File.separator
                    + "notices"
                    + File.separator;

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
                        "File is empty"
                );

                return response;
            }

            // create folder
            File directory =
                    new File(
                            UPLOAD_DIR
                    );

            if (!directory
                    .exists()) {

                directory
                        .mkdirs();
            }

            // unique file name
            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file
                            .getOriginalFilename();

            // save path
            File destinationFile =
                    new File(
                            UPLOAD_DIR
                                    + fileName
                    );

            // save file
            file.transferTo(
                    destinationFile
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
                    "pdfUrl",
                    "/uploads/notices/"
                            + fileName
            );

        } catch (
                IOException e
        ) {

            e.printStackTrace(); // ✅ show exact error

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