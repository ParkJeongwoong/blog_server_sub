package io.github.parkjeongwoong.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Getter
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private long categoryId;

    @Column(length = 20, nullable = false)
    private String category;

    @Column(length = 20)
    private String subCategory;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @Column(length = 8)
    private String date;

    @Column(length = 100, nullable = false)
    private String fileName;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<Image> images = new ArrayList<>();

    public Article(MultipartFile multipartFile, String category, String subCategory, long categoryId) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) throw new NoSuchFileException("파일을 첨부해주세요");
        InputStream file = multipartFile.getInputStream();
        this.fileName = multipartFile.getOriginalFilename();
        if (fileName == null) throw new NoResultException("파일명을 확인할 수 없습니다");
        this.date = fileName.substring(0,8);
        this.content = getContent(file);
        this.title = content.split("\n", 2)[0].replace("# ", "");
        this.category = category;
        if (category == null || category.length() == 0) throw  new NoResultException("카테고리를 입력해주세요");
        this.subCategory = subCategory;
        this.categoryId = categoryId;
        String fileDate = fileName.substring(0,8);
        if (!fileDate.matches("^[0-9]+$")) throw new NoResultException("파일 이름의 첫 8자리는 작성일로 만들어주세요 (ex. 20220731_파일명)");

        log.info("fileName : {}", fileName);
        log.info("title : {}", title);
    }

    public void update(String title, String content) {
        if (title == null) title = this.title;
        if (content == null) content = this.content;
        this.title = title;
        this.content = content;
    }

    public ArrayList<String> changeImageDirectory() {
        String SERVER_ADDRESS = "https://dvlprjw.kro.kr";
        Pattern imagePattern = Pattern.compile("!\\[(.*?)]\\((?!http)(.*?)\\)");
        Matcher image_in_articleData = imagePattern.matcher(content);
        ArrayList<String> imageNames = new ArrayList<>();

        while (image_in_articleData.find()) {
            String oldImageDirectory = image_in_articleData.group();
            String[] oldImageDirectoryList = oldImageDirectory.split("/");
            String newImageName = java.lang.System.currentTimeMillis() + "_" + oldImageDirectoryList[oldImageDirectoryList.length-1];
            newImageName = newImageName.substring(0, newImageName.length() - 1); // 마지막에 붙는 닫는 괄호, ) 제거
            String newImageDirectory = oldImageDirectory.replace(oldImageDirectory.split("!\\[(.*?)]\\(")[1], SERVER_ADDRESS + "/blog-api/image/" + newImageName + ")");
            imageNames.add(newImageName);
            this.content = content.replace(oldImageDirectory, newImageDirectory);
        }
        return imageNames;

        // (이미지 이름 일치여부 확인) 이 부분은 save_images로 (이거 때매 return 값을 boolean에서 string으로 변경)
//                if (!fileService.checkImageName()) {
//                    return "문서의 이미지와 업로드한 이미지가 다릅니다";
//                }
    }

    private String getContent(InputStream file) {
        InputStreamReader inputStreamReader = new InputStreamReader(file);
        Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
        return streamOfString.collect(Collectors.joining("\n"));
    }

    public int countImage() {
        Pattern imagePattern = Pattern.compile("!\\[(.*?)]\\((?!http)(.*?)\\)");
        Matcher image_in_articleData = imagePattern.matcher(this.content);

        int cnt = 0;
        while (image_in_articleData.find())
            cnt++;

        return cnt;
    }

    // Todo - 파일명 맞는지 확인
    private boolean checkImageName() {
        return true;
    }
}
