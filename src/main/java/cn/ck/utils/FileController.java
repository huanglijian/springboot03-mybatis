package cn.ck.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileController {

    /*上传文件，返回文件路径*/
    public static List<String> fileupload(MultipartFile file, String path){
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

        List<String> list=new ArrayList<>();
        list.add(fileurl);
        list.add(entryName);

        return list;
    }
}
