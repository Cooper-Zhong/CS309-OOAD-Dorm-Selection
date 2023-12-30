package cs309_dorm_backend.service.Verification;

import java.io.File;
import java.util.List;

public interface MailService {
    void sendSimpleMail(String to, String title, String checkCode);

    void sendAttachmentsMail(String to, String title, String cotent, List<File> fileList);
}
