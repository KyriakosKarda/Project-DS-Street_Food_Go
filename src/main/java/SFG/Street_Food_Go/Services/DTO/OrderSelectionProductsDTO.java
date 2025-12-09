package SFG.Street_Food_Go.Services.DTO;

import java.util.ArrayList;
import java.util.List;

public class OrderSelectionProductsDTO {
    private List<SelectedProducts> selections;

    public OrderSelectionProductsDTO(){
        this.selections = new ArrayList<>();
    }

    public List<SelectedProducts> getSelections() {
        return selections;
    }

    public void setSelections(List<SelectedProducts> selections) {
        this.selections = selections;
    }

    @Override
    public String toString() {
        return "OrderSelectionProductsDTO{" +
                "selections=" + selections +
                '}';
    }
}
