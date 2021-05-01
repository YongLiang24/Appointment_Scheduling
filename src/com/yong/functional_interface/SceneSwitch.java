package com.yong.functional_interface;

import java.io.IOException;
import javafx.scene.Parent;
import javafx.stage.Stage;
/**
 *
 * @author yongl
 */
/** This is a functional interface for scene switching using Lambda Expression. */
public interface SceneSwitch {
    /** A void abstract method that takes a type of Stage and a Parent as parameter.
     * @param stage A Stage object reference
     * @param scene A Parent object reference
     * @return Multiple Line Lambda requires a return
     * @throws java.io.IOException throws IOException for all lambda expressions that implements this method*/
    Stage switchScene(Stage stage, Parent scene)throws IOException;
}
