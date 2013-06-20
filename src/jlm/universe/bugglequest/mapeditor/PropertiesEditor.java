package jlm.universe.bugglequest.mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import jlm.universe.Entity;
import jlm.universe.World;
import jlm.universe.bugglequest.AbstractBuggle;
import jlm.universe.bugglequest.BuggleWorld;
import jlm.universe.bugglequest.BuggleWorldCell;

import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

public class PropertiesEditor extends JComponent implements EditionListener {
	private static final long serialVersionUID = 3904327915735497696L;
	private I18n i18n = I18nFactory.getI18n(getClass(),"org.jlm.i18n.Messages",getLocale(), I18nFactory.FALLBACK);
	
	private AbstractBuggle selectedBuggle;

	private DefaultTableModel model = new DefaultTableModel() {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int rowIndex, int colIndex) {
			return colIndex>0;
		}
	};
	private JTable table = new JTable(model);

	private Editor editor; 
	private int selectedXRank,selectedYRank,topRank,leftRank;

	Vector<JLMProperty> properties = new Vector<JLMProperty>();
	
	public PropertiesEditor(Editor _editor) {
		editor = _editor;
		editor.addEditionListener(this);

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);

		model.setColumnCount(2);
		model.setColumnIdentifiers(new Object[] {i18n.tr("Property"), i18n.tr("Value")});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		repopulateTable();
		setVisible(true);
		setPreferredSize(new Dimension(100, 500));

		model.addTableModelListener(new MyTableModelListener(editor,table,properties));
	}
	private void repopulateTable() {
		while (model.getRowCount()>0)
			model.removeRow(0);
		properties.removeAllElements();

		/* The editor for the name */
		model.insertRow(0, new Object[] {i18n.tr("World name"), new JLMProperty(properties) { 
			@Override
			public void setValue(String value) {
				editor.getWorld().setName(value);
			}
			@Override
			public String toString() {
				return editor.getWorld().getName();
			}
		}});

		/*---------- world width ---------------*/
		model.addRow(new Object[] {i18n.tr("World width"), new JLMProperty(properties) {
			@Override
			public String toString() {
				return ""+editor.getWorld().getWidth();
			}
			@Override
			public void setValue(String value) {
				Integer i;
				try {
					i = Integer.parseInt(value);
				} catch (NumberFormatException nfe) {
					table.setValueAt(""+editor.getWorld().getWidth(),rank,1);
					return; // silently ignore invalid values
				}
				editor.getWorld().setWidth(i);
				editor.getWorld().notifyWorldUpdatesListeners();
			}
		}});
		
		/*---------- world height ---------------*/
		model.addRow(new Object[] {i18n.tr("World height"), new JLMProperty(properties) {
			@Override
			public String toString() {
				return ""+editor.getWorld().getHeight();
			}
			@Override
			public void setValue(String value) {
				Integer i;
				try {
					i = Integer.parseInt(value);
				} catch (NumberFormatException nfe) {
					table.setValueAt(""+editor.getWorld().getHeight(),rank,1);
					return; // silently ignore invalid values
				}
				editor.getWorld().setHeight(i);
				editor.getWorld().notifyWorldUpdatesListeners();
			}
		}});

		/*---------- selected cell ---------------*/
		model.addRow(new Object[] {i18n.tr("Selected cell X"), new JLMProperty(properties) {
			@Override
			public String toString() {
				selectedXRank = rank;
				return ""+editor.getWorld().getSelectedCell().getX();
			}
			@Override
			public void setValue(String value) {
				Integer x;
				try {
					x = Integer.parseInt(value);
					if (x>=editor.getWorld().getWidth() || x<0)
						throw new NumberFormatException("out of world");
				} catch (NumberFormatException nfe) {
					table.setValueAt(""+editor.getWorld().getSelectedCell().getX(),rank,1);
					return; // silently ignore invalid values
				}
				editor.getWorld().setSelectedCell(x, editor.getWorld().getSelectedCell().getY());
			}
		}});

		/*---------- selected cell ---------------*/
		model.addRow(new Object[] {i18n.tr("Selected cell Y"), new JLMProperty(properties) {
			@Override
			public String toString() {
				selectedYRank = rank;
				return ""+editor.getWorld().getSelectedCell().getY();
			}
			@Override
			public void setValue(String value) {
				Integer y;
				try {
					y = Integer.parseInt(value);
					if (y>=editor.getWorld().getHeight() || y<0)
						throw new NumberFormatException("out of world");
				} catch (NumberFormatException nfe) {
					table.setValueAt(""+editor.getWorld().getSelectedCell().getY(),rank,1);
					return; // silently ignore invalid values
				}
				editor.getWorld().setSelectedCell(editor.getWorld().getSelectedCell().getX(),y);
			}
		}});
		/*---------- top wall cell ---------------*/
		model.addRow(new Object[] {i18n.tr("Top wall?"), new JLMProperty(properties) {
			@Override
			public String toString() {
				topRank = rank;
				return editor.getWorld().getSelectedCell().hasTopWall()?i18n.tr("Y"):i18n.tr("N");
			}
			@Override
			public void setValue(String value) {
				if (!value.equalsIgnoreCase(i18n.tr("Y")) && !value.equalsIgnoreCase(i18n.tr("N"))) {
					table.setValueAt(editor.getWorld().getSelectedCell().hasTopWall()?i18n.tr("Y"):i18n.tr("N"),rank,1);
					return;
					
				} else if (value.equalsIgnoreCase(i18n.tr("Y"))) {
					if (!editor.getWorld().getSelectedCell().hasTopWall()) // only update if needed
						editor.getWorld().getSelectedCell().putTopWall();
				} else {
					if (editor.getWorld().getSelectedCell().hasTopWall()) // only update if needed
						editor.getWorld().getSelectedCell().removeTopWall();
				}
			}
		}});
		/*---------- left wall cell ---------------*/
		model.addRow(new Object[] {i18n.tr("Left wall?"), new JLMProperty(properties) {
			@Override
			public String toString() {
				leftRank = rank;
				return editor.getWorld().getSelectedCell().hasLeftWall()?i18n.tr("Y"):i18n.tr("N");
			}
			@Override
			public void setValue(String value) {
				if (!value.equalsIgnoreCase(i18n.tr("Y")) && !value.equalsIgnoreCase(i18n.tr("N"))) {
					table.setValueAt(editor.getWorld().getSelectedCell().hasLeftWall()?i18n.tr("Y"):i18n.tr("N"),rank,1);
					return;
					
				} else if (value.equalsIgnoreCase(i18n.tr("Y"))) {
					if (!editor.getWorld().getSelectedCell().hasLeftWall()) // only update if needed
						editor.getWorld().getSelectedCell().putLeftWall();
				} else {
					if (editor.getWorld().getSelectedCell().hasLeftWall()) // only update if needed
						editor.getWorld().getSelectedCell().removeLeftWall();
				}
			}
		}});
		
	}
	@Override
	public void setWorld(World w) {
		if (((BuggleWorld) w).getSelectedCell() == null)
			((BuggleWorld) w).setSelectedCell(0,0);
		repopulateTable();		
		
	}
	@Override
	public void worldEdited() {
		BuggleWorldCell selected = editor.getWorld().getSelectedCell();
		
		table.setValueAt(""+selected.getX(),selectedXRank,1);
		table.setValueAt(""+selected.getY(),selectedYRank,1);
		table.setValueAt(selected.hasTopWall() ?i18n.tr("Y"):i18n.tr("N"), topRank, 1);
		table.setValueAt(selected.hasLeftWall()?i18n.tr("Y"):i18n.tr("N"), leftRank,1);
	}
	@Override
	public void selectedChanged(int x, int y, Entity ent) {
		BuggleWorldCell selected = editor.getWorld().getSelectedCell();
		
		table.setValueAt(""+selected.getX(),selectedXRank,1);
		table.setValueAt(""+selected.getY(),selectedYRank,1);
		table.setValueAt(selected.hasTopWall() ?i18n.tr("Y"):i18n.tr("N"), topRank, 1);
		table.setValueAt(selected.hasLeftWall()?i18n.tr("Y"):i18n.tr("N"), leftRank,1);
		
		if (selectedBuggle != ent) {
			selectedBuggle = (AbstractBuggle) ent;
			repopulateTable();
		} else {
			
		}
	}
}

class MyTableModelListener implements TableModelListener {
	private JTable table;
	private Vector<JLMProperty> properties;
	
	MyTableModelListener(Editor e, JTable t, Vector<JLMProperty> props) {
		table = t;
		properties = props;
	}
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow(); // selections are SINGLE_SELECTION anyway, so ignore getLastRow

		if (e.getType() == TableModelEvent.UPDATE) {
			for (JLMProperty p : properties) {
				if (p.rank == row) {
					p.setValue(""+ table.getModel().getValueAt(row, 1));
					return;
				}
			}
			System.out.println("No property seem to be in charge of row "+row+". Ignoring the edit.");
		}
	}
}

abstract class JLMProperty {
	public int rank;
	public JLMProperty(Vector<JLMProperty> props) {
		rank = props.size();
		props.add(this);
	}
	public abstract void setValue(String value);
	public abstract String toString();
}