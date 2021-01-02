package info.itsthesky.SuperGuilds.tools;

import java.util.List;

public class Utils {

	public String join(List<String> strs) {
		StringBuilder f = new StringBuilder();
		for (String s : strs) {
			f.append(s);
		}
		return f.toString();
	}

}
