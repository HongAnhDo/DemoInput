package had.com;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class FormBuilder {
    private final Container container;
    private int row;
    private int col = -1;
    private GridBagConstraints labelCons;

    private FormBuilder(Container container) {
        this.container = container;
        container.setLayout(new GridBagLayout());
    }

    public static FormBuilder init(Container container) {
        FormBuilder builder = new FormBuilder(container);
        return builder;
    }

    private void initLabelCons() {
        labelCons = new GridBagConstraints();
        labelCons.weightx = 0;
        labelCons.gridwidth = 1;
        labelCons.ipadx = 1;
        labelCons.anchor = GridBagConstraints.NORTHEAST;
    }

    //add component without label
    public FormBuilder add(JComponent comp) {
        this.add(null, comp, null);
        return this;
    }

    //add component without label and allow caller to set constraints
    @SafeVarargs
    public final FormBuilder add(JComponent comp, Consumer<GridBagConstraints>... consSetters) {
        this.add(null, comp, consSetters);
        return this;
    }

    //add component with label and allow caller to set constraints
    @SafeVarargs
    public final FormBuilder add(String label, JComponent comp, Consumer<GridBagConstraints>... consSetters) {
        if (label != null) {
            if (this.labelCons == null) {
                initLabelCons();
            }
            JLabel jLabel = new JLabel(label);
            labelCons.gridx = ++col;
            labelCons.gridy = row;
            container.add(jLabel, labelCons);
        }
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = ++col;
        cons.gridy = row;
        cons.fill = GridBagConstraints.BOTH;//by default fill the available grid space
        cons.insets = new Insets(0, 5, 5, 0);
        if (consSetters != null) {//allow client side to set constraints
            for (Consumer<GridBagConstraints> cs : consSetters) {
                cs.accept(cons);
            }
        }
        container.add(comp, cons);
        return this;
    }

    //add multiple labels in current row
    public FormBuilder addLabelsAsRowHeading(String... labels) {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridy = row;
        for (String label : labels) {
            cons.gridx = ++col;
            JLabel jLabel = new JLabel(label);
            container.add(jLabel, cons);
        }
        return this;
    }

    //start new row
    public FormBuilder newRow() {
        ++row;
        col = -1;
        return this;
    }

    //skip columns
    public FormBuilder skipColumns(int columns) {
        col+=columns;
        return this;
    }

    /**
     * Methods to set constraints, Useful when used as Java 8 method reference
     */
    //horizontal span 2
    public static void spanX2(GridBagConstraints c) {
        c.gridwidth = 2;
    }

    //horizontal span 3
    public static void spanX3(GridBagConstraints c) {
        c.gridwidth = 3;
    }

    //horizontal span 4
    public static void spanX4(GridBagConstraints c) {
        c.gridwidth = 4;
    }

    //vertical span 2
    public static void spanY2(GridBagConstraints c) {
        c.gridheight = 2;
    }

    //fills horizontally if parent resized
    public static void fillParentX(GridBagConstraints c) {
        c.weightx = 1;
    }

    //fills vertically if parent resized
    public static void fillParentY(GridBagConstraints c) {
        c.weighty = 1;
    }
    //todo add more if needed
}