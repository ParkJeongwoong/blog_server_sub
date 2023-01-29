package io.github.parkjeongwoong.application.data.usecase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DataUsecase {
    void download(HttpServletRequest request, HttpServletResponse response, String filename) throws IOException;
    boolean backup() throws IOException, InterruptedException;
    boolean restore() throws IOException, InterruptedException;
    boolean unzip() throws IOException, InterruptedException;
}
