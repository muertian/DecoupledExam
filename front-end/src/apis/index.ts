import loginAPI from "./Server/loginAPI";
import getQuestionTypeAPI from "./Server/getQuestionTypeAPI";
import getSubjectAPI from "./Server/getSubjectAPI";
import getQuestionsAPI from "./Server/getQuestionsAPI";
import loginFaceAPI from "./Server/loginFaceAPI";
import addQuestionsAPI from "./Server/addQuestionsAPI";
import importQuestionsAPI from "./Server/importQuestionsAPI";
import registerAPI  from "./Server/registerAPI";
import teacherRegisterAPI from "./Server/teacherRegisterAPI";
import userAPI from "./Server/userAPI";
import {
  getSystemOperationLogsAPI,
  getSecurityEventLogsAPI,
  getUserLoginLogsAPI
} from "./Server/getLogsAPI";

export {
    loginAPI,
    getQuestionTypeAPI,
    getSubjectAPI,
    getQuestionsAPI,
    loginFaceAPI,
    addQuestionsAPI,
    importQuestionsAPI,
    registerAPI,
    teacherRegisterAPI,
    userAPI,
    getSystemOperationLogsAPI,
    getSecurityEventLogsAPI,
    getUserLoginLogsAPI,
}