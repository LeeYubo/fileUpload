package com.lyb.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
public class CommonController {

	@RequestMapping(value="/fileupload", method=RequestMethod.GET)
	public String showPage(){
		System.out.println("前往文件上传界面...");
		return "upload";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(){
		System.out.println("进入文件上传首页...");
		return "index";
	}
	
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public String uploadByStream(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request){
		System.out.println("开始【流式方式】文件上传...");
		Long startTime = System.currentTimeMillis();
        for(int i = 0;i<files.length;i++){
        	System.out.println("待上传文件名：fileName = " + files[i].getOriginalFilename());  
            if(!files[i].isEmpty()){
                try {
                    //拿到输出流，同时重命名上传的文件
                	String newFileName = "H://fileupload//" + new Date().getTime()+files[i].getOriginalFilename();
                	System.out.println("新文件名：newFileName = "+newFileName);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFileName));
                    //拿到上传文件的输入流
                    BufferedInputStream bis = new BufferedInputStream(files[i].getInputStream());
                    //以写字节的方式写文件  
                    byte[] byteArray = new byte[1024];//每次读取1024字节 in.read()代表每次只读一个字节，in.read(byte[])表示每次读取指定长度的字节
                    while(bis.read(byteArray) != -1){  
                    	bos.write(byteArray);
                    }
                    bos.flush();
                    bis.close();
                    bos.close();
                    //将新的文件名写到请求参数中，在成功页面获取并展示
                    request.setAttribute("newfilename", newFileName);
                } catch (Exception e) {  
                    e.printStackTrace();  
                    System.out.println("上传出错");  
                }
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("完成文件上传使用时间 = "+String.valueOf(endTime - startTime)+"ms");
        return "success";
    }
	
	@RequestMapping(value="/upload2", method=RequestMethod.POST)
	public String uploadByTransfer(@RequestParam("file") CommonsMultipartFile[] files,Model model){
		System.out.println("开始【file.Transto】文件上传...");
		Long startTime = System.currentTimeMillis();
        for(int i = 0;i<files.length;i++){
        	System.out.println("待上传文件名：fileName = " + files[i].getOriginalFilename());
            if(!files[i].isEmpty()){
                try {
                	String newFileName = "H://fileupload//" + new Date().getTime()+files[i].getOriginalFilename();
                	File newFile = new File(newFileName);
                	System.out.println("新文件名：newFileName = "+newFileName);
                	files[i].transferTo(newFile);
                	//将新的文件名写到请求参数中，在成功页面获取并展示
                    model.addAttribute("newfilename", newFileName);
                } catch (Exception e) {  
                    e.printStackTrace();  
                    System.out.println("上传出错");  
                }
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("完成文件上传使用时间 = "+String.valueOf(endTime - startTime)+"ms");
        return "success";
	}
	
	
	@RequestMapping("/upload3")
	public String springUpload(HttpServletRequest request, Model model) 
			throws IllegalStateException, IOException{
		System.out.println("spring内部包装类实现文件上传...");
		Long startTime = System.currentTimeMillis();
		//将当前上下文初始化给-多部分解析器
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//检查form中是否有enctype="multipart/form-data"
		if(commonsMultipartResolver.isMultipart(request)){
			//将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator<String> iter=multiRequest.getFileNames();
            System.out.println("iter = "+iter);
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                System.out.println("待上传文件名：fileName = " + file.getOriginalFilename());
                if(file!=null)
                {
                	String newFileName = "H://fileupload//" + new Date().getTime()+file.getOriginalFilename();
                	File newFile = new File(newFileName);
                    //上传
                    file.transferTo(newFile);
                    model.addAttribute("newfilename",newFileName);
                } 
            }
		}
		Long endTime = System.currentTimeMillis();
        System.out.println("完成文件上传使用时间 = "+String.valueOf(endTime - startTime)+"ms");
		return "success";
	}
}
