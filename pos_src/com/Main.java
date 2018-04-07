package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException
	{
		Latex latex=new Latex("E:\\dictionary\\dict_freq.txt");//���ݵĲ������ֵ��λ��
//		File file = new File("F:\\分词\\test\\普通文本\\testing_data.txt");  
//        BufferedReader in = new BufferedReader(  
//                new InputStreamReader(new FileInputStream(file), "utf-8"));//�����ֵ�
//        
        String str="今天下雨";
        String line="";
//       while((line=in.readLine())!=null)
//       {
//       	str=str+line;
//       }
		latex.Latex_str(str);
        
	}
}
