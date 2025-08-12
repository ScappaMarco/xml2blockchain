package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface Session extends EObject {
    String getId();
    void setId(String id);

    String getTime();
    void setTime(String time);
}