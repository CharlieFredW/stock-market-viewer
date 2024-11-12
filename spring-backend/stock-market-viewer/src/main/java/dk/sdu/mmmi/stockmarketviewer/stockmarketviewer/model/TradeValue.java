package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "trade_value", schema = "cryptoDB")
@Data
public class TradeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_values_id")
    private Integer id;

    @Column(name = "high_value")
    private Double highValue;

    @Column(name = "mid_value")
    private Double midValue;

    @Column(name = "low_value")
    private Double lowValue;

    @Column(name = "open_value")
    private Double openValue;

    @Column(name = "close_value")
    private Double closeValue;

    public TradeValue() {}

    public TradeValue(Double highValue, Double midValue, Double lowValue, Double openValue, Double closeValue) {
        this.highValue = highValue;
        this.midValue = midValue;
        this.lowValue = lowValue;
        this.openValue = openValue;
        this.closeValue = closeValue;
    }

}
