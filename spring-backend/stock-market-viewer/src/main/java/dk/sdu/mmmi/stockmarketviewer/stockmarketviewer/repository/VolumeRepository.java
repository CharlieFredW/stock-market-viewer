package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.repository;

import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.TradeValue;
import dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Integer> {

    Volume findTopByOrderByIdDesc();
}
