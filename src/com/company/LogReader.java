package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class LogReader {

    private final String URI = "C:\\logs";
    private final File directory = new File(URI);

    void init() throws IOException {
        if (!this.directory.exists()) {
            System.out.println(this.URI + " does not exist");
        } else {
            listFilesForFolder();
        }
    }

    void listFilesForFolder() throws IOException {
        File[] files = this.directory.listFiles();

        if (files.length == 0) {
            System.out.println(this.URI + " is empty");
            System.exit(0);
        }

        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

        FileInputStream inputStream = null;
        Scanner sc = null;

        for (File file: files) {

            if (file.isDirectory()) {
                continue;
            }

            long startTimer = System.nanoTime();

            System.out.println(file.getName());

            List<Date> dates = new ArrayList<>();
            LogsSeverity ls = new LogsSeverity();

            LinkedHashSet<String> libraries = new LinkedHashSet<>();


            try {
                inputStream = new FileInputStream(file);
                sc = new Scanner(inputStream, StandardCharsets.UTF_8);

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();

                    String[] arr = line.split("\\s+", 5);

                    Date date = null;
                    try {
                        String fullDate = arr[0] + arr[1];
                        date = new SimpleDateFormat("yyyy-MM-ddkk:mm:ss,SSS", Locale.ENGLISH).parse(fullDate);

                    } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                         // handle exception
                    }

                    if (date != null) {
                        dates.add(date);
                    }
                    dates.sort(Date::compareTo);

                    if (arr.length > 3) {
                        ls.count(arr[2]);
                        char test = arr[3].charAt(0);
                        if (test == '[') {
                            libraries.add(arr[3]);
                        }
                    }
                }

                System.out.println("Logs from " + dates.get(0) + " to " + dates.get(dates.size() - 1));
                System.out.println(ls);
                ls.logRatio();
                System.out.println("Unique libraries: " + libraries.size());


            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (sc != null) {
                    sc.close();
                }
            }

            long stopTimer = System.nanoTime();
            System.out.println("File read in: " + TimeUnit.MILLISECONDS.convert((stopTimer - startTimer), TimeUnit.NANOSECONDS) + " ms");
            System.out.println("-----------------------------------------------");

        }

    }

}
