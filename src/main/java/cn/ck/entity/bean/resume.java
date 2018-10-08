package cn.ck.entity.bean;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

//简历文件bean类型
public class resume implements Serializable {
    private MultipartFile resume;

    public MultipartFile getResume() {
        return resume;
    }

    public void setResume(MultipartFile resume) {
        this.resume = resume;
    }
}
