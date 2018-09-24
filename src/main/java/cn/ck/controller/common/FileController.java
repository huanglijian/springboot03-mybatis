package cn.ck.controller.common;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileController {

    /*上传文件，返回文件路径*/
    public String fileupload(MultipartFile file,String path){
//      String path = "G:/ck/project/img";

        String entryName = file.getOriginalFilename();
        String indexName=entryName.substring(entryName.lastIndexOf("."));
        String fileName = String.valueOf(new Date().getTime());

        File targetFile = new File(path+"/"+fileName+indexName);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileurl=path+"/"+fileName+indexName;

        return fileurl;
    }
}
