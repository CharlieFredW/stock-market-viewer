package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "volume", schema = "cryptoDB")
@Data
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "volume_notional")
    private Double volumeNotional;

    @Column(name = "trades_done")
    private Double tradesDone;

    public Volume() {}

    public Volume(Double volume, Double volumeNotional, Double tradesDone) {
        this.volume = volume;
        this.volumeNotional = volumeNotional;
        this.tradesDone = tradesDone;
    }
}
