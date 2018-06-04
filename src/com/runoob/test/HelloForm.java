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
@WebServlet("/HelloForm")
public class HelloForm extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static String attendanceTime = "Defaut";
    public static String attendanceName = "Defaut";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = JsonReader.receivePost(request);
        String time = json.getString("time");
        attendanceTime = time;
        System.out.print(time+"hahaha");
        String str = json.getString("picture");
        byte[] picture  = Base64.getMimeDecoder().decode(str);
        File f = new File("/Users/xiaozhu/Desktop/FaceRecognition/unknown.jpg");
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(f);
            imageOutput.write(picture, 0, picture.length);
            imageOutput.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        String name = null;
        try {
            List<String> cmd = new ArrayList();
            cmd.add("python");
            cmd.add("FaceRecognition.py");
            ProcessBuilder pb = new ProcessBuilder();
            pb.redirectErrorStream(true);
            File f2 = new File("/Users/xiaozhu/Desktop/FaceRecognition");
            pb.directory(f2);
            Process ps = pb.command(cmd).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            name = sb.toString();
            attendanceName = name;
            System.out.print("result:"+name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        if(name == null){
            jsonObject.put("result","0");
            jsonObject.put("name",null);
        }else {
            jsonObject.put("result","1");
            jsonObject.put("name",name);
        }
        PrintWriter out = response.getWriter();
        out.write(jsonObject.toString());
        out.close();

    }

    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

}