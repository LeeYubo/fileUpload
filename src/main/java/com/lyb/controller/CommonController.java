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
		System.out.println("ǰ���ļ��ϴ�����...");
		return "upload";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(){
		System.out.println("�����ļ��ϴ���ҳ...");
		return "index";
	}
	
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public String uploadByStream(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request){
		System.out.println("��ʼ����ʽ��ʽ���ļ��ϴ�...");
		Long startTime = System.currentTimeMillis();
        for(int i = 0;i<files.length;i++){
        	System.out.println("���ϴ��ļ�����fileName = " + files[i].getOriginalFilename());  
            if(!files[i].isEmpty()){
                try {
                    //�õ��������ͬʱ�������ϴ����ļ�
                	String newFileName = "H://fileupload//" + new Date().getTime()+files[i].getOriginalFilename();
                	System.out.println("���ļ�����newFileName = "+newFileName);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFileName));
                    //�õ��ϴ��ļ���������
                    BufferedInputStream bis = new BufferedInputStream(files[i].getInputStream());
                    //��д�ֽڵķ�ʽд�ļ�  
                    byte[] byteArray = new byte[1024];//ÿ�ζ�ȡ1024�ֽ� in.read()����ÿ��ֻ��һ���ֽڣ�in.read(byte[])��ʾÿ�ζ�ȡָ�����ȵ��ֽ�
                    while(bis.read(byteArray) != -1){  
                    	bos.write(byteArray);
                    }
                    bos.flush();
                    bis.close();
                    bos.close();
                    //���µ��ļ���д����������У��ڳɹ�ҳ���ȡ��չʾ
                    request.setAttribute("newfilename", newFileName);
                } catch (Exception e) {  
                    e.printStackTrace();  
                    System.out.println("�ϴ�����");  
                }
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("����ļ��ϴ�ʹ��ʱ�� = "+String.valueOf(endTime - startTime)+"ms");
        return "success";
    }
	
	@RequestMapping(value="/upload2", method=RequestMethod.POST)
	public String uploadByTransfer(@RequestParam("file") CommonsMultipartFile[] files,Model model){
		System.out.println("��ʼ��file.Transto���ļ��ϴ�...");
		Long startTime = System.currentTimeMillis();
        for(int i = 0;i<files.length;i++){
        	System.out.println("���ϴ��ļ�����fileName = " + files[i].getOriginalFilename());
            if(!files[i].isEmpty()){
                try {
                	String newFileName = "H://fileupload//" + new Date().getTime()+files[i].getOriginalFilename();
                	File newFile = new File(newFileName);
                	System.out.println("���ļ�����newFileName = "+newFileName);
                	files[i].transferTo(newFile);
                	//���µ��ļ���д����������У��ڳɹ�ҳ���ȡ��չʾ
                    model.addAttribute("newfilename", newFileName);
                } catch (Exception e) {  
                    e.printStackTrace();  
                    System.out.println("�ϴ�����");  
                }
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("����ļ��ϴ�ʹ��ʱ�� = "+String.valueOf(endTime - startTime)+"ms");
        return "success";
	}
	
	
	@RequestMapping("/upload3")
	public String springUpload(HttpServletRequest request, Model model) 
			throws IllegalStateException, IOException{
		System.out.println("spring�ڲ���װ��ʵ���ļ��ϴ�...");
		Long startTime = System.currentTimeMillis();
		//����ǰ�����ĳ�ʼ����-�ಿ�ֽ�����
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//���form���Ƿ���enctype="multipart/form-data"
		if(commonsMultipartResolver.isMultipart(request)){
			//��request��ɶಿ��request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //��ȡmultiRequest �����е��ļ���
            Iterator<String> iter=multiRequest.getFileNames();
            System.out.println("iter = "+iter);
            while(iter.hasNext())
            {
                //һ�α��������ļ�
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                System.out.println("���ϴ��ļ�����fileName = " + file.getOriginalFilename());
                if(file!=null)
                {
                	String newFileName = "H://fileupload//" + new Date().getTime()+file.getOriginalFilename();
                	File newFile = new File(newFileName);
                    //�ϴ�
                    file.transferTo(newFile);
                    model.addAttribute("newfilename",newFileName);
                } 
            }
		}
		Long endTime = System.currentTimeMillis();
        System.out.println("����ļ��ϴ�ʹ��ʱ�� = "+String.valueOf(endTime - startTime)+"ms");
		return "success";
	}
}
