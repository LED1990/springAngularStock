package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stock_symbols")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockSymbol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_symbols_id_seq")
    @SequenceGenerator(name = "stock_symbols_id_seq", sequenceName = "stock_symbols_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    @Length(max = 10)
    private String symbol;
    @Length(max = 500)
    private String name;
    private Float price;//USD
}
