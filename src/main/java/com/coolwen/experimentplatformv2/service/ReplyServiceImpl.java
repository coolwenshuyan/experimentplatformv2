package com.coolwen.experimentplatformv2.service;

import com.coolwen.experimentplatformv2.dao.ReplyRepository;
import com.coolwen.experimentplatformv2.model.Reply;
import com.coolwen.experimentplatformv2.specification.SimplePageBuilder;
import com.coolwen.experimentplatformv2.specification.SimpleSortBuilder;
import com.coolwen.experimentplatformv2.specification.SimpleSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yellow
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    //    注入
    @Autowired
    private ReplyRepository replyRepository;

//    @Autowired
//    @PersistenceContext
//    private EntityManager entityManager;


    @Value("${SimplePageBuilder.pageSize}")
    int size;

    //    添加回复
    @Override
    public void add(Reply reply) {
        replyRepository.save(reply);
    }

    //    删除回复
    @Override
    public void delete(int id) {
        replyRepository.deleteById(id);
    }

    //    通过qid删回复
    @Override
    public void deleteByQid(int id) {
////        List<Integer> ids = replyRepository.findByQid(id);
////        replyRepository.deleteByQid(ids);
//        Reply reply = entityManager.find(Reply.class, id);
//        replyRepository.updateByHql("delete from Reply where id=?",id);
//        //强行抛出异常,验证声明式事务是否起作用
//        if(id>1) {
//            throw new RuntimeException();
//        }
//        entityManager.remove(reply);

        replyRepository.deleteByQid(id);

    }

    //通过qid查
    @Override
    public List<Reply> findByreplycontent(int qid) {
        return replyRepository.findByreplycontent(qid);
    }

    @Override
    public Page<Reply> findPageByQuesionId(int pageNum, int questionId) {
        Pageable pager = PageRequest.of(pageNum, size);
        Page<Reply> replyPage = replyRepository.findAll(new SimpleSpecificationBuilder<Reply>(
                "qid", "=", questionId)
                .generateSpecification(), SimplePageBuilder.generate(pageNum, SimpleSortBuilder.generateSort("dicDatetime_d")));
        return replyPage;
    }

    //通过id查回复
    @Override
    public Reply findById(int id) {
        Reply reply = new Reply();
        reply = replyRepository.findById(id);
        return reply;
    }

    //查所有
    @Override
    public List<Reply> getAll() {
        return null;
    }

    //通过qid查问题的id
    @Override
    public List<Integer> findByQid(int qid) {
        return replyRepository.findByQid(qid);
    }
}
