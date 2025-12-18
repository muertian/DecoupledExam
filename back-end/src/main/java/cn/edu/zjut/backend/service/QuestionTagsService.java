package cn.edu.zjut.backend.service;

import cn.edu.zjut.backend.dao.QuestionDAO;
import cn.edu.zjut.backend.dao.QuestionTagsDAO;
import cn.edu.zjut.backend.po.QuestionTags;
import cn.edu.zjut.backend.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("questionTagsServ")
public class QuestionTagsService {
    public Session getSession() {
        return HibernateUtil.getSession();
    }

    public boolean addQuestionTags(List<QuestionTags> questionTags) {
        Session session = this.getSession();
        QuestionTagsDAO dao = new QuestionTagsDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            for (QuestionTags questionTag : questionTags) {
                dao.add(questionTag);
            }
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("add questionTags failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public boolean deleteQuestionTags(List<Long> questionTagIds) {
        Session session = this.getSession();
        QuestionTagsDAO dao = new QuestionTagsDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            for (Long questionTagId : questionTagIds) {
                dao.delete(questionTagId);
            }
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("delete questionTags failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public boolean updateQuestionTags(QuestionTags questionTags) {
        Session session = this.getSession();
        QuestionTagsDAO dao = new QuestionTagsDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            dao.update(questionTags);
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("delete questionTags failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public List<QuestionTags> queryQuestionTags(Long questionId) {
        Session session = getSession();
        QuestionTagsDAO dao = new QuestionTagsDAO();
        dao.setSession(session);
        List<QuestionTags> questionTags = dao.query(questionId);
        HibernateUtil.closeSession();
        return questionTags;
    }
}
