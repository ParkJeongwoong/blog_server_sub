package io.github.parkjeongwoong.application.data.service;

import io.github.parkjeongwoong.application.data.usecase.DataUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataService implements DataUsecase {

    @Value("${download.path}")
    String default_filePath;
    @Value("${db.username}")
    String dbUsername;
    @Value("${db.password}")
    String dbPassword;
    @Value("${db.database}")
    String dbName;
    @Value("${db.outputLocation}")
    String outputFile;

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, String filename) throws IOException {
        if (filename == null || filename.equals("")) {
            return ;
        }

        File dFile = getFilePath(filename);

        long fSize = dFile.length();

        if (fSize > 0) {
            String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(filename, "UTF-8");
            setResponse(response, encodedFilename, fSize);
            bufferedStream(response, dFile);
        }

    }

    @Override
    public boolean backup() throws IOException, InterruptedException {
        String command = String.format("mysqldump -u %s -p %s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);
        Process process = Runtime.getRuntime().exec(command);
//        String command = String.format("mysqldump -u %s -p %s --add-drop-table --databases %s -r %s",
//                dbUsername, dbPassword, dbName, outputFile);
//        String[] commandArray = new String[] {command, dbPassword};
//        Process process = Runtime.getRuntime().exec(commandArray);
//        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int processComplete = process.waitFor();
        return processComplete == 0;
    }

    private File getFilePath(String filename) {
        String filePath = default_filePath + filename;
        File dFile = new File(filePath);
        if (!dFile.exists()) {
            log.info("Find file again");

            filePath = System.getProperty("user.dir")
                    + File.separator + "src"
                    + File.separator + "main"
                    + File.separator + "resources"
                    + File.separator + "downloadable"
                    + File.separator + filename;
            dFile = new File(filePath);
        } else {log.info("Find file");}

        log.info("File Path : {}", filePath);
        return dFile;
    }

    private void setResponse(HttpServletResponse response, String encodedFilename, long fSize) {
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", encodedFilename);
        response.setContentLengthLong(fSize);
    }

    private void bufferedStream(HttpServletResponse response, File dFile) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(dFile));
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

        try {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
        } finally {
            in.close();
            out.close();
        }
    }

}
