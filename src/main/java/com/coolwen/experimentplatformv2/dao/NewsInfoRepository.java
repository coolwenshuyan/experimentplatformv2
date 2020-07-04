package com.coolwen.experimentplatformv2.dao;

import com.coolwen.experimentplatformv2.dao.basedao.BaseRepository;
import com.coolwen.experimentplatformv2.model.NewsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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
}
