package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.NewsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//public interface NewsInfoRepository  extends PagingAndSortingRepository<NewsInfo,Integer> {
public interface NewsInfoRepository  extends BaseRepository<NewsInfo, Integer>, JpaSpecificationExecutor<NewsInfo> {

    @Query(value="select * from t_newsinfo where id = ?",nativeQuery=true)
    NewsInfo findByInfo(int id);

    @Query(value ="select * from t_newsinfo order by t_newsinfo.dic_datetime desc ",nativeQuery=true)
    public Page<NewsInfo> findAllorderby(Pageable pageable);

    //当期考核人数
    @Query(value ="select count(*) from t_totalscore_current",nativeQuery=true)
    int findAllmodelpeople();
    //往期考核人数
    @Query(value ="select count(*) from t_totalscore_pass",nativeQuery=true)
    int findAllmodelpeople1();
    //当期通过人数
    @Query(value = "select count(*) from t_totalscore_current where total_score>=60",nativeQuery=true)
    int findAllPass();
    //往期通过人数
    @Query(value = "select count(*) from t_totalscore_pass where total_score>=60",nativeQuery=true)
    int findAllPass1();

    @Query(value = "select count(*) from t_totalscore_pass where total_score>=85",nativeQuery=true)
    int findExcellentpeople();

    @Query(value = "select count(*) from t_totalscore_pass where total_score>=60 and total_score<85",nativeQuery=true)
    int findQualifiedpeople();

    @Query(value = "select count(*) from t_totalscore_pass where total_score<60",nativeQuery=true)
    int findUnqualifiedpeople();
    //查询该课程往期考核人数
    @Query(value ="select count(*) from t_totalscore_pass where course_id = ?",nativeQuery=true)
    int findAllpasspeopleByCourseId(int id);
    //查询该课程往期考核分数高于85分的人
    @Query(value = "select count(*) from t_totalscore_pass where total_score>=85 and course_id = ?",nativeQuery=true)
    int findExcellentpeopleByCourseId(int id);
    //查询该课程往期考核分数60-85分的人
    @Query(value = "select count(*) from t_totalscore_pass where total_score>=60 and total_score<85 and course_id = ?",nativeQuery=true)
    int findQualifiedpeopleByCourseId(int id);
    //查询该课程往期考核分数60分以下的人
    @Query(value = "select count(*) from t_totalscore_pass where total_score<60 and course_id = ?",nativeQuery=true)
    int findUnqualifiedpeopleByCourseId(int id);

    //查询学生总成绩排行榜
//    @Query(value = "select new com.coolwen.experimentplatformv2.model.DTO.OverallScoreDto (s.stuXuehao,avg(avg(tc.totalScore)+avg(tp.totalScore)),l.totalTime) from Student s ,TotalScoreCurrent tc,TotalScorePass tp,LearningTime l where s.id = tc.stuId and tp.stuId = s.id and l.sid = s.id group by s.id")
//    List<OverallScoreDto> findScoreRanking();
//    @Query(value = "select s.stu_xuehao,ifnull((cc_sumscore+pp_sumscore)/(cc_sumcount+pp_sumcount),0),ifnull(l.total_time,0) \n" +
//            "\n" +
//            "from t_student s LEFT JOIN t_learning_time l on s.id=l.sid \n" +
//            "\n" +
//            "LEFT JOIN ((SELECT stu_id,sum(total_score) AS cc_sumscore,count(stu_id ) as cc_sumcount\n" +
//            "        FROM t_totalscore_current \n" +
//            "        GROUP BY t_totalscore_current.stu_id \n" +
//            ")AS cc_temp) ON s.id=cc_temp.stu_id \n" +
//            "LEFT JOIN ((SELECT stu_id,sum(total_score) AS pp_sumscore,count(stu_id ) as pp_sumcount\n" +
//            "        FROM t_totalscore_pass \n" +
//            "        GROUP BY t_totalscore_pass.stu_id \n" +
//            ")AS pp_temp) on s.id=pp_temp.stu_id \n" +
//            "group by s.id \n" +
//            "order by ifnull((cc_sumscore+pp_sumscore)/(cc_sumcount+pp_sumcount),0) desc,ifnull(l.total_time,0) desc limit 10",nativeQuery=true)
    @Query(value = "select stu_xuehao_a,((cc_sumscore_a+pp_sumscore_a)/(cc_sumcount_a+pp_sumcount_a)),total_time_a from\n" +
            "(select s.stu_xuehao as stu_xuehao_a,ifnull(cc_sumscore,0) as cc_sumscore_a,ifnull(pp_sumscore,0) as pp_sumscore_a,ifnull(cc_sumcount,0) as cc_sumcount_a,ifnull(pp_sumcount,0) as pp_sumcount_a,ifnull(l.total_time,0) as total_time_a\n" +
            "from t_student s LEFT JOIN t_learning_time l on s.id=l.sid\n" +
            "LEFT JOIN (SELECT stu_id,ifnull(sum(total_score),0) AS cc_sumscore,ifnull(count(stu_id ),0) as cc_sumcount\n" +
            "        FROM t_totalscore_current\n" +
            "        GROUP BY t_totalscore_current.stu_id\n" +
            ")AS cc_temp ON s.id=cc_temp.stu_id\n" +
            "LEFT JOIN (SELECT stu_id,ifnull(sum(total_score),0) AS pp_sumscore,ifnull(count(stu_id),0) as pp_sumcount\n" +
            "        FROM t_totalscore_pass\n" +
            "        GROUP BY t_totalscore_pass.stu_id\n" +
            ")AS pp_temp on s.id=pp_temp.stu_id) as temp_tb1\n" +
            "order by ((cc_sumscore_a+pp_sumscore_a)/(cc_sumcount_a+pp_sumcount_a)) desc,total_time_a desc limit 10",nativeQuery=true)
    List findScoreRanking();

//    @Query(value = "select s.stu_xuehao,ifnull(tc.total_score,IFNULL(tp.total_score,0)),ifnull(l.total_time,0) from t_student s LEFT JOIN t_learning_time l on s.id=l.sid LEFT JOIN t_arrange_class ac on s.class_id=ac.class_id and ac.course_id=? LEFT JOIN t_totalscore_current tc ON s.id=tc.stu_id and ac.id=tc.arrange_id LEFT JOIN t_totalscore_pass tp on s.id=tp.stu_id and tp.course_id=ac.course_id ORDER BY ifnull(tc.total_score,IFNULL(tp.total_score,0)) DESC,ifnull(l.total_time,0) DESC limit 14",nativeQuery=true)
    @Query(value = "select * FROM ((SELECT t_student.stu_xuehao,IFNULL(total_score,0) AS score1,IFNULL(t_learning_time.total_time,0) AS total_time \n" +
            "FROM t_totalscore_current left join t_arrange_class on t_totalscore_current.arrange_id=t_arrange_class.id left join t_learning_time on t_totalscore_current.stu_id=t_learning_time.sid LEFT JOIN t_student on t_student.id=t_totalscore_current.stu_id where t_arrange_class.course_id=?1) union (SELECT t_student.stu_xuehao,total_score AS score1,IFNULL(total_time,0) \n" +
            "FROM t_totalscore_pass LEFT JOIN t_learning_time ON t_totalscore_pass.stu_id=t_learning_time.sid LEFT JOIN t_student on t_student.id = t_totalscore_pass.stu_id WHERE t_totalscore_pass.course_id=?1))  as temp ORDER BY score1 DESC,total_time desc  limit 14",nativeQuery=true)
    List findClassScoreRanking(int courseId);

