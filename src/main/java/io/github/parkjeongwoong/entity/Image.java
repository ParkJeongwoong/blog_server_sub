package io.github.parkjeongwoong.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;

@Getter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(length = 500, nullable = false)
    private String directory;

    @Builder
    Image(Article article, String directory) {
        this.article = article;
        this.directory = directory;
    }

    public String saveImageFile(MultipartFile imageFile, String imageName) throws IOException {
        String rootPath = setRootPath();
        File destination = new File(rootPath + File.separator + imageName);
        imageFile.transferTo(destination);
        return destination.getPath();
    }

    private String setRootPath() {
        String rootPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "article_images";
        File folder = new File(rootPath);
        boolean isDirectoryCreated = false;
        if (!folder.exists()) isDirectoryCreated = folder.mkdirs();
        if (!isDirectoryCreated) System.out.println("이미지 저장 폴더를 생성했습니다");

        return rootPath;
    }
}
