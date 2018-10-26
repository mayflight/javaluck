package may.flight.luck.tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    public static String getNewFileName(String prefixName, MultipartFile file) {
        if (null == file) {
            return null;
        }
        String originName = file.getOriginalFilename();
        if (StringUtils.isBlank(originName)) {
            return null;
        }
        String[] array = originName.split("\\.");
        if ( array.length != 2) {
            return originName;
        }
        if (StringUtils.isBlank(prefixName)) {
            return originName;
        }
        return prefixName + "." + array[1];
    }
}
