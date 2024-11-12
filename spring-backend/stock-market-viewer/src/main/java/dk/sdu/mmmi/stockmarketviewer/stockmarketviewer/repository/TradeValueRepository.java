package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeValueRepository extends JpaRepository<TradeValue, Integer> {

    TradeValue findTopByOrderByIdDesc();


}