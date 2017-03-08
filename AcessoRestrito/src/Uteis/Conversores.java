package Uteis;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Conversores {

	public static Date stringToDate(String data) throws Exception {
		if (data == null || data.equals(""))
			return null;
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

	public static String getDataFormatadaPorExtenso(Date data) {
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
		String dataExtenso = formatador.format(data);
		int index = dataExtenso.indexOf(",") + 2;
		int lenght = dataExtenso.length();
		return dataExtenso.substring(index, lenght).toLowerCase();
	}

	public static String getDataNowFormatadaPorExtenso() {
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));

		String dataExtenso = formatador.format(new Date(System.currentTimeMillis()));
		int index = dataExtenso.indexOf(",") + 2;
		int lenght = dataExtenso.length();
		return dataExtenso.substring(index, lenght).toLowerCase();
	}

	public String convertStringToMd5(String valor) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("MD5");
			byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (byte b : valorMD5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return null;
		}
	}
}