    @Query(value = "SELECT count1+count4,count2+count4,count3+count6 FROM(\n" +
            "(SELECT count(CASE WHEN t_totalscore_current.total_score BETWEEN 0 and 59 then 1 END) as count1 FROM t_totalscore_current) as t1,\n" +
            "(SELECT count(CASE WHEN t_totalscore_current.total_score BETWEEN 60 and 84 then 1 END) as count2 FROM t_totalscore_current) as t2,\n" +
            "(SELECT count(CASE WHEN t_totalscore_current.total_score BETWEEN 85 and 100 then 1 END) as count3 FROM t_totalscore_current) as t3,\n" +
            "(SELECT count(CASE WHEN t_totalscore_pass.total_score BETWEEN 0 and 59 then 1 END) as count4 FROM t_totalscore_pass) as t4,\n" +
            "(SELECT count(CASE WHEN t_totalscore_pass.total_score BETWEEN 60 and 84 then 1 END) as count5 FROM t_totalscore_pass) as t5,\n" +
            "(SELECT count(CASE WHEN t_totalscore_pass.total_score BETWEEN 85 and 100 then 1 END) as count6 FROM t_totalscore_pass) as t6)",nativeQuery=true)
    List resultsStatistical();

    @Query(value = "SELECT count1+count4,count2+count4,count3+count6 FROM (\n" +
            "(SELECT count(CASE WHEN t_totalscore_current.total_score BETWEEN 0 and 59 then 1 END) as count1 FROM t_totalscore_current,t_arrange_class WHERE t_totalscore_current.arrange_id=t_arrange_class.id and t_arrange_class.course_id = ?1) as t1,\n" +
            "(SELECT count(CASE WHEN t_totalscore_current.total_score BETWEEN 60 and 84 then 1 END) as count2 FROM t_totalscore_current,t_arrange_class WHERE t_totalscore_current.arrange_id=t_arrange_class.id and t_arrange_class.course_id = ?1) as t2,\n" +
            "(SELECT count(CASE WHEN t_totalscore_current.total_score BETWEEN 85 and 100 then 1 END) as count3 FROM t_totalscore_current,t_arrange_class WHERE t_totalscore_current.arrange_id=t_arrange_class.id and t_arrange_class.course_id = ?1) as t3,\n" +
            "(SELECT count(CASE WHEN t_totalscore_pass.total_score BETWEEN 0 and 59 then 1 END) as count4 FROM t_totalscore_pass WHERE t_totalscore_pass.course_id = ?1) as t4,\n" +
            "(SELECT count(CASE WHEN t_totalscore_pass.total_score BETWEEN 60 and 84 then 1 END) as count5 FROM t_totalscore_pass WHERE t_totalscore_pass.course_id = ?1) as t5,\n" +
            "(SELECT count(CASE WHEN t_totalscore_pass.total_score BETWEEN 85 and 100 then 1 END) as count6 FROM t_totalscore_pass WHERE t_totalscore_pass.course_id = ?1) as t6)",nativeQuery=true)
    List courseGrade(int id);
}
