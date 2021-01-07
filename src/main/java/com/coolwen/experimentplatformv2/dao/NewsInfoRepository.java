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
    @Query(value = "select s.stu_xuehao,ifnull((avg(tc.total_score)+AVG(tp.total_score))/2,0),ifnull(l.total_time,0) from t_student s LEFT JOIN t_learning_time l on s.id=l.sid LEFT JOIN t_totalscore_current tc ON s.id=tc.stu_id LEFT JOIN t_totalscore_pass tp on s.id=tp.stu_id group by s.id order by ifnull((avg(tc.total_score)+AVG(tp.total_score))/2,0) desc,ifnull(l.total_time,0) desc limit 10",nativeQuery=true)
    List findScoreRanking();

    @Query(value = "select s.stu_xuehao,ifnull(tc.total_score,IFNULL(tp.total_score,0)),ifnull(l.total_time,0) from t_student s LEFT JOIN t_learning_time l on s.id=l.sid LEFT JOIN t_arrange_class ac on s.class_id=ac.class_id and ac.course_id=? LEFT JOIN t_totalscore_current tc ON s.id=tc.stu_id and ac.id=tc.arrange_id LEFT JOIN t_totalscore_pass tp on s.id=tp.stu_id and tp.course_id=ac.course_id ORDER BY ifnull(tc.total_score,IFNULL(tp.total_score,0)) DESC,ifnull(l.total_time,0) DESC limit 14",nativeQuery=true)
    List findClassScoreRanking(int courseId);
}
