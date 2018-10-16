package com.hxh.Quesan.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@Service
public class SensivtiveService implements InitializingBean {
    private static final Logger logger=LoggerFactory.getLogger(SensivtiveService.class);
    private TrieNode rootnode=new TrieNode();
    //导入敏感词文本
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Sensitivewords");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                addWord(line.trim());//去前后空格
            }
            inputStreamReader.close();//关闭输入流
        }catch(Exception e){
            logger.error("读取失败"+e.getMessage());
        }
    }

    private class TrieNode{
        private boolean end=false;
        private Map<Character,TrieNode> subNodes=new HashMap<Character, TrieNode>();

        boolean isEnd() {
            return end;
        }
        void setEnd(boolean end) {
            this.end = end;
        }
        void addSubNode(Character key,TrieNode node){
            subNodes.put(key, node);
        }
        TrieNode getSubNode(char key){
            return subNodes.get(key);
        }
        public int getSubNodeCount() {
            return subNodes.size();
        }
    }
    //建树
    public void addWord(String line){
        TrieNode current=rootnode;
        for(int i=0;i<line.length();i++){
            Character ch=line.charAt(i);
            TrieNode node=current.getSubNode(ch);
            if(node==null){
                node=new TrieNode();
                current.addSubNode(ch,node);
            }
            current=node;
            if(i==line.length()-1){
                current.setEnd(true);
            }
        }
    }
    //过滤非法词
    private boolean isSymbol(char c){
        int ic=(int)c;
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic<0x2E80||ic>0x9fff);
    }
    //过滤
    public String filter(String text){
        if(StringUtils.isBlank("text")){
            return text;
        }
        String replace="***";
        StringBuilder printtext=new StringBuilder();
        TrieNode currentNode=rootnode;
        int currentChar=0;
        int StartChar=0;
        while(currentChar<text.length()) {
            char temp = text.charAt(currentChar);
            if(isSymbol(temp)){
                if(currentNode==rootnode){
                    StartChar++;
                    printtext.append(temp);
                }
                currentChar++;
                continue;
            }
            currentNode=currentNode.getSubNode(temp);
            if (currentNode == null) {
                printtext.append(text.charAt(StartChar));
                currentChar = StartChar + 1;
                StartChar= currentChar;
                currentNode=rootnode;
            }else if (currentNode.isEnd()) {
                printtext.append(replace);
                currentChar++;
                StartChar=currentChar;
                currentNode=rootnode;
            }else{
                currentChar++;
            }

        }
        printtext.append(text.substring(StartChar));
        return printtext.toString();
    }
/*public static void main(String args[]){
        SensivtiveService s=new SensivtiveService();
        s.addWord("色情");
    System.out.println(s.filter("你 好 色 情啊！"));
}*/

}
