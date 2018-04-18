package config;

public class Urls {


    public static final String ip = "http://192.168.0.104:8088/";


    /**
     * 注册
     */
    public static final String REGIST = ip + "user/register.do";


    /**
     * 登陆
     */
    public static final String LOGIN = ip + "user/login.do";


    /**
     * 检测是否有重复邮箱和用户
     */
    public static final String CHECK_USER_REPEAT = ip + "user/check_valid.do";


    /**
     * 密保问题修改用户密码1
     */
    public static final String GET_QUESTION = ip + "user/forget_get_question.do";


    /**
     * 密保问题修改用户密码2
     */
    public static final String ANSWER = ip + "user/forget_check_answer.do";

}
