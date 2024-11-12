package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "exchange_info", schema = "cryptoDB")
@Data
public class ExchangeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "trade_values_id")
    private Integer tradeValuesId;

    @Column(name = "volume_id")
    private Integer volumeId;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "trade_time")
    private java.sql.Timestamp tradeTime;

    public ExchangeInfo() {}

    public ExchangeInfo(Integer tradeValuesId, Integer volumeId, String ticker, java.sql.Timestamp tradeTime) {
        this.tradeValuesId = tradeValuesId;
        this.volumeId = volumeId;
        this.ticker = ticker;
        this.tradeTime = tradeTime;
    }
}