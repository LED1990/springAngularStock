package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "stock_week_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_week_data_id_seq")
    @SequenceGenerator(name = "stock_week_data_id_seq", sequenceName = "stock_week_data_id_seq", allocationSize = 1)
    private Long id;
    @Column
    private String symbol;
    @Column(name = "day_of_week")
    private Date date;
    @Column(name = "open_price")
    private Float open;
    @Column(name = "close_price")
    private Float close;
    @Column(name = "high_price")
    private Float high;
    @Column(name = "low_price")
    private Float low;
    @Column
    private Float change;
    @Column(name = "change_percent")
    private Float changePercent;
    @Column
    @NotNull
    private Boolean actual;

}
