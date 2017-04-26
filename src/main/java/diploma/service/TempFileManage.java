package diploma.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by IO on 23.04.2017.
 */
public class TempFileManage {
    public static File createTempFile(MultipartFile multipartFile){
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tmp", ".waw", new File(System.getProperty("user.dir") + "\\cache\\"));
            multipartFile.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static void deleteTempFile(File file){
        if (file.delete()){
            System.out.println("Deleted");
        }else {
            file.deleteOnExit();
        }
    }
}
