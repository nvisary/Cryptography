package com.sgfromrus.encoder;

import com.sgfromrus.Program;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by sgfromrus on 09.2017
 */
public class ShowKeyView extends JDialog {
    private JTable table1;
    private JTable table2;
    private MyTableModel tableModel1;
    private MyTableModel tableModel2;
    private File file;

    public ShowKeyView(File file) {
        super((Dialog) null, "keys", false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(2, 1));


        this.file = file;
        tableModel1 = new MyTableModel();
        tableModel2 = new MyTableModel();

        loadKeyFromFile();

        table1 = new JTable(tableModel1);
        table2 = new JTable(tableModel2);


        JScrollPane scrollpane = new JScrollPane(table1);
        JScrollPane scrollPane2 = new JScrollPane(table2);

        getContentPane().add(scrollpane);
        getContentPane().add(scrollPane2);
        setVisible(true);
    }

    private void loadKeyFromFile() {
        try {
            Scanner in = new Scanner(file);
            int i = 0;
            boolean first = true;
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.length() == 13) {
                    for (int j = 0; j < 13; j++) {
                        if (first) {
                            tableModel1.setValueAt(i, j, line.charAt(j));
                        } else {
                            tableModel2.setValueAt(i, j, line.charAt(j));
                        }
                    }
                    i++;
                    if (i == 13) {
                        i = 0;
                        first = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private class MyTableModel extends AbstractTableModel {
        private char[][] key = new char[13][13];

        @Override
        public int getRowCount() {
            return 13;
        }

        @Override
        public int getColumnCount() {
            return 13;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return key[rowIndex][columnIndex];
        }

        public void setValueAt(int rowIndex, int columnIndex, char value) {
            key[rowIndex][columnIndex] = value;
        }
    }
}
