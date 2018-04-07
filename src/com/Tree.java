package com;

import java.util.HashMap;
import java.util.Map;

public class Tree {
	private String str;//
	private int frequency = -1;//频率
	private double  probability=-1;//出现的概率
	
	private int flag;//标记从跟到此节点的字符串是否是字典词
	private Map<String, Tree> children;//孩子序列
	
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}	
	public void addChild(Tree node) {  
         if (children == null) {  
             children = new HashMap<String, Tree>();  
         }  
           
         if (!children.containsKey(node.getStr())) {  
             children.put(node.getStr(), node);  
         }
     }
	
	 public Tree getChild(String ch) {  
         if (children == null || !children.containsKey(ch)) {  
             return null;  
         }  
           
         return children.get(ch);  
     }  
     
	 public Map<String,Tree> getAllChilden()
	 {
		 return this.children;
	 }
	 
     public void removeChild(String ch) {  
         if (children == null || !children.containsKey(ch)) {  
             return;  
         }  
         children.remove(ch);  
     }  
}