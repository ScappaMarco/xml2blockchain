package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface Create extends EObject {
    String getEclass();
    void setEclass(String eclass);

    String getEpackage();
    void setEpackage(String epackage);

    String getId();
    void setId(String id);
}