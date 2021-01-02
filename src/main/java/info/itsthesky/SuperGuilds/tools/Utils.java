package info.itsthesky.SuperGuilds.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utils {

	public String join(List<String> strs, String s) {
		StringBuilder f = new StringBuilder();
		for (String st : strs) {
			f.append(st).append(s);
		}
		return f.toString();
	}

	public String now(String pattern) {
		if (pattern == null) {
			pattern = "yyyy/MM/dd HH:mm:ss";
		}
		if (pattern.equalsIgnoreCase("hash")) {
			pattern = "yyyy,MM,dd/HH,mm,ss";
		}
		if (pattern.equalsIgnoreCase("normal")) {
			pattern = "yyyy/MM/dd HH:mm:ss";
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

}
