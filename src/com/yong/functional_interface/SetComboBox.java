package com.yong.functional_interface;

import javafx.scene.control.ComboBox;

/** This functional interface sets an observableList to a combo box using Lambda.
 * @author yongl
 */
public interface SetComboBox {
    /** set an observableList of objects to a combo box.
     * @param combo Accepts a reference of ComboBox. */
    void setListToCombo(ComboBox combo);
    
}
