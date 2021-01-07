package com.coolwen.experimentplatformv2.utils;

import com.coolwen.experimentplatformv2.annotation.OperLog;
import com.coolwen.experimentplatformv2.dao.ExpLogDao;
import com.coolwen.experimentplatformv2.model.ExpLog;
import com.coolwen.experimentplatformv2.model.Student;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据切点表达式在访问指定接口时生成日志，保存日志
 *
 * @author Artell
 * @version 2020/12/30 11:01
 */


@Aspect
@Component
public class OperLogAspect {

    /**
     * 设置操作日志切入点 记录操作日志
     */
    @Autowired
    private ExpLogDao expLogDao;

    /**
     * 在注解的位置切入代码
     * 表示匹配com.coolwen.experimentplatformv2.controller包下的所有方法
     */

    @Pointcut("execution(* com.coolwen.experimentplatformv2.controller.ExpModelController.*(..))")
    public void operLogPoinCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {

        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);

        ExpLog expLog = new ExpLog();

//        这里可以将主键改为String并使用随机的UUID作为主键,因为int有朝一日总会装满的,不过短期内不考虑这个问题

        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();

            Object[] args = joinPoint.getArgs();


            // 获取操作
            OperLog opLog = method.getAnnotation(OperLog.class);
            if (opLog != null) {
                String value = opLog.value();
                expLog.setActionType(value);
                if (expLog.getActionType().equals("进入实验")){
                    //            将参数提取出来
                    Map<String, Object> fieldsName = getFieldsName((ProceedingJoinPoint)joinPoint);
                    expLog.setArrangeId(Integer.parseInt(fieldsName.get("arrangeId").toString()));
                    expLog.setExpId(Integer.parseInt(fieldsName.get("expId").toString()));
                }
            }
            // 获取请求的类名
//            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
//            String methodName = method.getName();
//            methodName = className + "." + methodName + "()";


//            System.out.println("methodName>>>>"+methodName);
            // 请求方法
//            sysLog.setMethod(methodName);
//            String params = getParams(joinPoint, method);

//            sysLog.setParams(params);
            // 请求学生名称

            try {
                Student student = (Student) SecurityUtils.getSubject().getPrincipal();
                expLog.setStudentId(student.getId());


                // 访问的url
//            sysLog.setUrl(request.getRequestURL().toString());
                // 请求IP
                expLog.setIp(IPUtils.getIpAddr(request));
                // 创建时间
                expLog.setLogTime(new Date());
                System.out.println(expLog.getLogTime()+"现在时间");
//            sysLog.setTime((int) (System.currentTimeMillis() - beginTime));
                // 保存日志

//            if (opLog != null) {
//                if (expLog.getActionType().equals("enterExp")){
//                    expLogDao.save(expLog);
//                }
//            }
                expLogDao.save(expLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取请求的参数名称
    private String getParams(JoinPoint joinPoint, Method method) {
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        String params = null;
        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
        }
        return params;
    }

    /**
     * 获取参数列表
     *
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) {
        // 参数值
        Object[] args = joinPoint.getArgs();
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = pnd.getParameterNames(method);
        Map<String, Object> paramMap = new HashMap<>(32);
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], Integer.parseInt(args[i].toString()));
        }
        return paramMap;
    }
}
