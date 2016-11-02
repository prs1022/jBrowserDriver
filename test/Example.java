import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;

import java.io.*;

/**
 * Created by rensong.pu on 2016/10/19.
 */
public class Example {
    public static void main(String[] args) {

        // You can optionally pass a Settings object here,
        // constructed using Settings.Builder
        JBrowserDriver driver = new JBrowserDriver(Settings.builder().
                timezone(Timezone.AMERICA_NEWYORK).build());
        // This will block for the page load and any
        // associated AJAX requests
        driver.get("http://example.com");
        //添加html
        String exeJs = "$(function(){\n" +
                "var script = document.createElement(\"script\");\n" +
                "\t\tscript.type = \"text/javascript\";\n" +
                "\t\tscript.src=\"http://cdn.hcharts.cn/highcharts/highcharts.js\";\n" +
                "\t\t$(document.body).append(script);"+
                "\tvar strText = '<div id=\"container\" style=\"min-width:400px;height:400px\"></div><div class=\"message\"></div>'\n" +
                "\t$(document.body).append(strText);\n" +
                "\t})";

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(Example.class.getResourceAsStream("js")
                            , "utf-8"));
            BufferedReader br2 = new BufferedReader(
                    new InputStreamReader(Example.class.getResourceAsStream("jquery")
                            , "utf-8"));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            String line;
            String line2;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            while ((line2 = br2.readLine()) != null) {
                sb2.append(line2 + "\n");
            }
            driver.executeScript(sb2.toString(), null);
            driver.executeScript(exeJs, null);
            driver.executeScript(sb.toString(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // You can get status code unlike other Selenium drivers.
        // It blocks for AJAX requests and page loads after clicks
        // and keyboard events.
        System.out.println(driver.getStatusCode());

        // Returns the page source in its current state, including
        // any DOM updates that occurred after page load
        System.out.println(driver.getPageSource());

        // Close the browser. Allows this thread to terminate.
        driver.quit();
    }
}
