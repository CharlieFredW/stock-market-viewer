package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.ExchangeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ExchangeInfoRepository extends JpaRepository<ExchangeInfo, Integer> {
    ExchangeInfo findTopByOrderByIdDesc();
    @Query("SELECT ei, tv FROM ExchangeInfo ei JOIN TradeValue tv ON ei.tradeValuesId = tv.id WHERE ei.tradeTime > :currentTime")
    List<Object[]> fetchTrade(@Param("currentTime") Timestamp currentTime);

    @Query("SELECT ei, v FROM ExchangeInfo ei JOIN Volume v ON ei.volumeId = v.id WHERE ei.tradeTime > :currentTime")
    List<Object[]> fetchVolume(@Param("currentTime") Timestamp currentTime);
}

