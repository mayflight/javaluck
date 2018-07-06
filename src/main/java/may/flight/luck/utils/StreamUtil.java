package may.flight.luck.utils;

import may.flight.luck.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class StreamUtil {
    private static Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static String readRequestInputStream(HttpServletRequest request) {
        return readRequestInputStream(request, "utf-8");
    }

    public static String readRequestInputStream(HttpServletRequest request, String charset) {
        if (null == request
                || StringUtils.isEmpty(charset)) {
            return null;
        }

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }catch (Exception e) {
            logger.error("reader_error", e);
        }finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            }catch (IOException e) {
                logger.error("reader_close_error", e);
            }
        }
        return builder.toString();
    }

}
