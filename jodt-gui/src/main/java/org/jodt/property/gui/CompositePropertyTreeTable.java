package org.jodt.property.gui;

import org.apache.log4j.Logger;
import org.jodt.property.CompositeProperty;
import org.jodt.property.PropertyTool;
import org.jodt.util.gui.treetable.DefaultJXTreeTable;
import org.jodt.util.gui.treetable.DefaultParentUpdater;

/**
 * @author Oliver Stuch (oliver@stuch.net)
 */
//TODO: Update alle Vorkommen eines Objektes
//Idee: Alle Vorkommen eines Objektes in der Table anzeigen (ctrl-shift-g sozusasgen)
public class CompositePropertyTreeTable extends DefaultJXTreeTable {

    private Object object;
    private String objectName;
    private PropertyTool propertyTool;

    /**
     * @param object Das in einer PropertyTreeTable darzustellende Objekt
     * @param objectName Name des Objekts (momentan ohne Funktion/Bedeutung)
     */
    public CompositePropertyTreeTable(Object object, String objectName, PropertyTool propertyTool) {
        this.propertyTool = propertyTool;
        setTreeCellRenderer(new PropertyIconTreeCellRenderer(this));
        set(object, objectName);
        new ContextMenuFactory().setupContextMenu(this);
    }

// TODO unschön!?
    public void update() {
        set(object, objectName);
    }

    public void set(Object object, String objectName) {
        this.object = object;
        this.objectName = objectName;
        CompositeProperty compositeProperty = propertyTool.createCompositeProperty(object, objectName);
        if (compositeProperty != null) {
            DefaultJXTreeTable.DefaultJXTreeTableModel dttm = new DefaultJXTreeTableModel(new CompositeProperty2TreeTableNodeAdapter(compositeProperty), new String[]{
                "Attribut oder Index", "Wert"});
            dttm.addTreeModelListener(new DefaultParentUpdater(dttm));
            dttm.setNotEditable(notEditableRegistry);
            setTreeTableModel(dttm);
        }
    }

    private static Logger logger = Logger.getLogger(CompositePropertyTreeTable.class);
}
