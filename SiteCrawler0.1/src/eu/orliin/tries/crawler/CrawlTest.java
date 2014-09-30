package eu.orliin.tries.crawler;

import java.net.*;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlTest {
	public static String getText(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}

	public static void main(String[] args) throws Exception {
//		String content = Crawl.getText(args[0]);
//		content = Crawl.getText("abv.bg");
//		System.out.println(content);
		
//		String root = "http://orliin.eu/test/index.html?abv=5";	//ok
//		String root = "http://orliin.eu/test/index.html";		//ok
//		String root = "http://orliin.eu/test/";					//ok
//		String root = "http://orliin.eu/test";					//--bad--
//		String root = "http://orliin.eu/";
//		String root = "http://orliin.eu/test/index.html?abv=5/";	//ok
//		String root = "http://orliin.eu/test/index.html/";		//
//		String root = "http://orliin.eu/test/../";					//
		String root = "http://ebusiness.free.bg";
		
//		if(root.charAt(root.length()-1) != '/') root = root+"/";
		
//		String rootUrl = new URL(root).toString();
//		System.out.println(rootUrl);
		
		Document doc = Jsoup.connect(root).get();
		System.out.println(doc);
		Elements anchors = doc.select("a");
		for (Element element : anchors) {
//			String uri = element.attr("href");
//			System.out.println(new URL(new URL(root), uri));
			String absUri = element.attr("href");
			System.out.println(new URL(new URL(root), absUri));
			
		}
	}
}