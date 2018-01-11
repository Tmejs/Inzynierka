
package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 *
 * @author Tmejs
 */
public class CoursesPanel<T extends MyModel> extends MyContainer<T> {

    public CoursesPanel(T model) {
        super(model);
    }

    @Override
    public void buildView() {
        this.addComponent(new TextField(this.getClass().toString()));
    }

}
