package com.agencia.objectdb.vistas;

import com.agencia.objectdb.modelos.Turista;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TuristaTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Código", "Nombre", "Apellidos", "Teléfono"};
    private List<Turista> turistas;

    public TuristaTableModel() {
        this.turistas = new ArrayList<>();
    }

    public TuristaTableModel(List<Turista> turistas) {
        this.turistas = turistas != null ? turistas : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return turistas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Turista turista = turistas.get(rowIndex);
        switch (columnIndex) {
            case 0: return turista.getId();
            case 1: return turista.getCodigoTurista();
            case 2: return turista.getNombre();
            case 3: return turista.getApellidos();
            case 4: return turista.getTelefono();
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Long.class;
        } else {
            return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setTuristas(List<Turista> turistas) {
        this.turistas = turistas != null ? turistas : new ArrayList<>();
        fireTableDataChanged();
    }

    public Turista getTuristaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < turistas.size()) {
            return turistas.get(rowIndex);
        }
        return null;
    }

    public void guardarTurista(Turista turista) {
        turistas.add(turista);
        fireTableRowsInserted(turistas.size() - 1, turistas.size() - 1);
    }

    public void eliminarTurista(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < turistas.size()) {
            turistas.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
