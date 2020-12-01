/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.component.table;

import domain.Production;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mihailo
 */
public class ProductionTableModel extends AbstractTableModel{
    private final List<Production> productionCompanies;
    private final String[] columnNames = {
        "ID",
        "Production co. name"
    };
    private final Class[] columnClasses = {
        Integer.class,
        String.class
    };
    
    public ProductionTableModel(List<Production> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    @Override
    public int getRowCount() {
        if (productionCompanies == null) 
            return 0;
         else 
            return productionCompanies.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Production production = productionCompanies.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return production.getProductionCompany().getProductionCompanyID();
            case 1: return production.getProductionCompany().getName();
            default: return "N/A";
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    public Production getProductionAt(int row) {
        return productionCompanies.get(row);
    }
    
    public List<Production> getAll() {
        return productionCompanies;
    }
}
