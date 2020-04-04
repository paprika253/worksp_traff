package admin_panel;
/***********************************************
 * 
 * @author yaa 24.03.2017 Форматирование вывода данных в байтах
 * 
 ***********************************************/



public class Bytes {
	public static String format(final Double i) {
		Double kb = i / 1024;
		Double mb = kb / 1024;
		Double gb = mb / 1024;
		Double tb = gb / 1024;
		if (tb > 1.)
			return String.format("%.1f tb", gb);
		if (gb > 1.)
			return String.format("%.1f gb", gb);
		if (mb > 1.)
			return String.format("%.1f mb", mb);
		if (kb > 1.)
			return String.format("%.1f kb", kb);
		return String.format("%.0f b", i);
	}

	public static String format(final Long i) {
		Double j = i.doubleValue();
		return format(j);
	}

	public static String format(final Integer i) {
		Double j = i.doubleValue();
		return format(j);
	}

}