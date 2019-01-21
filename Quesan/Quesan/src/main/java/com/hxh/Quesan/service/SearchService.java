package com.hxh.Quesan.service;

import com.hxh.Quesan.controller.SearchController;
import com.hxh.Quesan.model.Question;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class SearchService {
    private static final Logger logger=LoggerFactory.getLogger(SearchService.class);
    private static final String SOLR_URL="http://127.0.0.1:8983/solr/quesan";
    private HttpSolrClient client=new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE_FIELD="question_title";
    private static final String QUESTION_CONTENT_FIELD="question_content";

    public List<Question> searchQuestion(String keyword, int offset,int count,
                                         String hlPre, String hlPos) throws Exception{//hlpre,hlpos高亮的前缀后缀
        List<Question> questionList= new ArrayList<>();
        SolrQuery query=new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl",QUESTION_TITLE_FIELD+","+QUESTION_CONTENT_FIELD);
        QueryResponse response=client.query(query);
        if(response==null){
            logger.info("搜索失败！-service");
        }
        for(Map.Entry<String,Map<String,List<String>>> entry: response.getHighlighting().entrySet()){
            Question q=new Question();
            q.setId(Integer.parseInt(entry.getKey()));
            if(entry.getValue().containsKey(QUESTION_CONTENT_FIELD)){
                List<String> contentList=entry.getValue().get(QUESTION_CONTENT_FIELD);
                if(contentList.size()>0){
                    q.setContent(contentList.get(0));
                }
            }
            if(entry.getValue().containsKey(QUESTION_TITLE_FIELD)){
                List<String> titleList=entry.getValue().get(QUESTION_TITLE_FIELD);
                if(titleList.size()>0){
                    q.setTitle(titleList.get(0));
                }
            }
            questionList.add(q);
        }
        return questionList;
    }

    public boolean indexQuestion(int qid, String title, String content) throws Exception{//建索引
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id",qid);
        doc.setField(QUESTION_TITLE_FIELD,title);
        doc.setField(QUESTION_CONTENT_FIELD,content);
        UpdateResponse response=client.add(doc,1000);
        return response !=null&&response.getStatus()==0;
    }
}
