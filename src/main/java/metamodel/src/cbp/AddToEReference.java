package metamodel.src.cbp;

import org.eclipse.emf.ecore.EObject;

public interface AddToEReference extends EObject {
	String getName();
	void setName(String getName);

	String getPosition();
	void setPosition(String position);

	String getTarget();
	void setTarget(String target);

	Value getValue();
	void setValue(Value value);
}