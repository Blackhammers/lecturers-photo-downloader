import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws IOException {
		String baseURL = "http://pers.uz.zgora.pl:7777/pers/";
		String baseDirectory = "C:\\\\lecturers-photo-downloader\\imagesuz\\";
		String noImageURL = (baseURL + "pers_upload.blob_display?wp_pracownik_id=-1");
		String tmp, nazwaPliku, imageURL, has_img;

		for (int x = 1; x <= 1050000; x++) {
			//if(x>11000) { x=1020000; }
			Document doc = Jsoup.connect(baseURL + "result_3.show_employee?wp_pracownik_id=" + x).get();
			Elements nazwa = doc.select("td.pracownik");
			Elements Image = doc.select("td.zdjecie > img");
			tmp = nazwa.text();
			tmp = tmp.trim();

			if (tmp != null && !tmp.isEmpty()) {
				String[] namelist = tmp.split("\\s+");

				nazwaPliku = (namelist[namelist.length - 2] + "-" + namelist[namelist.length - 1]);
				imageURL = (baseURL + Image.attr("src"));

				has_img = "Nie";

				if (!imageURL.equals(noImageURL)) {

					has_img = "Tak";

					InputStream inputStream = null;
					OutputStream outputStream = null;

					try {
						URL url = new URL(imageURL);
						inputStream = url.openStream();
						outputStream = new FileOutputStream(baseDirectory + nazwaPliku + ".jpg");

						byte[] buffer = new byte[2048];
						int length;

						while ((length = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, length);
						}

					} finally {
						try {
							inputStream.close();
							outputStream.close();

						} catch (IOException e) {
							System.out.println("Finally IOException :- " + e.getMessage());
						}
					}
				}
				System.out.println(x + ": " + nazwaPliku + " - IMG: " + has_img);
			}
		}

	}

}
