import com.sun.jndi.toolkit.url.Uri;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger("MyLog");
    static FileHandler fh;
    static String url = "https://webtv.feratel.com/webtv/?cam=5132&design=v3&c0=0&c2=1&lg=en&s=0";

    public static void main(String[] args) throws IOException, InterruptedException {
        fh = new FileHandler("D:\\StuetzRepos\\LinkWatcher\\assignment02-webcam-scraping-davidPointner\\scraperJava\\MyLog.log");
        logger.addHandler(fh);

        createHttpServer();
//        while(true){
//            Thread.sleep(10000);
//            String source = scrapeUrl(url);
//            logger.info(source);
//
//        }




    }

    static void createHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(1337), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = String.format(
                    "<video controls autoplay><source src=\"%s\" type=\"video/mp4\"></video> ", scrapeUrl(url));
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html");
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
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
