package marco_tests.someTablesExamples;

/*
Core SWING Advanced Programming 
By Kim Topley
ISBN: 0 13 083292 8       
Publisher: Prentice Hall  
*/


// http://stackoverflow.com/questions/8923089/swing-jtable-custom-rendering

// TODO qui combobox
//http://www.java2s.com/Code/Java/Swing-Components/ComboBoxTable.htm


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class TableIcon {
  public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {}
  
    JFrame f = new JFrame("Icon Currency Table");
    JTable tbl = new JTable(new CurrencyTableModel());
    tbl.setDefaultRenderer(java.lang.Number.class,
        new FractionCellRenderer(10, 3, SwingConstants.RIGHT));

    TableColumnModel tcm = tbl.getColumnModel();
    tcm.getColumn(0).setPreferredWidth(150);
    tcm.getColumn(0).setMinWidth(150);
    TextWithIconCellRenderer renderer = new TextWithIconCellRenderer();
    tcm.getColumn(0).setCellRenderer(renderer);

    tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tbl.setPreferredScrollableViewportSize(tbl.getPreferredSize());

    JScrollPane sp = new JScrollPane(tbl);
    f.getContentPane().add(sp, "Center");
    f.pack();
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    f.setVisible(true);
  }
}

class CurrencyTableModel extends AbstractTableModel {
  protected String[] columnNames = { "Currency", "Yesterday", "Today",
      "Change" };

  // Constructor: calculate currency change to create the last column
  public CurrencyTableModel() {
    for (int i = 0; i < data.length; i++) {
      data[i][DIFF_COLUMN] = new Double(
          ((Double) data[i][NEW_RATE_COLUMN]).doubleValue()
              - ((Double) data[i][OLD_RATE_COLUMN]).doubleValue());
    }
  }

  // Implementation of TableModel interface
  public int getRowCount() {
    return data.length;
  }

  public int getColumnCount() {
    return COLUMN_COUNT;
  }

  public Object getValueAt(int row, int column) {
    return data[row][column];
  }

  public Class getColumnClass(int column) {
    return (data[0][column]).getClass();
  }

  public String getColumnName(int column) {
    return columnNames[column];
  }

  protected static final int OLD_RATE_COLUMN = 1;

  protected static final int NEW_RATE_COLUMN = 2;

  protected static final int DIFF_COLUMN = 3;

  protected static final int COLUMN_COUNT = 4;

  protected static final Class thisClass = CurrencyTableModel.class;

  protected Object[][] data = new Object[][] {
      {
          new DataWithIcon("Belgian Franc", new ImageIcon(thisClass
              .getResource("belgium.gif"))),
          new Double(37.6460110), new Double(37.6508921), null },
      {
          new DataWithIcon("British Pound", new ImageIcon(thisClass
              .getResource("gb.gif"))),
          new Double(0.6213051), new Double(0.6104102), null },
      {
          new DataWithIcon("Canadian Dollar", new ImageIcon(thisClass
              .getResource("canada.gif"))),
          new Double(1.4651209), new Double(1.5011104), null },
      {
          new DataWithIcon("French Franc", new ImageIcon(thisClass
              .getResource("france.gif"))),
          new Double(6.1060001), new Double(6.0100101), null },
      {
          new DataWithIcon("Italian Lire", new ImageIcon(thisClass
              .getResource("italy.gif"))),
          new Double(1181.3668977), new Double(1182.104), null },
      {
          new DataWithIcon("German Mark", new ImageIcon(thisClass
              .getResource("germany.gif"))),
          new Double(1.8191804), new Double(1.8223421), null },
      {
          new DataWithIcon("Japanese Yen", new ImageIcon(thisClass
              .getResource("japan.gif"))),
          new Double(141.0815412), new Double(121.0040432), null } };
}

class DataWithIcon {
  public DataWithIcon(Object data, Icon icon) {
    this.data = data;
    this.icon = icon;
  }

  public Icon getIcon() {
    return icon;
  }

  public Object getData() {
    return data;
  }

  public String toString() {
    return data.toString();
  }

  protected Icon icon;

  protected Object data;
}

class FractionCellRenderer extends DefaultTableCellRenderer {
  public FractionCellRenderer(int integer, int fraction, int align) {
    this.integer = integer; // maximum integer digits
    this.fraction = fraction; // exact number of fraction digits
    this.align = align; // alignment (LEFT, CENTER, RIGHT)
  }

  protected void setValue(Object value) {
    if (value != null && value instanceof Number) {
      formatter.setMaximumIntegerDigits(integer);
      formatter.setMaximumFractionDigits(fraction);
      formatter.setMinimumFractionDigits(fraction);
      setText(formatter.format(((Number) value).doubleValue()));
    } else {
      super.setValue(value);
    }
    setHorizontalAlignment(align);
  }

  protected int integer;

  protected int fraction;

  protected int align;

  protected static NumberFormat formatter = NumberFormat.getInstance();
}

class TextWithIconCellRenderer extends DefaultTableCellRenderer {
  protected void setValue(Object value) {
    if (value instanceof DataWithIcon) {
      if (value != null) {
        DataWithIcon d = (DataWithIcon)value;
        Object dataValue = d.getData();

        setText(dataValue == null ? "" : dataValue.toString());
        setIcon(d.getIcon());
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
      } else {
        setText("");
        setIcon(null);
      }
    } else {
      super.setValue(value);
    }
  }
}