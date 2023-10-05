package com.example.demo;

import java.io.File;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mgazul by MohistMC
 * @date 2023/10/5 20:01:51
 */
@RestController
@RequestMapping("/download")
public class DownloadFile {

    @GetMapping("/{variable}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String variable) {

        try {
            File folder = new File("files", variable);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();

                if (files != null) {
                    for (File file : files) {
                        folder = file;
                    }
                } else {
                    return ResponseEntity.notFound().build();
                }
            }

            Resource resource = new UrlResource(folder.toPath().toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
