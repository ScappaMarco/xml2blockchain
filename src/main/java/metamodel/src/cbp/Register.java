package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface Register extends EObject {
    String getEpackage();
    void setEpackage(String epackage);
}