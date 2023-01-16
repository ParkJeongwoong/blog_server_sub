package io.github.parkjeongwoong.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
@Entity
public class Visitor extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String url;

    @Column(length = 15)
    private String ip;

    @Column(length = 100)
    private String lastPage;

    @Column(nullable = false)
    private boolean justVisited;

    @Builder
    public Visitor(String url, String lastPage) {
        this.url = url;
        this.lastPage = lastPage;
    }

    public void setData() {
        setIp();
        setJustVisited();
    }

    private void setIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        System.out.println("X-FORWARDED-FOR : " + ip);
        if (ip == null) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr : " + ip);
        }
        this.ip = ip;
    }

    private void setJustVisited() {
        this.justVisited = this.lastPage == null;
    }
}
