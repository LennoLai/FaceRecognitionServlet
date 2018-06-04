package com.runoob.test;

import net.sf.json.JSONObject;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * Servlet implementation class HelloForm
 */
@WebServlet("/OutputPicture")
public class OutputPicture extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutputPicture() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = JsonReader.receivePost(request);
        String name = json.getString("name");
        System.out.print(name+"hahaha");
        String str = json.getString("picture");
        byte[] picture  = Base64.getMimeDecoder().decode(str);
        File f = new File("/Users/xiaozhu/Desktop/FaceRecognition/pictures_known/"+name+".jpg");
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(f);
            imageOutput.write(picture, 0, picture.length);
            imageOutput.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

}