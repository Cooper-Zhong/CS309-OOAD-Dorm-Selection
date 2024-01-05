package cs309_dorm_backend.service.Verification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * 通过mailtemplate.ftl文件构建邮件内容
 */
@Slf4j
public class BuildEmailContentUtils {
	private BuildEmailContentUtils() {}

	public static String getContent(String code) {
		//加载邮件html模板
		Resource resource = new ClassPathResource("mailtemplate.ftl");
		InputStream inputStream = null;
		BufferedReader fileReader = null;
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			inputStream = resource.getInputStream();
			fileReader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = fileReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			log.info("发送邮件读取模板失败{}", e);
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//替换html模板中的参数
		return MessageFormat.format(buffer.toString(), code);
	}
}

//作者：用户347531115143
//链接：https://juejin.cn/post/7315730050577367040
//来源：稀土掘金