package cn.ck.controller;

import cn.ck.utils.ConstCofig;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        System.out.println("fileName "+fileName);
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

    /**
     * 文件下载
     * @param fileName 文件的名字
     */
    //这是一个下载的函数
    @RequestMapping(value = "/down",method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
        System.out.println("in--down---and--filename--is:"+fileName);
        try {
            //存放地址
//            String realPath = "F:\\down";
            String realPath = "E:\\ChuangKeFile\\Project\\file";
            //获得服务器端某个文件的完整路径
            String fullPath = realPath + File.separator + fileName;
            //设置响应
            response.setContentType("application/force-download");
            //设置响应头信息
            response.setHeader("Content-Disposition", "attachment;fileName="+fileName);// 设置文件名
            //文件名有中文时设置编码
            response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1"));

            File downloadFile = new File(fullPath);
            FileInputStream inputStream = new FileInputStream(downloadFile);
            OutputStream outputStream =  response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
