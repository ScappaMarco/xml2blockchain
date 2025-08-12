package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface RemoveFromEAttribute extends EObject {
	String getName();
	void setName(String getName);

	String getTarget();
	void setTarget(String target);

	Value getValue();
	void setValue(Value value);
}