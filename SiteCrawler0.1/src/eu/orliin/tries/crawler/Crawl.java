package eu.orliin.tries.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawl {

	static String baseUri;
	static Queue<String> queue = new LinkedList<String>();
	static Set<String> crawled = new HashSet<String>();
	static boolean found = false;

	public static void main(String[] args) {
		// check if two arguments
		if (args.length < 2) {
			System.out.println("Not enough arguments.");
			return;
		}
		// check if URL is correct
		if (!args[0].startsWith("http")) {
			args[0] = "http://" + args[0];
		}
		baseUri = getBaseUri(args[0]);
		if (baseUri == "") {
			System.out.println("URL doesn't match https?://([^/]+\\.[^/]+)/?");
			return;
		}

		// do the crawl
		System.out.println("Searching case INsensitive in " + args[0]
				+ " needle " + args[1]);
		crawl(args[0], args[1].toLowerCase());
	}

	private static void crawl(String url, String needle) {
		queue.offer(url);
		crawled.add(url);

		while (!queue.isEmpty()) {
			// get from queue and get text
			String currentURL = queue.poll();
			Document doc;
			try {
				doc = Jsoup.connect(currentURL).get();
			} catch (IOException e) {
				// e.printStackTrace();
				continue;
			}

			// check for needle
			if (doc.toString().toLowerCase().indexOf(needle) != -1) {
				System.out.println("Needle found in " + currentURL);
				found = true;
				break;
			}

			// find and add links to queue if not crawled and in same site
			Elements anchors = doc.select("a");
			for (Element element : anchors) {
				String absUri = element.attr("abs:href");
				// System.out.println("f:\t" + absUri);
				if (!"".equals(absUri) && getBaseUri(absUri).equals(baseUri)
						&& !crawled.contains(absUri)) {
					System.out.println("q: " + absUri);
					crawled.add(absUri);
					queue.offer(absUri);
				}
			}
		}
		if (!found)
			System.out.println("Needle not found.");
	}

	private static String getBaseUri(String url) {
		Pattern pattern = Pattern.compile("https?://([^/]+\\.[^/]+)/?");
		Matcher matcher = pattern.matcher(url);
		String result = "";
		if (matcher.find()) {
			result = matcher.group(1);
		}
		return result;
	}

}
