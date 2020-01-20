package app.model.wrappers;

import app.model.StockSymbol;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SymbolsWrapper {
    private List<StockSymbol> symbolsList;
}
