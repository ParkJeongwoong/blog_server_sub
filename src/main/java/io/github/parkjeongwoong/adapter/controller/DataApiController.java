package io.github.parkjeongwoong.adapter.controller;

import io.github.parkjeongwoong.application.data.service.ServerSynchronizingService;
import io.github.parkjeongwoong.application.data.usecase.DataUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("data-api")
public class DataApiController {

    private final DataUsecase dataUsecase;

    @GetMapping("/download/{filename}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
        dataUsecase.download(request, response, filename);
    }

    // TEST
    @GetMapping(value = "/backup")
    public boolean backuptest() throws IOException, InterruptedException {return dataUsecase.backup();}
    @GetMapping(value = "/restore")
    public boolean restoretest() throws IOException, InterruptedException {return dataUsecase.restore();}
    private final ServerSynchronizingService synchronizingService;
    @GetMapping(value = "/sync")
    public void synctest() {synchronizingService.getVisitorTableFromMainServer();}
    @GetMapping(value = "/unzip")
    public boolean unziptest() throws IOException, InterruptedException {return dataUsecase.unzip();}

}
