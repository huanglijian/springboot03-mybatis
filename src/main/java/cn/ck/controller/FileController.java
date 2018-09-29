package cn.ck.controller;

import cn.ck.utils.ConstCofig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("file")
public class FileController {

    /**
     * 加载图片
     * 以字节数组的形式
     * 用response输出到页面
     * 假设数据库的是文件的文件名和格式如"myImg.jgp"
     * 若需要其他存储格式，自己定义处理方法，可以拿到File就可以使用responseFile输出
     * @param fileName 要加载的图片文件名
     * @param response
     */
    @RequestMapping("/showImg/{fileName}")
    public void showPicture(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //将文件名分割成 文件名 和 格式
        //“ . " 需要用两次转义
        String [] path = fileName.split("\\.");
        //获取服务器中的文件
        File imgFile = new File(ConstCofig.RootPath + ConstCofig.ImgPath + path[0] + "." + path[1]);
        //输出到页面
        responseFile(response, imgFile);
    }

    /**
     * 响应输出图片文件
     * 文件转换为字节数组
     * @param response
     * @param imgFile
     */
    public static void responseFile(HttpServletResponse response, File imgFile) {
        try(InputStream is = new FileInputStream(imgFile); OutputStream os = response.getOutputStream()){
            byte [] buffer = new byte[(int)imgFile.length()]; // 图片文件流缓存池
            while(is.read(buffer) != -1){
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param file 文件
     * @param path 文件绝对路径：path=E:\ChuangKeFile;传入的路径要以这个为文件头
     * @return fileurl：数据库路径文件名、entryName：文件原本文件名
     */
    public static List<String> fileupload(MultipartFile file, String path){
        String entryName = file.getOriginalFilename();
        String indexName=entryName.substring(entryName.lastIndexOf("."));
        String fileName = String.valueOf(UUID.randomUUID());

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
        String fileurl=fileName+indexName;

        List<String> list=new ArrayList<>();
        list.add(fileurl);
        list.add(entryName);

        return list;
    }
}
