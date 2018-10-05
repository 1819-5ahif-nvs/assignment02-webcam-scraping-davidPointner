import com.sun.jndi.toolkit.url.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger("MyLog");
    static FileHandler fh;

    public static void main(String[] args) throws IOException, InterruptedException {
        fh = new FileHandler("D:\\StuetzRepos\\LinkWatcher\\assignment02-webcam-scraping-davidPointner\\scraperJava\\MyLog.log");
        logger.addHandler(fh);
        String url = "https://webtv.feratel.com/webtv/?cam=5132&design=v3&c0=0&c2=1&lg=en&s=0";
        while(true){
            Thread.sleep(10000);
            String source = scrapeUrl(url);
            logger.info(source);

        }




    }

    static String scrapeUrl(String url) throws IOException {
        Document doc;
        doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com")
                .get();
        Element e = doc.getElementById("fer_video");
        String source = e.select("source").first().attr("src");
        return source;

    }
}
