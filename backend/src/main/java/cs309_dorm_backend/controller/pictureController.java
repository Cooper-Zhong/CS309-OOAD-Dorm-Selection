package cs309_dorm_backend.controller;

import cs309_dorm_backend.config.MyException;
import cs309_dorm_backend.dto.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/picture")
@Slf4j
public class pictureController {


//    static String picturePath = "/Users/cooperz/SUSTech/2023_Fall/CS309_OOAD/CS309-OOAD-Dorm-Selection/backend/src/main/resources/pictures/";
    static String picturePath = "/home/cooper/cs309ooad/src/main/resources/pictures/";

    // Handling OPTIONS request explicitly
    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
                .build();
    }

    @PostMapping("/upload")
    public GlobalResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new GlobalResponse<>(1, "file is empty", null);
        }
        try {
            // 获取文件的原始名称
            String originalFilename = file.getOriginalFilename();
            File dest = new File(picturePath + originalFilename);
            file.transferTo(dest);
            log.info("upload success " + originalFilename);

            return new GlobalResponse<>(0, "upload success ", originalFilename);
        } catch (IOException e) {
            log.error(e.toString());
            return new GlobalResponse<>(2, "upload failed", null);
        }
    }

    @GetMapping("/download/{filename}")
    public GlobalResponse<String> downloadBase64(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(picturePath).resolve(filename);
            if (Files.exists(imagePath)) {
                byte[] imageBytes = Files.readAllBytes(imagePath);
                String base64Image = Base64.encodeBase64String(imageBytes);
                log.info("download success" + filename);
                return new GlobalResponse<>(0, "download success ", base64Image);
            } else {
                log.warn("file not found " + filename +", use default png");
                Path defaultImagePath = Paths.get(picturePath).resolve("default.png");
                byte[] imageBytes = Files.readAllBytes(defaultImagePath);
                String base64Image = Base64.encodeBase64String(imageBytes);
                return new GlobalResponse<>(0, "file not found, use default", base64Image);
            }
        } catch (IOException e) {
            log.error(e.toString());
            return new GlobalResponse<>(2, "download failed", null);
        }
    }

//    @GetMapping("/download/{filename}")
//    public void download(@PathVariable String filename, HttpServletResponse response) {
//        try {
//            //输入流，通过输入流读取文件内容
//            FileInputStream fileInputStream = new FileInputStream(picturePath + filename);
//            //输出流，通过输出流将文件写回浏览器
//            ServletOutputStream outputStream = response.getOutputStream();
////            response.setContentType(MediaType.IMAGE_PNG_VALUE);
////            response.setContentType("application/octet-stream");
////            response.setHeader("Content-Disposition", "inline; filename=" + filename);
//            int len = 0;
//            byte[] bytes = new byte[1024];
//            byte[] data = Files.readAllBytes(Path.of(picturePath + filename));
////            while ((len = fileInputStream.read(bytes)) != -1) {
////                outputStream.write(bytes, 0, len);
////            }
//            outputStream.write(data);
//            outputStream.flush();
//        } catch (Exception e) {
//            System.err.println(filename + " not found");
//        } finally {
//            //关闭流
//            try {
//                response.getOutputStream().close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
