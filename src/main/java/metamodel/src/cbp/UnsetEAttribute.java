package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface UnsetEAttribute extends EObject {
	String getName();
	void setName(String getName);

	String getTarget();
	void setTarget(String target);
}