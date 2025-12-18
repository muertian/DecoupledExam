package cn.edu.zjut.backend.service;

import cn.edu.zjut.backend.dao.ExamPaperDAO;
import cn.edu.zjut.backend.dao.ExamPaperQuestionDAO;
import cn.edu.zjut.backend.dto.ExamPaperDTO;
import cn.edu.zjut.backend.po.ExamPaper;
import cn.edu.zjut.backend.po.ExamPaperQuestion;
import cn.edu.zjut.backend.po.ExamPaperQuestionId;
import cn.edu.zjut.backend.util.HibernateUtil;
import cn.hutool.core.bean.BeanUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("examPaperServ")
public class ExamPaperService {

    public Session getSession() {
        return HibernateUtil.getSession();
    }

    // 添加试卷（手动组卷）
    public boolean addExamPaper(ExamPaperDTO examPaperDTO) {

        ExamPaper examPaper = new ExamPaper();
        BeanUtil.copyProperties(examPaperDTO, examPaper);

        Session session = this.getSession();
        ExamPaperDAO examPaperDAO = new ExamPaperDAO();
        ExamPaperQuestionDAO questionDAO = new ExamPaperQuestionDAO();
        examPaperDAO.setSession(session);
        questionDAO.setSession(session);

        Transaction tran = null;
        try {
            tran = session.beginTransaction();

            examPaper.setComposeType("1");  // 设置为手动组件
            examPaperDAO.add(examPaper);
            if(examPaperDTO.getQuestions() != null){
                for(ExamPaperDTO.QuestionInfoDTO questionInfoDTO : examPaperDTO.getQuestions()){
                    ExamPaperQuestionId examPaperQuestionId = new ExamPaperQuestionId();
                    ExamPaperQuestion examPaperQuestion = new ExamPaperQuestion();

                    examPaperQuestionId.setPaperId(examPaper.getPaperId());
                    examPaperQuestionId.setQuestionId(questionInfoDTO.getQuestionId());
                    BeanUtil.copyProperties(questionInfoDTO, examPaperQuestion);
                    examPaperQuestion.setId(examPaperQuestionId);

                    questionDAO.add(examPaperQuestion);
                }
            }

            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("save examPaper failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    // 智能组卷
    public boolean generateExamPaper(ExamPaperDTO examPaperDTO) {
        return true;
    }

    // 查询试卷
    public List<ExamPaperDTO> queryExamPaper(Long creatorId) {

        List<ExamPaperDTO> examPaperDTOList = new ArrayList<ExamPaperDTO>();

        // DAO查询
        Session session = this.getSession();
        ExamPaperDAO examPaperDAO = new ExamPaperDAO();
        ExamPaperQuestionDAO questionDAO = new ExamPaperQuestionDAO();
        examPaperDAO.setSession(session);
        questionDAO.setSession(session);

        // 查询所有的试卷
        List<ExamPaper> examPapers = examPaperDAO.query(creatorId);
        if(examPapers != null && !examPapers.isEmpty()){
            for(ExamPaper examPaper : examPapers){

                ExamPaperDTO examPaperDTO = new ExamPaperDTO();     // 试卷DTO
                BeanUtil.copyProperties(examPaper, examPaperDTO);

                List<ExamPaperQuestion> examPaperQuestions = questionDAO.query(examPaper.getPaperId());
                for(ExamPaperQuestion examPaperQuestion : examPaperQuestions){
                    ExamPaperDTO.QuestionInfoDTO questionInfoDTO = new ExamPaperDTO.QuestionInfoDTO();
                    questionInfoDTO.setQuestionId(examPaperQuestion.getId().getQuestionId());   // 提取题目的ID
                    BeanUtil.copyProperties(examPaperQuestion, questionInfoDTO);

                    examPaperDTO.getQuestions().add(questionInfoDTO);
                }

                examPaperDTOList.add(examPaperDTO);
            }
        }

        return examPaperDTOList;
    }

    // 删除试卷
    public boolean deleteExamPaper(List<Long> paperIds) {
        Session session = this.getSession();
        ExamPaperDAO dao = new ExamPaperDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            for(Long paperId : paperIds){
                dao.delete(paperId);
            }
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("save customer failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    // 修改试卷
    public boolean updateExamPaper(ExamPaper examPaper) {
        examPaper.setUpdatedAt(new Date());

        Session session = this.getSession();
        ExamPaperDAO dao = new ExamPaperDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            dao.update(examPaper);
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("update examPaper failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    // 添加试卷题目
    public boolean addExamPaperQuestion(List<ExamPaperQuestion> examPaperQuestions) {
        Session session = this.getSession();
        ExamPaperQuestionDAO dao = new ExamPaperQuestionDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            for(ExamPaperQuestion examPaperQuestion : examPaperQuestions){
                dao.add(examPaperQuestion);
            }
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("save examPaperQuestion failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    // 删除试卷题目
    public boolean deleteExamPaperQuestion(List<ExamPaperQuestionId> examPaperQuestionIds) {
        Session session = this.getSession();
        ExamPaperQuestionDAO dao = new ExamPaperQuestionDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();

            for(ExamPaperQuestionId examPaperQuestionId : examPaperQuestionIds){
                ExamPaperQuestion examPaperQuestion = dao.query(examPaperQuestionId);
                if(examPaperQuestion != null){
                    dao.delete(examPaperQuestion);
                }else{
                    throw new Exception();
                }
            }

            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("delete examPaperQuestion failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }

    // 修改试卷题目
    public boolean updateExamPaperQuestion(ExamPaperQuestion examPaperQuestion) {
        Session session = this.getSession();
        ExamPaperQuestionDAO dao = new ExamPaperQuestionDAO();
        dao.setSession(session);
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            dao.update(examPaperQuestion);
            tran.commit();
            return true;
        } catch (Exception e) {
            System.out.println("update examPaperQuestion failed "+ e);
            if (tran != null) {
                tran.rollback();
            }
            return false;
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
