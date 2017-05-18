package com.net.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class ReadWriteContentsController
{

    Logger log = Logger.getLogger(ReadWriteContentsController.class.getName());

    private final Integer default_i = 2;
    private volatile Integer i = 2;

    public void test(int i) {

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("ME №" + i);
            }
        }.start();
    }

    @RequestMapping(value = "read_write_contents", method = RequestMethod.GET)
    public String readWriteContents(@RequestParam Map<String,String> allRequestParams, HttpServletRequest request) {

        if (allRequestParams.get("foo") == null)
            return "please enter correct URI: \"" + (
                    (allRequestParams.size() == 0)
                        ? request.getRequestURL().toString() + "?foo=some_value"
                        : request.getRequestURL().toString() + "?" + request.getQueryString() + "&foo=some_value"
            ) + "\"";

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable getContents = new Runnable() {
            public void run() {

                synchronized (i) {

                    if (i <= 0) { // 2 раза
                        System.out.println("End");
                        i = default_i;

                        /**
                         * shutdown() - не будет принимать новые задачи, но завершит начатые;
                         * shutdownNow() - попытается прервать и текущие, если задачи не игнорируют прерывание
                         */
                        ses.shutdown();
                    } else {
                        i--;

                        Date date = new Date();
                        log.warn(i + " PING!" + date);

                        ReadWriteContentsController readContentsFromUrlAndSave = new ReadWriteContentsController();

                        try {

                            readContentsFromUrlAndSave.writeToFile(
                                    readContentsFromUrlAndSave.getContents("http://example.com/"),
                                    null,
                                    ("test_" + new Date().toString() + ".txt") . replace(" ", "_")
                                    , i
                            );
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }  catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        /*
         Если уменьшить до 3, то будет не успевать отработать
        */
        ses.scheduleAtFixedRate(getContents, 3, 3, TimeUnit.SECONDS);

        return "ScheduledThreadPool";

    }

    private final String defPath = File.separator + "tmp" + File.separator;

    private BufferedReader getContents(String uri) throws MalformedURLException, IOException {

        URL url = new URL(uri);
        URLConnection connection = url.openConnection();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return bufferedReader;

    }

    private void writeToFile(BufferedReader br, String defPath, String fileName, int i) throws IOException {

        if (defPath == null)
            defPath = this.defPath;

        File f = new File(defPath);
        if (!f.exists() || !f.isDirectory())
            try{

                f.mkdir();

            } catch (SecurityException e) {
                System.out.println("Error create Dir");
            }


        String inputLine;
        File file = new File(this.defPath + fileName);

        if (!file.exists())
            file.createNewFile();

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fileWriter);

        while ((inputLine = br.readLine()) != null)
            bw.write(inputLine);

        log.fatal("------------------------------------------");
        log.info("MEE 123 №" + i + " | " + defPath + fileName);
        log.fatal("------------------------------------------");

        bw.close();
        br.close();

    }

}
