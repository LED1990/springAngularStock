package app.model.wrappers;

import app.model.StockSymbol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SymbolsWrapper {
    private List<StockSymbol> symbolsList;
}
