package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Latex {
	public int Max=999999;
	private Tree root=null;
	
	public Latex(String file) throws IOException
	{
		BuildTree(file);
	}
	
	public void Print() {
	}
	//图的节点的属性
	public class Node{
		public int id;
		public String str;//保r存节点上的字符
		public double cost;
		public String pro_str;
		public String next_str;
	}
	
	public Tree getNodeByWord(String word) {  
          if (root == null) {  
              System.out.println("发生错误！");
        	  return null;  
          }  
          Tree node = root;  
          for (int i = 0; i < word.length(); i++) {  
              String ch = word.charAt(i) + "";  
              if (node == null) {  
                  break;  
              } else {  
                  node = node.getChild(ch);  
              }  
          }  
            
          return node;  
      }  
	
	public List<Node> BuildTu(String sentence)
	{
		List<Node> Segs = new ArrayList<Node>(); 
		
		Node start_node=new Node();
		start_node.pro_str="-1";
		start_node.next_str="-1";
		start_node.id=0;
		start_node.str="start";
		Segs.add(start_node);
		int tmp_id=0;
		
		for (int i = 0; i < sentence.length(); i++) {  
            for (int j = i + 1; j <= sentence.length(); j++) {  
                String word = sentence.substring(i, j);  
                Tree tmp_node = this.getNodeByWord(word);  
                if (tmp_node == null) {  
                    break;  
                }  
                if (tmp_node.getFrequency() <= 0) {  
                    continue;  
                }  
                Node seg = new Node();  
                seg.str = word;
                seg.id=tmp_id++;
                seg.next_str = word.substring(word.length() - 1, word.length());  
                if (i == 0) {  
                    seg.pro_str="-1";  
                } else {  
                    seg.pro_str = sentence.substring(i - 1, i);  
                }  
                seg.cost = tmp_node.getProbability();  
                Segs.add(seg);  
            }  
        }

		Node end_node=new Node();
		end_node.id=tmp_id++;
		end_node.cost=1;
		end_node.pro_str=sentence.substring(sentence.length()-1,sentence.length());
		end_node.next_str="-1";
		end_node.str="end";
		Segs.add(end_node);
		return Segs;
	}
	//
	public void search_path(List<Node> Segs)
	{
		
		if(Segs.size()==0)
			return;
		int N=Segs.size();
		double COST[][]=new double[N][N];
		
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
				COST[i][j]=Max;//初始化为最大值
		}
		
		for(int i=0;i<N;i++)
		{
			Node tmp_node=Segs.get(i);
			for(int j=0;j<N;j++)
			{
				Node ttmp=Segs.get(j);
				if(tmp_node.next_str.equals(ttmp.pro_str))
				{
					COST[i][j]=ttmp.cost;
//					System.out.println(tmp_node.str+"  "+i+"->"+j+"  "+ttmp.str);
				}
			}
		}
		
		int dex_path[]=new int[N];
		dex_path[0]=-1;
		String path[]=new String[N];
		for(int i=1;i<N;i++)
		{
			dex_path[i]=0;
		}
		
		 List<Integer> S = new ArrayList<Integer>();
		 S.add(0);
		 List<Integer> T=new ArrayList<Integer>();
		 double distance[]=new double[N];
		 for(int i=1;i<N;i++)
		 {
			 T.add(i);
			 if(COST[0][i]<Max)
				 distance[i]=COST[0][i];
			 else
				 distance[i]=Max;
		 }
		 while(!T.isEmpty())
		 {
			 double min=distance[T.get(0)];
			 int k=T.get(0);
			 int kk=0;
			 for(int i=0;i<T.size();i++)
			 {
				 if(distance[T.get(i)]<min)
				 {
					 min=distance[T.get(i)];
					 k=T.get(i);
					 kk=i;
				 }
			 }
			 
			 T.remove(kk);
			 //更改权值
			 for(int i=0;i<N;i++)
			 {
				 if(distance[i]>distance[k]+COST[k][i])
				 {
					 distance[i]=distance[k]+COST[k][i];
					 dex_path[i]=k;
				 }
			 }
			 
		}
		
		String path_str="";
		
		int x=dex_path[N-1];
		while(x!=-1)
		{
			path_str=Segs.get(x).str+"/"+path_str;
			x=dex_path[x];
		}
		System.out.println(path_str.substring(6,path_str.length()));
	}
	
	//依概率的方式进行分析
	public String Latex_str(String str)
	{
		String result="";
		search_path(BuildTu(str));
		return result;
	}
	//建立字典树
	public void BuildTree(String file_name) throws IOException
	{
		root=new Tree();
		  File file = new File(file_name);  
          BufferedReader in = new BufferedReader(  
                  new InputStreamReader(new FileInputStream(file), "utf-8"));//�����ֵ�
        
          String line = in.readLine();//读入第一行，是词典词的总频数  
          
          int totalFreq = Integer.parseInt(line);
          
          while((line=in.readLine())!=null)
          {
        	  
        	  Tree node=root;
        	  String[] segs = line.split("\t");
              String word = segs[0];
              int freq=-1;
              freq = Integer.parseInt(segs[1]);//读取词频
              
        	  for(int i=0;i<word.length();i++)
        	  {
        		  char ch=word.charAt(i);
        		  Tree tmp_node=node.getChild(""+ch);
        		  if(tmp_node==null)
        		  {
        			  tmp_node=new Tree();
        			  tmp_node.setStr(ch+"");
        			  node.addChild(tmp_node);
        		  }
        		  node=tmp_node;
        	  }
        	  node.setFlag(1);
        	  node.setFrequency(freq);
        	  node.setProbability(Math.log((double)totalFreq / freq));
          }
          in.close();
	}
}
